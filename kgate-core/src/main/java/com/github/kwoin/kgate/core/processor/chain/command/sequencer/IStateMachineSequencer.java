package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

/**
 * @author P. WILLEMET
 */
public interface IStateMachineSequencer extends ISequencer {

    int CUT = -1;
    int STOP = -2;

    int getCurrentStateIndex();


}
