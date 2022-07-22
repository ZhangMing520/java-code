package com.example.logger;

import Ice.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangming
 * @date 2020/7/14 23:36
 * <p>
 * 修改 Ice 系统日志，采用我们使用的日志库输出（Logback）
 * <p>
 * 思路：扩张一个 Ice Log 类 Sl4JLoggerI， 采用 Logback 来输出日志，最后强迫 IceBox 加载此 Log 类作为默认的 Logger
 *
 * wrap类一个 IceBox 类
 */
public class Sl4JLoggerI implements Ice.Logger {

    private final org.slf4j.Logger logger;

    public Sl4JLoggerI(String loggerName) {
        this.logger = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public void print(String message) {
        logger.info(message);
    }

    @Override
    public void trace(String category, String message) {
        logger.debug("{} {}", category, message);
    }

    @Override
    public void warning(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public Logger cloneWithPrefix(String prefix) {
        return new Sl4JLoggerI(prefix);
    }
}
