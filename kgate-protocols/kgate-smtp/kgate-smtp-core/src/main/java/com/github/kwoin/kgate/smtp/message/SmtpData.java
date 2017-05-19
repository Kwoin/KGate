package com.github.kwoin.kgate.smtp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class SmtpData extends SmtpRequest {


    private final Logger logger = LoggerFactory.getLogger(SmtpData.class);
    private final Message mail;


    public SmtpData(byte[] original, Message mail) {

        super(original);
        this.mail = mail;

    }


    public Message getMail() {

        return mail;

    }


    @Override
    protected byte[] toByteArray() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            mail.writeTo(baos);
            baos.write("\r\n.\r\n".getBytes());
        } catch (IOException | MessagingException e) {
            logger.error("Should not happen", e);
        }
        return baos.toByteArray();

    }
}
