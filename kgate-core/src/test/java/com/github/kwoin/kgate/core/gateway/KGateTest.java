package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommandListFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
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

        IGateway gateway = new DefaultGateway();
        gateway.setSourceToTargetChainFactory(() -> {

            IChain chain = new DefaultChain();
            chain.setCommandListFactory(() -> Arrays.asList((ICommand) (source, target, context, callingChain) -> {

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

            }));

            return chain;

        });
        gateway.setTargetToSourceChainFactory(new IChainFactory() {
            @Override
            public IChain newChain() {
                IChain chain = new DefaultChain();
                chain.setCommandListFactory(new ICommandListFactory() {
                    @Override
                    public List<ICommand> newCommandList() {
                        return Collections.EMPTY_LIST;
                    }
                });
                return chain;
            }
        });


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
        //server.setSoTimeout(1000);
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
