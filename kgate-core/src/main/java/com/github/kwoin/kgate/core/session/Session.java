package com.github.kwoin.kgate.core.session;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;

import java.io.IOException;
import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class Session<T extends Message> implements Runnable {


    protected Socket input;
    protected Socket output;
    protected AbstractSequencer<T> sequencer;
    protected Chain<T> chain;
    protected Session oppositeSession;
    protected boolean started;


    Session(Socket input, Socket output, AbstractSequencer<T> sequencer, Chain<T> chain) {

        this.input = input;
        this.output = output;
        this.sequencer = sequencer;
        this.chain = chain;
        started = false;

    }


    public Socket getInput() {

        return input;

    }


    public Socket getOutput() {

        return output;

    }


    public AbstractSequencer<T> getSequencer() {

        return sequencer;

    }


    public Chain<T> getChain() {

        return chain;

    }


    public Session getOppositeSession() {

        return oppositeSession;

    }


    public void setOppositeSession(Session oppositeSession) {

        this.oppositeSession = oppositeSession;

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

        try {
            while (started && sequencer.hasNext()) {

                T message = sequencer.next();
                chain.execute(this, null, message);

            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error", e);
        }

    }


}
