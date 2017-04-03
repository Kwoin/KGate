package com.github.kwoin.kgate.smtp;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.smtp.gateway.SmtpGateway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author P. WILLEMET
 */
public class SmtpGatewayTest {


    static final SmtpGateway gateway = new SmtpGateway();


    @BeforeAll
    static void initAll() {

        SmtpTestUtil.startSmtpServer(25251);
        KGateConfig.getConfig().setProperty("kgate.core.client.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.client.port", "25251");
        KGateConfig.getConfig().setProperty("kgate.core.server.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.server.port", "25252");

    }


    @AfterAll
    static void tearDownAll() {

        SmtpTestUtil.stopSmtpServer();

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
    void testSmtpGateway() throws MessagingException {

        MimeMessage message = SmtpTestUtil.initMessage("emitter@test.fr", "receiver1@test.fr", "receiver2@test.fr");
        message.setText("TEST");
        SmtpTestUtil.sendMessage(message);

    }


}
