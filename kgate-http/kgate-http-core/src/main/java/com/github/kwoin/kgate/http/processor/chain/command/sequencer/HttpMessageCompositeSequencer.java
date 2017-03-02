package com.github.kwoin.kgate.http.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.processor.chain.command.sequencer.CompositeSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpMessageCompositeSequencer extends CompositeSequencer {


    public HttpMessageCompositeSequencer() {

        super();
        addSequencerComponent(new HttpRequestStateMachineSequencer());
        addSequencerComponent(new HttpResponseStateMachineSequencer());

    }



}
