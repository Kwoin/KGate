package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.client.DefaultClientSocketFactory;
import com.github.kwoin.kgate.core.gateway.server.DefaultServer;
import com.github.kwoin.kgate.core.gateway.server.DefaultServerSocketFactory;
import com.github.kwoin.kgate.core.processor.DefaultProcessor;
import com.github.kwoin.kgate.core.processor.IProcessor;
import com.github.kwoin.kgate.core.processor.IProcessorFactory;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommandListFactory;
import com.github.kwoin.kgate.core.socket.KGateSocket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class KGateTlsTest {


    private static boolean success;


    @BeforeClass
    public static void beforeClass() {

        System.setProperty("javax.net.debug", "all");
        KGateConfig.getConfig().setProperty("kgate.core.security.tlsEnabled", "true");
        System.setProperty("javax.net.ssl.keyStore", KGateConfig.getConfig().getString("kgate.core.security.keystore.path"));
        System.setProperty("javax.net.ssl.keyStorePassword", KGateConfig.getConfig().getString("kgate.core.security.keystore.password"));
        if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.path").isEmpty())
            System.setProperty("javax.net.ssl.trustStore", KGateConfig.getConfig().getString("kgate.core.security.truststore.path"));
        if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.password").isEmpty())
            System.setProperty("javax.net.ssl.trustStore", KGateConfig.getConfig().getString("kgate.core.security.truststore.password"));

    }


    @Before
    public void before() {

        success = false;

    }


    @Test
    public void test() throws IOException, KGateServerException, NoSuchAlgorithmException, KeyManagementException {

        IGateway gateway = new DefaultGateway(new DefaultServer(new IProcessorFactory() {
            @Override
            public IProcessor newProcessor() {
                return new DefaultProcessor(
                        new IChainFactory() {
                            @Override
                            public IChain newChain() {
                                return new DefaultChain(new ICommandListFactory() {
                                    @Override
                                    public List<ICommand> newCommandList() {
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
                                });
                            }
                        },
                        new IChainFactory() {
                            @Override
                            public IChain newChain() {
                                return new DefaultChain(new ICommandListFactory() {
                                    @Override
                                    public List<ICommand> newCommandList() {
                                        return Collections.EMPTY_LIST;
                                    }
                                });
                            }
                        }
                );
            }
        },
                new DefaultServerSocketFactory(),
                new DefaultClientSocketFactory()));

        ServerSocket serverSocket = SSLServerSocketFactory.getDefault().createServerSocket(7072);

        gateway.start();


        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, null);
        Socket socket = sslContext.getSocketFactory().createSocket("127.0.0.1", 7070);
        socket.getOutputStream().write("kwoin".getBytes());
        socket.getInputStream().read();
        socket.close();
        serverSocket.close();
        gateway.stop();
        Assert.assertTrue(success);

    }

}
