package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.ISequencerGateway;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class HttpGatewayTest {


    static final ISequencerGateway gateway = new HttpGateway();


    @BeforeAll
    static void initAll() {

        KGateConfig.getConfig().setProperty("kgate.core.client.host", "172.217.16.206");
        KGateConfig.getConfig().setProperty("kgate.core.client.port", "80");
        KGateConfig.getConfig().setProperty("kgate.core.server.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.server.port", "80");

    }


    @BeforeEach
    void init() throws KGateServerException {

        gateway.start();

    }


    @AfterEach
    void tearDown() throws KGateServerException {

        gateway.stop();

    }


    @Test
    void testGateway() throws Exception {

        HttpClient client = new HttpClient();
        client.start();
        ContentResponse contentResponse = client.GET("http://127.0.0.1/");
        client.stop();
        assertNotNull(contentResponse);

    }

}
