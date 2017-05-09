package com.github.kwoin.kgate.core.session;

import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


/**
 * @author P. WILLEMET
 */
public class SessionManager {


    private static final SessionManager ourInstance = new SessionManager();
    private static final Set<Session> leftSessions = new HashSet<>();


    public static SessionManager getInstance() {

        return ourInstance;

    }


    private SessionManager() {



    }


    public synchronized <L extends Message, R extends Message> void createSession(Socket input,
                                                                                  Socket output,
                                                                                  AbstractSequencer<L> clientToServerSequencer,
                                                                                  Chain<L> clientToServerChain,
                                                                                  AbstractSequencer<R> serverToClientSequencer,
                                                                                  Chain<R> serverToClientChain) {

        Session<L> leftSession = new Session<>(input, output, clientToServerSequencer, clientToServerChain, true);
        Session<R> rightSession = new Session<>(output, input, serverToClientSequencer, serverToClientChain, false);
        leftSession.setOppositeSession(rightSession);
        rightSession.setOppositeSession(leftSession);
        leftSessions.add(leftSession);
        leftSession.start();
        rightSession.start();

    }


    public synchronized void deleteBothSessions(Session leftSession) {

        if(!leftSessions.remove(leftSession))
            throw new IllegalArgumentException("Not a left Session");

        leftSession.stop();
        leftSession.getOppositeSession().stop();

    }


    public synchronized void deleteAllSessions() {

        for (Session leftSession : leftSessions) {
            leftSession.stop();
            leftSession.getOppositeSession().stop();
        }
        leftSessions.clear();

    }

}
