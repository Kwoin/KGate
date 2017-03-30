package com.github.kwoin.kgate.core.factory;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.factory.IInputPointManagerComponentsFactory;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.gateway.io.SocketPoint;
import com.github.kwoin.kgate.core.gateway.processor.DefaultProcessor;
import com.github.kwoin.kgate.core.gateway.processor.IProcessor;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * @author P. WILLEMET
 */
public class DefaultInputPointManagerComponentFactory implements IInputPointManagerComponentsFactory<Socket> {


    @Override
    public IoPoint newInputPoint(IContext context, Socket backStream) {

        return new SocketPoint(backStream);

    }


    @Override
    public IoPoint newOutputPoint(IContext context) throws IOException {

        KGateConfig.getConfig().setThrowExceptionOnMissing(true);
        String host = KGateConfig.getConfig().getString("kgate.core.client.host");
        KGateConfig.getConfig().setThrowExceptionOnMissing(false);
        int port = KGateConfig.getConfig().getInt("kgate.core.client.port");

        SocketFactory socketFactory = null;
        if (Boolean.parseBoolean(KGateConfig.getConfig().getString("kgate.core.security.tlsEnabled"))) {

            if (Boolean.parseBoolean(KGateConfig.getConfig().getString("kgate.core.security.acceptAllCerts"))) {

                try {
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
                    socketFactory = sslContext.getSocketFactory();
                } catch (NoSuchAlgorithmException |KeyManagementException e) {
                    //should not happen
                    e.printStackTrace();
                }

            } else {

                socketFactory = SSLSocketFactory.getDefault();

            }

        } else {

            socketFactory = SocketFactory.getDefault();

        }

        return new SocketPoint(socketFactory.createSocket(host, port));

    }


    @Override
    public IProcessor newProcessor(IContext context) {

        return new DefaultProcessor();

    }

}
