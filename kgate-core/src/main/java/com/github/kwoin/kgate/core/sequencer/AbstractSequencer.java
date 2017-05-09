package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractSequencer<T extends Message> implements Iterator<T> {


    private final Logger logger = LoggerFactory.getLogger(AbstractSequencer.class);
    protected Socket input;
    protected boolean hasNext;


    public AbstractSequencer(Socket input) {

        this.input = input;
        hasNext = !input.isInputShutdown();

    }

    
    @Override
    public boolean hasNext() {

        hasNext &= !input.isClosed();
        return hasNext;

    }


    @Override
    @Nullable
    public T next() {

        if(!hasNext())
            throw new NoSuchElementException();

        try {
            return readNextMessage();
        } catch (SocketException e) {
            logger.debug("Input read() interrupted because socket has been closed");
            hasNext = false;
            return null;
        } catch (IOException e) {
            logger.error("Unexpected error while reading next message", e);
            return null;
        }

    }


    protected abstract T readNextMessage() throws IOException;


}
