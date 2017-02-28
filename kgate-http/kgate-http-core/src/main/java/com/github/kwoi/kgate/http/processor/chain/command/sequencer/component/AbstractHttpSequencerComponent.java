package com.github.kwoi.kgate.http.processor.chain.command.sequencer.component;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractHttpSequencerComponent {


    private final Logger logger = LoggerFactory.getLogger(AbstractHttpSequencerComponent.class);


    protected interface Callback {

        ESequencerResult

    }
}


    protected class Context {

        private State state;

        public void setState(State state) {
            this.state = state;
        }

        public State getState() {
            return state;
        }

    }


    protected interface State {

        ESequencerResult push(byte b, Context context);

    }


    protected class ReadStartLine implements State {

        private boolean cr;
        ByteArrayOutputStream baos;

        public ReadStartLine() {
            cr = false;
            baos = new ByteArrayOutputStream();
        }

        @Override
        public ESequencerResult push(byte b, Context context) {

            if(!cr) {
                if (b == '\r')
                    cr = true;

                baos.write(b);
                return ESequencerResult.CONTINUE;
            } else {
                if (b != '\n') {
                    logger.error("Malformed HTTP start line : contains CR not followed by LF");
                    return ESequencerResult.STOP;

            }


        }
    }


}
