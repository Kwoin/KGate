package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.ESequencerScope;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.factory.ISequencerCommandComponentsFactory;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.sequencing.ESequencerResult;
import com.github.kwoin.kgate.core.sequencing.ISequencer;


/**
 * @author P. WILLEMET
 */
public class SequencerCommand implements ICommand {


    protected ISequencerCommandComponentsFactory sequencerCommandComponentsFactory;


    public SequencerCommand(ISequencerCommandComponentsFactory sequencerCommandComponentsFactory) {

        this.sequencerCommandComponentsFactory = sequencerCommandComponentsFactory;

    }


    @Override
    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

        IContext messageContext = new DefaultContext(IContext.ECoreScope.MESSAGE, context);
        IContext sequencerContext = new DefaultContext(ESequencerScope.SEQUENCER, messageContext);
        ISequencer sequencer = sequencerCommandComponentsFactory.newSequencer(context);
        sequencer.init(sequencerContext, inputPoint);

        ESequencerResult result;
        int read;
        do {

            read = inputPoint.getInputStream().read();
            result = sequencer.push((byte) read);

            if(result != ESequencerResult.CONTINUE) {
                inputPoint.getInputStream().reset();
                IChain chain = result == ESequencerResult.CUT
                        ? sequencerCommandComponentsFactory.onNewMessage(messageContext)
                        : sequencerCommandComponentsFactory.onUnhandledMessage(messageContext);
                chain.run(inputPoint, outputPoint, messageContext, callingChain);
                inputPoint.getInputStream().clear();
                sequencer.reset();
            }

        } while (read != -1);

        // Stream ended prematuraly, data was still expected
        /*
        if(result == ESequencerResult.CONTINUE) {

            inputPoint.getInputStream().reset();
            sequencerCommandComponentsFactory.onUnhandledMessage(context).run(inputPoint, outputPoint, messageContext, callingChain);

        }*/

        outputPoint.close();

    }


}
