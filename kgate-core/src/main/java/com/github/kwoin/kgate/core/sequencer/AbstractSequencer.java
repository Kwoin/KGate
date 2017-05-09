package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.Socket;
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

        hasNext &= !input.isInputShutdown();
        return hasNext;

    }


    @Override
    @Nullable
    public T next() {

        if(!hasNext())
            throw new NoSuchElementException();

        try {
            return readNextMessage();
        } catch (IOException e) {
            logger.error("Unexpected error while reading next message", e);
            return null;
        }

    }


    protected abstract T readNextMessage() throws IOException;


}
