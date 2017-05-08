package com.github.kwoin.kgate.core.session;

import com.github.kwoin.kgate.core.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class SessionManager {


    private static final SessionManager ourInstance = new SessionManager();
    private static final List<Session> sessions = new ArrayList<>();


    public static SessionManager getInstance() {

        return ourInstance;

    }


    private SessionManager() {



    }


    public synchronized <T extends Message> void createSession(Socket input, Socket output, AbstractSequencer<T> sequencer, DefaultChain<T> chain) {

        Session<T> session = new Session(input, output, sequencer, chain);
        sessions.add(session);
        session.start();

    }


    public synchronized void deleteSession(Session session) {

        session.stop();
        sessions.remove(session);

    }


    public synchronized void deleteAllSessions() {

        for (int i = sessions.size() - 1; i >= 0; i--)
            sessions.remove(i).stop();

    }

}
