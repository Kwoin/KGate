package com.github.kwoin.kgate.core.processor.command.sequencer;

/**
 * @author P. WILLEMET
 */
public interface ISequencer {


    ESequencerResult push(byte b);

    void reset();

}
