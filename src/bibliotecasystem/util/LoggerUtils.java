package bibliotecasystem.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class LoggerUtils {
    private static final Logger ROOT_LOGGER = Logger.getLogger("bibliotecasystem");
    static {
        ROOT_LOGGER.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        ROOT_LOGGER.addHandler(handler);
        ROOT_LOGGER.setUseParentHandlers(false);
    }

    private LoggerUtils() {
    }

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        for (var handler : logger.getHandlers()) {
            handler.setLevel(Level.ALL);
        }
        if (logger.getHandlers().length == 0) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        }
        return logger;
    }
}
