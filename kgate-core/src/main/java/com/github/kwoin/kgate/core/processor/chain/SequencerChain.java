package com.github.kwoin.kgate.core.processor.chain;

import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.processor.chain.command.ICommandListFactory;
import com.github.kwoin.kgate.core.processor.chain.command.SequencerCommand;

import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class SequencerChain extends DefaultChain {


    public SequencerChain(SequencerCommand sequencer) {

        commandListFactory = new ICommandListFactory() {
            @Override
            public List<ICommand> newCommandList() {
                return Arrays.asList(sequencer);
            }
        };

    }


}
