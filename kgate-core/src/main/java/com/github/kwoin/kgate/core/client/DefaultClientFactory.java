package com.github.kwoin.kgate.core.client;

import com.github.kwoin.kgate.core.configuration.KGateConfig;

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
public class DefaultClientFactory implements IClientFactory {


    @Override
    public Socket newClient() {

        try {

            String host = KGateConfig.getConfig().getString("kgate.core.client.host");
            int port = KGateConfig.getConfig().getInt("kgate.core.client.port");

            SocketFactory socketFactory = null;
            if (Boolean.parseBoolean(KGateConfig.getConfig().getString("kgate.core.security.tlsEnabled"))) {

                if (Boolean.parseBoolean(KGateConfig.getConfig().getString("kgate.core.security.acceptAllCerts"))) {

                    try {
                        SSLContext sslContext = SSLContext.getInstance("TLS");
                        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
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
                    } catch (NoSuchAlgorithmException | KeyManagementException e) {
                        //should not happen
                        e.printStackTrace();
                    }

                } else {

                    socketFactory = SSLSocketFactory.getDefault();

                }

            } else {

                socketFactory = SocketFactory.getDefault();

            }

            return socketFactory.createSocket(host, port);

        } catch (IOException e) {
            throw new RuntimeException("Could not create client", e);
        }

    }


}
