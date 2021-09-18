package com.logs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logs.model.entity.InterfaceLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ： coder.Yang
 * @date ： 2021/9/17 18:52
 * @description ：日志处理
 */
@Mapper
public interface InterfaceLogsMapper extends BaseMapper<InterfaceLogs> {

}
