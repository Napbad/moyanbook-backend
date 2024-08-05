package com.moyanshushe.utils.log;

import org.slf4j.helpers.MessageFormatter;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class LogUtil {
    private static final ThreadLocal<Logger> LOGGERS = new ThreadLocal<>();

    public static void init(Class<?> clazz, String loggerName) {
        LOGGERS.set(Logger.getLogger(loggerName != null ? loggerName : clazz.getName()));
    }

    public static void log(LogLevel level, String message, Object... args) {
        if (LOGGERS.get() != null && LOGGERS.get().isLoggable(level.getLevel())) {
            LOGGERS.get().log(level.getLevel(), formatMessage(message, args));
        }
    }

    private static String formatMessage(String message, Object[] args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }

    public enum LogLevel {
        TRACE(Level.FINEST),
        DEBUG(Level.FINE),
        INFO(Level.INFO),
        WARN(Level.WARNING),
        ERROR(Level.SEVERE);

        private Level level;

        LogLevel(Level level) {
            this.level = level;
        }

        public Level getLevel() {
            return level;
        }
    }
}
