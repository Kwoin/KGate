package com.github.kwoin.kgate.smtp.processor.command.sequencer;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.sequencer.ISequencer;

import java.util.Arrays;


/**
 * @author P. WILLEMET
 */
public class SmtpReplySequencer implements ISequencer {


    private static final byte[] WAIT_FOR_INPUT_CODE = "354".getBytes();
    private static final byte[] END_LINE = "\r\n".getBytes();

    private IContext context;
    private int cursor;
    private int endLineCursor;
    private byte[] code;
    private boolean cutAtEndLine;


    @Override
    public void start(IContext context) {

        this.context = context;
        cursor = 0;
        endLineCursor = 0;
        code = new byte[3];
        cutAtEndLine = true;

    }


    @Override
    public ESequencerResult push(byte b) {

        ESequencerResult result = ESequencerResult.CONTINUE;

        if(cutAtEndLine && cursor < 3)
            code[cursor++] = b;
        else if(cursor == 4)
            cutAtEndLine = b == ' ';
        else {
            if(b == END_LINE[endLineCursor])
                endLineCursor++;
            if(endLineCursor == END_LINE.length)
                if(cutAtEndLine) {
                    result = ESequencerResult.CUT;
                    if(Arrays.equals(code, WAIT_FOR_INPUT_CODE))
                        context.setVariable(IContext.ECoreScope.SESSION, "kgate.smtp.sequencer.status", 1);
                }
                else
                    reset();
        }

        return result;

    }


    @Override
    public void reset() {

        cutAtEndLine = true;
        cursor = 0;
        endLineCursor = 0;

    }

}
