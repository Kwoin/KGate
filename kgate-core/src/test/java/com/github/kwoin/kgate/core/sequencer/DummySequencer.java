package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.message.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class DummySequencer extends AbstractSequencer<Message> {


    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();


    public DummySequencer(Socket input) {

        super(input);

    }


    @Override
    protected Message readNextMessage() throws IOException {

        baos.reset();

        int read;
        while((read = input.getInputStream().read()) != -1) {
            baos.write(read);
            if(read == '!')
                break;
        }

        if(read == -1)
            input.close();

        return new Message(baos.toByteArray());

    }
}
