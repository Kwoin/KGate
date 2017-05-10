package com.github.kwoin.kgate.smtp.sequencer;

import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.smtp.message.SmtpCommand;
import com.github.kwoin.kgate.smtp.message.SmtpData;
import com.github.kwoin.kgate.smtp.message.SmtpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class SmtpRequestSequencer extends AbstractSequencer<SmtpRequest> {


    private final Logger logger = LoggerFactory.getLogger(SmtpRequestSequencer.class);
    private static final byte[] END_OF_DATA = "\r\n.\r\n".getBytes();
    private static final byte[] END_OF_COMMAND = "\r\n".getBytes();
    private boolean readData;
    private boolean waitForResponse;


    @Override
    protected SmtpRequest readNextMessage() throws IOException {

        while(waitForResponse);

        return readData ? readSmtpData() : readSmtpCommand();

    }


    public void setWaitForResponse(boolean waitForResponse) {

        this.waitForResponse = waitForResponse;

    }


    public void setReadData(boolean readData) {

        this.readData = readData;

    }


    private SmtpData readSmtpData() throws IOException {

        byte[] mailBytes = readUntil(END_OF_DATA, false);

        Message message = null;
        try {
            message = new MimeMessage(null, new ByteArrayInputStream(mailBytes));
        } catch (MessagingException e) {
            logger.error("Unexpected error : incorrect Mail format");
        }

        readData = false;
        return new SmtpData(baos.toByteArray(), message);

    }


    private SmtpCommand readSmtpCommand() throws IOException {

        byte[] commandBytes = readUntil(END_OF_COMMAND, false);

        String smtpCommand = new String(commandBytes);
        String[] splittedSmtpCommand = smtpCommand.split(" ", 2);
        String command = splittedSmtpCommand[0];
        String parameters = splittedSmtpCommand.length == 2 ? splittedSmtpCommand[1] : null;

        if(command.equals("DATA"))
            waitForResponse = true;

        return new SmtpCommand(baos.toByteArray(), command, parameters);

    }


}
