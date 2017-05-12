package com.github.kwoin.kgate.core.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;
import com.github.kwoin.kgate.core.transmitter.Transmitter;
import com.github.kwoin.kgate.test.sequencer.DummySequencer;
import com.github.kwoin.kgate.test.DummyServer;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * @author P. WILLEMET
 */
public class KGateTest {


    DummyServer dummyServer = new DummyServer();
    AbstractGateway<Message, Message> gateway = new AbstractGateway<Message, Message>() {
        {
            clientToServerSequencerFactory = new ISequencerFactory<Message>() {
                @Override
                public AbstractSequencer<Message> newSequencer() {
                    return new DummySequencer();
                }
            };
            serverToClientSequencerFactory = new ISequencerFactory<Message>() {
                @Override
                public AbstractSequencer<Message> newSequencer() {
                    return new DummySequencer();
                }
            };
            toServerTransmitter = new Transmitter<>();
            toClientTransmitter = new Transmitter<>();
        }
    };
    DummyServer.Callback callback = new DummyServer.Callback() {
        @Override
        public void execute(Socket socket) {
            int read;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                while((read = socket.getInputStream().read()) != -1) {
                    baos.write(read);
                    if(read == '!')
                        break;
                }
                if("bye!".equals(baos.toString()))
                    socket.close();
                else
                    socket.getOutputStream().write("ok!".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };


    @Test
    public void test() throws IOException {

        dummyServer.start(callback, false);
        gateway.start();

        Socket socket = new Socket(
                KGateConfig.getConfig().getString("kgate.core.server.host"),
                KGateConfig.getConfig().getInt("kgate.core.server.port"));

        socket.getOutputStream().write("kwoin!".getBytes());
        byte[] buffer = new byte[3];
        socket.getInputStream().read(buffer);
        socket.getOutputStream().write("bye!".getBytes());
        socket.close();
        assertArrayEquals("ok!".getBytes(), buffer);

        dummyServer.stop();
        gateway.stop();

    }


    @Test
    public void tlsTest() throws IOException, NoSuchAlgorithmException, KeyManagementException {

        //System.setProperty("javax.net.debug", "all"); incompatible with surefire ? needs investigation
        KGateConfig.getConfig().setProperty("kgate.core.security.tlsEnabled", "true");
        System.setProperty("javax.net.ssl.keyStore", KGateConfig.getConfig().getString("kgate.core.security.keystore.path"));
        System.setProperty("javax.net.ssl.keyStorePassword", KGateConfig.getConfig().getString("kgate.core.security.keystore.password"));
        if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.path").isEmpty())
            System.setProperty("javax.net.ssl.trustStore", KGateConfig.getConfig().getString("kgate.core.security.truststore.path"));
        if(!KGateConfig.getConfig().getString("kgate.core.security.truststore.password").isEmpty())
            System.setProperty("javax.net.ssl.trustStorePassword", KGateConfig.getConfig().getString("kgate.core.security.truststore.password"));
        dummyServer.start(callback, true);
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

        Socket socket = sslContext.getSocketFactory().createSocket(
                KGateConfig.getConfig().getString("kgate.core.server.host"),
                KGateConfig.getConfig().getInt("kgate.core.server.port"));

        socket.getOutputStream().write("kwoin!".getBytes());
        byte[] buffer = new byte[3];
        socket.getInputStream().read(buffer);
        socket.getOutputStream().write("bye!".getBytes());
        socket.close();
        assertArrayEquals("ok!".getBytes(), buffer);

        dummyServer.stop();
        gateway.stop();
        System.setProperty("javax.net.debug", "");
        KGateConfig.getConfig().setProperty("kgate.core.security.tlsEnabled", "false");
        System.setProperty("javax.net.ssl.keyStore", "");
        System.setProperty("javax.net.ssl.keyStorePassword", "");
        System.setProperty("javax.net.ssl.trustStore", "");
        System.setProperty("javax.net.ssl.trustStore", "");

    }


}
