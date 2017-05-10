package com.github.kwoin.kgate.smtp;

import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.command.chain.Chain;
import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;
import com.github.kwoin.kgate.debug.command.LoggerCommand;
import com.github.kwoin.kgate.smtp.gateway.SmtpGateway;
import com.github.kwoin.kgate.smtp.message.SmtpRequest;
import com.github.kwoin.kgate.smtp.message.SmtpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author P. WILLEMET
 */
public class SmtpGatewayTest {


    static final SmtpGateway gateway = new SmtpGateway();
    static final StoreMessageCommand<SmtpRequest> requestStore = new StoreMessageCommand<>();
    static final StoreMessageCommand<SmtpResponse> responseStore = new StoreMessageCommand<>();


    @BeforeAll
    static void initAll() {

        SmtpTestUtil.startSmtpServer(25251);
        KGateConfig.getConfig().setProperty("kgate.core.client.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.client.port", "25251");
        KGateConfig.getConfig().setProperty("kgate.core.server.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.server.port", "25252");
        gateway.addClientToServerCommandFactory(() -> requestStore);
        gateway.addClientToServerCommandFactory(LoggerCommand::new);
        gateway.addServerToClientCommandFactory(() -> responseStore);
        gateway.addServerToClientCommandFactory(LoggerCommand::new);

    }


    @AfterAll
    static void tearDownAll() {

        SmtpTestUtil.stopSmtpServer();

    }


    @BeforeEach
    void init() {

        gateway.start();

    }


    @AfterEach
    void tearDown() {

        gateway.stop();

    }


    @Test
    void testSmtpGateway() throws MessagingException, InterruptedException {

        MimeMessage message = SmtpTestUtil.initMessage("emitter@test.fr", "receiver1@test.fr", "receiver2@test.fr");
        message.setText("TEST");
        SmtpTestUtil.sendMessage(message);
        Thread.sleep(500);
        List<SmtpRequest> requests = requestStore.getMessages();
        List<SmtpResponse> responses = responseStore.getMessages();
        assertEquals(7, requests.size());
        assertEquals(8, responses.size());

    }


    static class StoreMessageCommand<T extends Message> implements ICommand<T> {


        private final List<T> messages = new ArrayList<T>();


        @Override
        public void execute(Session<T> session, Chain<T> callingChain, T message) throws IOException {

            messages.add(message);

        }


        public List<T> getMessages() {

            return messages;

        }


    }


}
