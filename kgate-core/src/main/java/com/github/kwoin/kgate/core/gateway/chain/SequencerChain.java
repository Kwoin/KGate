package com.github.kwoin.kgate.core.gateway.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.ICommandListFactory;
import com.github.kwoin.kgate.core.gateway.command.SequencerCommand;

import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class SequencerChain extends DefaultChain {


    public SequencerChain(SequencerCommand sequencer) {

        commandListFactory = new ICommandListFactory() {
            @Override
            public List<ICommand> newCommandList(IContext context) {
                return Arrays.asList(sequencer);
            }
        };

    }



}
