package com.logs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ： coder.Yang
 * @date ： 2021/9/17 18:50
 * @description ：日志服务
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("request_logs")
public class InterfaceLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "request_log_id", type = IdType.AUTO)
    private Long requestLogId;

    /**
     * 服务名称
     */
    private String serverName;
    /**
     * 服务地址
     */
    private String serverUrl;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 用户信息，可以是token
     */
    private String userInfo;
    /**
     * 头部信息
     */
    private String headerParam;
    /**
     * 入参
     */
    private String inputs;
    /**
     * 出参
     */
    private String outputs;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 请求开始时间
     */
    private LocalDateTime startTime;
    /**
     * 请求时间
     */
    private long requestTime;
    /**
     * 客户端
     */
    private String client;
    /**
     * 系统
     */
    private String os;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 备注说明
     */
    private String remark;
}
