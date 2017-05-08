package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.factory.DefaultGatewayFactorySet;
import com.github.kwoin.kgate.core.factory.DefaultProcessorComponentsFactory;
import com.github.kwoin.kgate.core.factory.IGatewayFactorySet;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class KGateTlsTest {


    @BeforeAll
    public static void beforeClass() {

        System.setProperty("javax.net.debug", "all");
        KGateConfig.getConfig().setProperty("kgate.core.security.tlsEnabled", "true");
        System.setProperty("javax.net.ssl.keyStore", KGateConfig.getConfig().getString("kgate.core.security.keystore.path"));
        System.setProperty("javax.net.ssl.keyStorePassword", KGateConfig.getConfig().getString("kgate.core.security.keystore.password"));
        if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.path").isEmpty())
            System.setProperty("javax.net.ssl.trustStore", KGateConfig.getConfig().getString("kgate.core.security.truststore.path"));
        if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.password").isEmpty())
            System.setProperty("javax.net.ssl.trustStorePassword", KGateConfig.getConfig().getString("kgate.core.security.truststore.password"));

    }


    @AfterAll
    public static void afterClass() {

        System.setProperty("javax.net.debug", "");
        KGateConfig.getConfig().setProperty("kgate.core.security.tlsEnabled", "false");
        System.setProperty("javax.net.ssl.keyStore", "");
        System.setProperty("javax.net.ssl.keyStorePassword", "");
        System.setProperty("javax.net.ssl.trustStore", "");
        System.setProperty("javax.net.ssl.trustStore", "");

    }


    @Test
    public void test() throws IOException, KGateServerException, NoSuchAlgorithmException, KeyManagementException {

        IGatewayFactorySet gatewayFactorySet = new DefaultGatewayFactorySet();
        gatewayFactorySet.setProcessorComponentsFactory( new DefaultProcessorComponentsFactory() {

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

        });


        AbstractGateway gateway = new DefaultGateway(gatewayFactorySet);

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
        int read = socket.getInputStream().read();
        socket.close();
        serverSocket.close();
        gateway.stop();
        assertEquals('1', read);

    }

}
