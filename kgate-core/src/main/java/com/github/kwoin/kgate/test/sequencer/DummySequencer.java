package com.github.kwoin.kgate.test.sequencer;

import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class DummySequencer extends AbstractSequencer<Message> {


    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();


    @Override
    protected Message readNextMessage() throws IOException {

        baos.reset();

        int read;
        while((read = session.getInput().getInputStream().read()) != -1) {
            baos.write(read);
            if(read == '!')
                break;
        }

        if(read == -1 || "bye!".equals(baos.toString()))
            hasNext = false;

        return new Message(baos.toByteArray());

    }


    @Override
    protected void resetState() {



    }
}
