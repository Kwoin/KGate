package com.github.kwoin.kgate.smtp.processor.command.sequencer;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.sequencing.sequencer.ESequencerResult;
import com.github.kwoin.kgate.sequencing.sequencer.ISequencer;


/**
 * @author P. WILLEMET
 */
public class SmtpRequestSequencer implements ISequencer {


    private static final byte[] REQUEST_END = "\r\n".getBytes();
    private static final byte[] DATA_END = "\r\n.\r\n".getBytes();

    private byte[] endSequence;
    private int cursor;
    private IContext context;



    @Override
    public void start(IContext context) {

        this.context = context;
        cursor = 0;
        endSequence = (Integer) context.getVariable(IContext.ECoreScope.SESSION, "kgate.smtp.sequencer.status") == 1
                ? DATA_END
                : REQUEST_END;

    }


    @Override
    public ESequencerResult push(byte b) {

        ESequencerResult result = ESequencerResult.CONTINUE;

        if(b == endSequence[cursor])
            cursor++;

        if(cursor == endSequence.length) {

            if(endSequence == DATA_END)
                context.setVariable(IContext.ECoreScope.SESSION, "kgate.smtp.sequencer.status", 3);

            result = ESequencerResult.CUT;

        }

        return result;

    }


    @Override
    public void reset() {

    }
}
