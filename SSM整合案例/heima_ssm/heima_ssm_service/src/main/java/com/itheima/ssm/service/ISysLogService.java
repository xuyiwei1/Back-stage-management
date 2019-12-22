package com.itheima.ssm.service;

import com.itheima.ssm.domain.SysLog;

import java.util.List;

public interface ISysLogService {

    /**
     * 插入日志信息
     * @param sysLog
     */
    void save(SysLog sysLog);

    List<SysLog> findAll();
}
