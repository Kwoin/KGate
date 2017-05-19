package com.github.kwoin.kgate.core.session;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.context.Context;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class Session<T extends Message> implements Runnable {


    private final Logger logger = LoggerFactory.getLogger(Session.class);
    protected Socket input;
    protected Socket output;
    protected AbstractSequencer<T> sequencer;
    protected Chain<T> chain;
    protected Context sessionContext;
    protected Session oppositeSession;
    protected boolean leftSession;
    protected boolean started;


    Session(Socket input, Socket output, AbstractSequencer<T> sequencer, Chain<T> chain, boolean leftSession) {

        this.input = input;
        this.output = output;
        this.sequencer = sequencer;
        this.chain = chain;
        this.leftSession = leftSession;
        sessionContext = new Context();
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


    public Context getSessionContext() {

        return sessionContext;

    }


    public Session getOppositeSession() {

        return oppositeSession;

    }


    public void setOppositeSession(Session oppositeSession) {

        this.oppositeSession = oppositeSession;

    }


    public void start() {

        logger.debug("Starting " + (leftSession ? "left" : "right") + " Session (" + this + ") ...");

        Thread sessionThread = new Thread(this);
        started = true;
        sessionThread.start();

        logger.debug((leftSession ? "left" : "right") + " Session (" + this + ") STARTED");

    }


    public void stop() {

        logger.debug("Stopping " + (leftSession ? "left" : "right") + " Session (" + this + ") ...");

        started = false;
        chain.interrupt();
        try {
            input.close();
        } catch (IOException e) {
            logger.error("Unexpected error while closing Session input", e);
        }

        logger.debug((leftSession ? "left" : "right") + " Session (" + this + ") STOPPED");

    }


    @Override
    public void run() {

        try {

            while (started && sequencer.hasNext()) {

                T message = sequencer.next();
                if(message != null)
                    chain.execute(this, null, message);

            }

            if(started && leftSession)
                SessionManager.getInstance().deleteBothSessions(this);

        } catch (IOException e) {
            throw new RuntimeException("Unexpected error", e);
        }

    }


}
