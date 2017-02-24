package com.github.kwoin.kgate.core;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.DefaultGateway;
import com.github.kwoin.kgate.core.gateway.IGateway;
import com.github.kwoin.kgate.core.gateway.server.DefaultServer;
import com.github.kwoin.kgate.core.processor.AbstractProcessor;
import com.github.kwoin.kgate.core.processor.IProcessor;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.AbstractChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class KGateTest {


    private static boolean success;


    @Before
    public void before() {

        success = false;

    }


    @Test
    public void test() throws IOException, KGateServerException {

        IGateway gateway = new DefaultGateway(new DefaultServer(new IProcessorFactory() {
            @Override
            public IProcessor newProcessor() {
                return new AbstractProcessor() {

                    @Override
                    protected IChain initializeSourceToTargetChain() {
                        return new AbstractChain() {
                            @Override
                            protected List<ICommand> initializeChainCommands() {
                                return Arrays.asList(new ICommand() {
                                    @Override
                                    public void run(Socket source, Socket target, IContext context, IChain callingChain) {

                                        try {
                                            byte[] buf = new byte[5];
                                            source.getInputStream().read(buf);
                                            String in = new String(buf);
                                            System.out.println("in : " + in);
                                            success = in.equals("kwoin");
                                            source.getOutputStream().write("ok".getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            try {
                                                source.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                });
                            }
                        };
                    }

                    @Override
                    protected IChain initializeTargetToSourceChain() {
                        return null;
                    }

                };
            }
        }));

        ServerSocket serverSocket = new ServerSocket(7072);

        gateway.start();

        Socket socket = new Socket("127.0.0.1", 7070);
        socket.getOutputStream().write("kwoin".getBytes());
        socket.getInputStream().read();
        Assert.assertTrue(success);

    }


}
