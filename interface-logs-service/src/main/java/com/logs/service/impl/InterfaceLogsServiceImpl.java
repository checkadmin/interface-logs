package com.logs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logs.mapper.InterfaceLogsMapper;
import com.logs.model.entity.InterfaceLogs;
import com.logs.service.IInterfaceLogsService;
import org.springframework.stereotype.Service;

/**
 * @author ： coder.Yang
 * @date ： 2021/9/17 18:56
 * @description ：日志过滤处理业务
 */
@Service
public class InterfaceLogsServiceImpl extends ServiceImpl<InterfaceLogsMapper, InterfaceLogs> implements IInterfaceLogsService {

}
