package com.github.kwoin.kgate.smtp;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;
import org.subethamail.smtp.server.SMTPServer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * @author P. WILLEMET
 */
public class SmtpTestUtil {


    private static SMTPServer smtpServer;
    private static List<MimeMessage> messages = new ArrayList<>();


    public static void startSmtpServer(int port) {

        smtpServer = new SMTPServer(messageContext ->

            new MessageHandler() {
                @Override
                public void from(String s) throws RejectException {

                }


                @Override
                public void recipient(String s) throws RejectException {

                }


                @Override
                public void data(InputStream inputStream) throws RejectException, TooMuchDataException, IOException {

                    try {

                        messages.add(new MimeMessage(Session.getDefaultInstance(System.getProperties()), inputStream));

                    } catch (MessagingException ex) {
                        throw new IOException(ex);
                    }

                }


                @Override
                public void done() {

                }
            }

        );

        smtpServer.setPort(port);
        smtpServer.start();

    }


    public static void stopSmtpServer() {

        smtpServer.stop();

    }


    public static List<MimeMessage> getReceivedMessages() {

        return messages;

    }


    public static void clearReceivedMessages() {

        messages.clear();

    }


    public static MimeMessage initMessage(String emitter, String... recipients) throws MessagingException {

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", KGateConfig.getConfig().getProperty("kgate.core.server.host"));
        prop.put("mail.smtp.port", KGateConfig.getConfig().getProperty("kgate.core.server.port"));
        Session session = Session.getDefaultInstance(prop);
        MimeMessage resultat = new MimeMessage(session);

        resultat.setFrom(new InternetAddress(emitter));

        InternetAddress[] recipientsAddresses = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++)
            recipientsAddresses[i] = new InternetAddress(recipients[i]);

        resultat.setRecipients(Message.RecipientType.TO, recipientsAddresses);

        return resultat;

    }


    public static void sendMessage(MimeMessage message) throws MessagingException {

        message.setSentDate(new Date());

        Transport.send(message);

    }


}
