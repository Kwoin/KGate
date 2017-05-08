package com.github.kwoin.kgate.core.session;

import com.github.kwoin.kgate.core.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class Session<T extends Message> implements Runnable {


    protected Socket input;
    protected Socket output;
    protected AbstractSequencer<T> sequencer;
    protected DefaultChain<T> chain;
    protected boolean started;


    Session(Socket input, Socket output, AbstractSequencer<T> sequencer, DefaultChain<T> chain) {

        this.input = input;
        this.output = output;
        this.sequencer = sequencer;
        this.chain = chain;
        started = false;

    }


    public void start() {

        Thread sessionThread = new Thread(this);
        started = true;
        sessionThread.start();

    }


    public void stop() {

        started = false;
        chain.interrupt();

    }


    @Override
    public void run() {

        while(started && sequencer.hasNext()) {

            T message = sequencer.next();
            chain.execute(input, output, null, message);

        }

    }


}
