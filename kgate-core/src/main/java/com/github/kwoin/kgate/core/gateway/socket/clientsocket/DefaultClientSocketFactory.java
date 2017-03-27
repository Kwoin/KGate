package com.github.kwoin.kgate.core.gateway.socket.clientsocket;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.gateway.socket.KGateSocket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * @author P. WILLEMET
 */
public class DefaultClientSocketFactory implements IClientSocketFactory {


    @Override
    public KGateSocket newClientSocket() throws IOException {

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
                } catch (NoSuchAlgorithmException|KeyManagementException e) {
                    //should not happen
                    e.printStackTrace();
                }

            } else {

                socketFactory = SSLSocketFactory.getDefault();

            }

        } else {

            socketFactory = SocketFactory.getDefault();

        }

        return new KGateSocket(socketFactory.createSocket(host, port));

    }


}
