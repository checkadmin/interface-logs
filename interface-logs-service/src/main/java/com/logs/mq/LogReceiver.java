package com.logs.mq;

import com.logs.model.entity.InterfaceLogs;
import com.logs.service.IInterfaceLogsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ： coder.Yang
 * @date ： 2021/9/17 19:05
 * @description ：消费入口
 */
@Component
@RabbitListener(
        bindings=@QueueBinding(
                value=@Queue(value="log.all",autoDelete="true"),
                exchange=@Exchange(value="log.topic",type= ExchangeTypes.TOPIC),
                key="log.*"
        )
)
public class LogReceiver {

    @Autowired
    private IInterfaceLogsService iInterfaceLogsService;

    @RabbitHandler
    public void process(InterfaceLogs userRequestLog ){
        iInterfaceLogsService.save(userRequestLog);
    }
}
