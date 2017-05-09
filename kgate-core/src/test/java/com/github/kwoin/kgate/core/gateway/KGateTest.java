package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.core.sequencer.DummySequencer;
import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author P. WILLEMET
 */
public class KGateTest {



    @BeforeEach
    public void init() {



    }


    @Test
    public void test() throws IOException {

        AbstractGateway<Message, Message> gateway = new AbstractGateway<Message, Message>() {
            {
                clientToServerSequencerFactory = new ISequencerFactory<Message>() {
                    @Override
                    public AbstractSequencer<Message> newSequencer(Socket input) {
                        return new DummySequencer(input);
                    }
                };
                serverToClientSequencerFactory = new ISequencerFactory<Message>() {
                    @Override
                    public AbstractSequencer<Message> newSequencer(Socket input) {
                        return new DummySequencer(input);
                    }
                };
            }
        };

        Socket socket = new Socket("127.0.0.1", 7072);

        gateway.start();

        Socket socket = new Socket("127.0.0.1", 7070);
        socket.getOutputStream().write("kwoin".getBytes());
        int read = socket.getInputStream().read();
        socket.close();
        serverSocket.close();
        gateway.stop();
        assertEquals('1', read);

    }


    class DummyServer extends Thread {

        @Override
        public void run() {

            ServerSocket serverSocket = new ServerSocket(

            )

        }

    }


}
