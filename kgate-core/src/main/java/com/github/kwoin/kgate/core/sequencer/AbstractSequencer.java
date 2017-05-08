package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.message.Message;

import javax.annotation.Nullable;
import java.net.Socket;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractSequencer<T extends Message> implements Iterator<T> {


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

        return readNextMessage();

    }


    protected abstract T readNextMessage();


}
