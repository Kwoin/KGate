package com.github.kwoin.kgate.smtp.sequencer;

import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.core.session.SessionManager;
import com.github.kwoin.kgate.smtp.message.SmtpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class SmtpResponseSequencer extends AbstractSequencer<SmtpResponse> {


    private static final byte[] END_OF_RESPONSE = "\r\n".getBytes();


    @Override
    protected SmtpResponse readNextMessage() throws IOException {

        boolean anotherLine = true;

        Integer code = null;
        ByteArrayOutputStream reasonPhraseBaos = new ByteArrayOutputStream();

        while(anotherLine) {

            byte[] codeBytes = readBytes(3);
            if(code == null)
                code = Integer.parseInt(new String(codeBytes));

            byte separator = readByte();
            anotherLine = separator == '-';

            byte[] reasonPhraseBytes = readUntil(END_OF_RESPONSE, anotherLine);
            reasonPhraseBaos.write(reasonPhraseBytes);

        }

        SmtpRequestSequencer requestSequencer = (SmtpRequestSequencer) session.getOppositeSession().getSequencer();
        requestSequencer.setReadData(code == 354);
        requestSequencer.setWaitForResponse(false);

        if(code == 221)
            SessionManager.getInstance().deleteBothSessions(session.getOppositeSession());

        return new SmtpResponse(baos.toByteArray(), code, reasonPhraseBaos.toString());

    }


}
