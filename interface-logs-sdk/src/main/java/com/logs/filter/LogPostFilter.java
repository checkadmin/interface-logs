package com.logs.filter;

import com.logs.model.constants.LogConstant;
import com.logs.model.entity.InterfaceLogs;
import com.logs.util.IPUtil;
import com.logs.util.OsUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Enumeration;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * @author ： coder.Yang
 * @date ： 2021/9/17 14:07
 * @description ：后置拦截
 */
@Configuration
public class LogPostFilter extends ZuulFilter {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String requestUrl=request.getRequestURI();
        if(requestUrl.startsWith("/api/foundation/v2/api-docs")
                ||requestUrl.startsWith("/api/user/v2/api-docs")
                || requestUrl.startsWith("/api/produce/v2/api-docs")
                || requestUrl.startsWith("/api/customer/v2/api-docs")
                || requestUrl.startsWith("/api/express/v2/api-docs")
                ||requestUrl.startsWith("/api/appconfig/v2/api-docs")
                || requestUrl.startsWith("/api/operuser/v2/api-docs")
                || requestUrl.startsWith("/api/nongwei/v2/api-docs")
                || requestUrl.startsWith("/api/expert/v2/api-docs")
                || requestUrl.startsWith("/api/governmentuser/v2/api-docs")
                || requestUrl.startsWith("/api/operate/v2/api-docs")
                || requestUrl.startsWith("/api/adminuser/v2/api-docs")
                || requestUrl.startsWith("/api/archive/v2/api-docs")){
            return null;
        }

        InterfaceLogs requestLog = new InterfaceLogs();
        requestLog.setServerUrl(request.getRequestURI());

        String url = request.getRequestURL().toString();
        String[] urlArray = url.split("/");
        requestLog.setServerName(urlArray[4]);

        requestLog.setIpAddress(IPUtil.getIp(request));
        requestLog.setMethod(request.getMethod());
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            sb.append( request.getHeader(headerName));
        }
        requestLog.setHeaderParam(sb.toString());

        InputStream in = request.getInputStream();
        String reqBody = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
        requestLog.setInputs(reqBody);

        InputStream out = ctx.getResponseDataStream();
        String outBody = StreamUtils.copyToString(out, StandardCharsets.UTF_8);
        requestLog.setOutputs(outBody);

        long startTime = (long) ctx.get(LogConstant.START_TIME_KE);
        requestLog.setStartTime(LocalDateTime.now());
        requestLog.setRequestTime((System.currentTimeMillis() - startTime));
        requestLog.setClient(OsUtil.getBrowserInfo(request));
        requestLog.setOs(OsUtil.getOs(request));

        requestLog.setUserInfo(request.getHeader("accessToken"));

        amqpTemplate.convertAndSend(LogConstant.MQ_CONFIG_EXCHANGE,LogConstant.MQ_CONFIG_QUEUE_ROUTING_KEY, requestLog);
        return null;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}
