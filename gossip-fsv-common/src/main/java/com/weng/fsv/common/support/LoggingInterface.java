package com.weng.fsv.common.support;

import com.weng.fsv.common.enums.LogLevelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wengchengjian
 * @date 2023/8/10-9:50
 */
public interface LoggingInterface {

    String DEFAULT_LOG_FORMAT = "[{}/{}]: %s";

    Logger log = LoggerFactory.getLogger(LoggingInterface.class);

    String getModule();

    LogLevelEnum getLogLevel();

    default void info(String format, Object... msg) {
        String nextFormat = String.format(DEFAULT_LOG_FORMAT, format);
        List<Object> args = new ArrayList<>();
        args.add(getLogLevel());
        args.add(getModule());
        args.addAll(Arrays.asList(msg));
        log.info(nextFormat, args.toArray());
    }

    default void warn(String format, Object... msg) {
        String nextFormat = String.format(DEFAULT_LOG_FORMAT, format);
        List<Object> args = new ArrayList<>();
        args.add(getLogLevel());
        args.add(getModule());
        args.addAll(Arrays.asList(msg));
        log.warn(nextFormat, args.toArray());
    }

    default void trace(String format, Object... msg) {
        String nextFormat = String.format(DEFAULT_LOG_FORMAT, format);
        List<Object> args = new ArrayList<>();
        args.add(getLogLevel());
        args.add(getModule());
        args.addAll(Arrays.asList(msg));
        log.trace(nextFormat, args.toArray());
    }

    default void debug(String format, Object... msg) {
        String nextFormat = String.format(DEFAULT_LOG_FORMAT, format);
        List<Object> args = new ArrayList<>();
        args.add(getLogLevel());
        args.add(getModule());
        args.addAll(Arrays.asList(msg));
        log.debug(nextFormat, args.toArray());
    }

    default void error(String format, Object... msg) {
        String nextFormat = String.format(DEFAULT_LOG_FORMAT, format);
        List<Object> args = new ArrayList<>();
        args.add(getLogLevel());
        args.add(getModule());
        args.addAll(Arrays.asList(msg));
        log.error(nextFormat, args.toArray());
    }
}
