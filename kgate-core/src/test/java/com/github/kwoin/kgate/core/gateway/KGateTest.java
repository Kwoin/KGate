package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChainFactory;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.ICommandListFactory;
import com.github.kwoin.kgate.core.gateway.socket.KGateSocket;
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


        ICommandListFactory commandListFactory = new ICommandListFactory() {
            @Override
            public List<ICommand> newCommandList(IContext context) {

                return Arrays.asList(new ICommand() {
                    @Override
                    public void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException {
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

        IChainFactory sourceToTargetChainFactory = new IChainFactory() {
            @Override
            public IChain newChain(IContext context) {
                IChain chain = new DefaultChain();
                chain.setCommandListFactory(commandListFactory);
                return chain;
            }
        };

        IGateway gateway = new DefaultGateway();
        gateway.setSourceToTargetChainFactory(sourceToTargetChainFactory);

        ServerSocket serverSocket = new ServerSocket(7072);

        gateway.start();

        Socket socket = new Socket("127.0.0.1", 7070);
        socket.getOutputStream().write("kwoin".getBytes());
        socket.getInputStream().read();
        socket.close();
        serverSocket.close();
        gateway.stop();
        Assert.assertTrue(success);

    }


    @Test
    public void test2() throws IOException, KGateServerException, InterruptedException {

        IGateway gateway = new DefaultGateway();
        ServerSocket server = new ServerSocket(KGateConfig.getConfig().getInt("kgate.core.client.port"));
        //io.setSoTimeout(1000);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket target = server.accept();
                    success = 'k' == target.getInputStream().read()
                            && 'w' == target.getInputStream().read()
                            && 'o' == target.getInputStream().read()
                            && 'i' == target.getInputStream().read()
                            && 'n' == target.getInputStream().read()
                            && -1 == target.getInputStream().read();
                    target.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        gateway.start();

        Socket source = new Socket(KGateConfig.getConfig().getString("kgate.core.server.host"), KGateConfig.getConfig().getInt("kgate.core.server.port"));
        source.getOutputStream().write("kwoin".getBytes());
        source.close();

        t.join();

        gateway.stop();
        server.close();
        source.close();

        Assert.assertTrue(success);

    }


}
