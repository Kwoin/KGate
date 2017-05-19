package com.github.kwoin.kgate.core.command;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class LoggerCommand implements ICommand {


    private final Logger logger = LoggerFactory.getLogger(LoggerCommand.class);
    private Level level;
    private Callback callback;


    public LoggerCommand() {

        this(Level.DEBUG, new DefaultCallback());

    }


    public LoggerCommand(Level level, Callback newLogMessageCallback) {

        this.level = level;
        this.callback = newLogMessageCallback;

    }


    @Override
    public void execute(Session session, Chain callingChain, Message message) throws IOException {

        switch(level) {
            case TRACE:
                if(!logger.isTraceEnabled()) return;
                break;
            case DEBUG:
                if(!logger.isDebugEnabled()) return;
                break;
            case INFO:
                if(!logger.isInfoEnabled()) return;
                break;
            case WARN:
                if(!logger.isWarnEnabled()) return;
                break;
            case ERROR:
                if(!logger.isErrorEnabled()) return;
                break;
        }

        String logMessage = callback.newLogMessage(session, message);

        switch(level) {
            case TRACE:
                logger.trace(logMessage);
                break;
            case DEBUG:
                logger.debug(logMessage);
                break;
            case INFO:
                logger.info(logMessage);
                break;
            case WARN:
                logger.warn(logMessage);
                break;
            case ERROR:
                logger.error(logMessage);
                break;
        }

    }


    public interface Callback {

        String newLogMessage(Session session, Message message);

    }


    public static class DefaultCallback implements Callback {

        @Override
        public String newLogMessage(Session session, Message message) {

            return new StringBuilder()
                    .append("NEW ")
                    .append(message.getClass().getSimpleName())
                    .append("\n")
                    .append(new String(message.getToBeTransmitted()))
                    .toString();

        }

    }

}
