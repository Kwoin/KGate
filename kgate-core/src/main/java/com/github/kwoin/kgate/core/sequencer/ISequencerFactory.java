package com.github.kwoin.kgate.core.sequencer;

/**
 * @author P. WILLEMET
 */
public interface ISequencerFactory<T> {


    AbstractSequencer<T> newSequencer();


}
