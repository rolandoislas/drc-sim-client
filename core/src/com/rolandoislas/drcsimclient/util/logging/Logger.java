package com.rolandoislas.drcsimclient.util.logging;

import com.rolandoislas.drcsimclient.data.Constants;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by rolando on 3/21/17.
 */
public class Logger {
    private static final java.util.logging.Logger logger;

    static {
        logger = java.util.logging.Logger.getLogger("drcsim");
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        File logFile = new File(Constants.PATH_LOG, "client.log");
        try {
            Constants.PATH_LOG.mkdirs();
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath());
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void debug(String message, Object... o) {
        logger.log(Level.FINE, String.format(message, o));
    }
}
