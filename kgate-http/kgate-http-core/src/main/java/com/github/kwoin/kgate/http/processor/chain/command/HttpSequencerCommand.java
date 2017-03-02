package com.github.kwoin.kgate.http.processor.chain.command;

import com.github.kwoin.kgate.core.processor.chain.command.AbstractSequencerCommand;
import com.github.kwoin.kgate.http.processor.chain.command.sequencer.HttpMessageCompositeSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpSequencerCommand extends AbstractSequencerCommand {


    public HttpSequencerCommand() {

        super(new HttpMessageCompositeSequencer());

    }


}
