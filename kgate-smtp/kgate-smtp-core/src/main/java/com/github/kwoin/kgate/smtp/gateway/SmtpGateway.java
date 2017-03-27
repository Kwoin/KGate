package com.github.kwoin.kgate.smtp.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.DefaultGateway;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.SequencerChain;
import com.github.kwoin.kgate.core.processor.command.SequencerCommand;


/**
 * @author P. WILLEMET
 */
public class SmtpGateway extends DefaultGateway {


    public SmtpGateway() {

        super();

        sourceToTargetChainFactory = new IChainFactory() {
            @Override
            public IChain newChain(IContext context) {
                return new SequencerChain(new SequencerCommand());
            }
        };

    }


}
