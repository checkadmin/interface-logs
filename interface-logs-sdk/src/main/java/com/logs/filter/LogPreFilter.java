package com.logs.filter;

import com.logs.model.constants.LogConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.context.annotation.Configuration;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * @author ： coder.Yang
 * @date ： 2021/9/17 14:07
 * @description ：前置拦截
 */
@Configuration
public class LogPreFilter extends ZuulFilter {

    @Override
    public Object run() {
        long startTime = System.currentTimeMillis();
        RequestContext.getCurrentContext().set(LogConstant.START_TIME_KE, startTime);
        return null;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
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
