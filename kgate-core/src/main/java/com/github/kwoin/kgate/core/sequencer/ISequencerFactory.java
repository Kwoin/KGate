package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.message.Message;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public interface ISequencerFactory<T extends Message> {


    AbstractSequencer<T> newSequencer(Socket input);


}
