package com.rolandoislas.drcsimclient.util.logging;

import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.data.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by rolando on 3/21/17.
 */
public class Logger {
    private static final java.util.logging.Logger logger;

    static {
        Level level = Level.INFO;
        if (Client.args.logDebug)
            level = Level.FINE;
        else if (Client.args.logExtra)
            level = Level.FINER;
        else if (Client.args.logVerbose)
            level = Level.FINEST;
        logger = java.util.logging.Logger.getLogger("drcsim");
        logger.setLevel(level);
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(level);
        consoleHandler.setFormatter(new LogFormatter());
        logger.addHandler(consoleHandler);
        File logFile = new File(Constants.PATH_LOG, "client.log");
        try {
            if (Constants.PATH_LOG.mkdirs())
                debug("Created log directory");
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath());
            fileHandler.setLevel(level);
            fileHandler.setFormatter(new LogFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            Logger.warn("Failed to create log file.");
            Logger.exception(e);
        }
    }

    public static void warn(String message, Object... o) {
        logger.log(Level.WARNING, message, o);
    }

    public static void debug(String message, Object... o) {
        logger.log(Level.FINE, String.format(message, o));
    }

    public static void info(String message, Object... o) {
        logger.log(Level.INFO, message, o);
    }

    public static void exception(Exception e) {
        extra(Arrays.toString(e.getStackTrace()));
    }

    private static void extra(String message, Object... o) {
        logger.log(Level.FINER, message, o);
    }

    private static void verbose(String message, Object... o) {
        logger.log(Level.FINEST, message, o);
    }

    private static class LogFormatter extends Formatter {
        @Override
        public String format(LogRecord logRecord) {
            return new Date(logRecord.getMillis()).toString() + " " +
                    logRecord.getLevel() + ":" +
                    logRecord.getLoggerName() + " " +
                    logRecord.getMessage() + "\n";
        }
    }
}
