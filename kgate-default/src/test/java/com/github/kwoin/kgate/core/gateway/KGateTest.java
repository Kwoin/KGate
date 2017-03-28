package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class KGateTest {



    @BeforeEach
    public void init() {



    }


    @Test
    public void test() throws IOException, KGateServerException {

        DefaultKGateComponentsFactory defaultKGateComponentsFactory = new DefaultKGateComponentsFactory() {

            @Override
            public IChain newRequestChain(IContext context) {
                return new DefaultChain(new ICommand() {
                    @Override
                    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {
                        try {
                            byte[] buf = new byte[5];
                            inputPoint.getInputStream().read(buf);
                            String in = new String(buf);
                            System.out.println("in : " + in);
                            if(in.equals("kwoin"))
                                inputPoint.getOutputStream().write("1".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                inputPoint.close();
                                outputPoint.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        };

        IGateway gateway = new DefaultGateway(defaultKGateComponentsFactory);

        ServerSocket serverSocket = new ServerSocket(7072);

        gateway.start();

        Socket socket = new Socket("127.0.0.1", 7070);
        socket.getOutputStream().write("kwoin".getBytes());
        int read = socket.getInputStream().read();
        socket.close();
        serverSocket.close();
        gateway.stop();
        assertEquals('1', read);

    }


}
