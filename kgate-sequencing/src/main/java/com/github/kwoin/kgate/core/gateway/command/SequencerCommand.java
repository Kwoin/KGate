package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.gateway.io.KGateInputStream;
import com.github.kwoin.kgate.sequencing.ESequencerResult;
import com.github.kwoin.kgate.sequencing.ISequencer;


/**
 * @author P. WILLEMET
 */
public class SequencerCommand implements ICommand {


    protected ISequencerComponentsFactory sequencerKGateComponentFactory;
    protected ISequencer sequencer;


    public SequencerCommand(ISequencerComponentsFactory sequencerKGateComponentFactory, ISequencer sequencer) {

        this.sequencerKGateComponentFactory = sequencerKGateComponentFactory;
        this.sequencer = sequencer;

    }


    @Override
    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

        sequencer.init(context);

        KGateInputStream in = ((KGateInputStream) inputPoint.getInputStream());

        int i;
        while((i = in.read()) != -1) {

           ESequencerResult result = sequencer.push((byte) i);

           if(result != ESequencerResult.CONTINUE) {
               IContext messageContext = new DefaultContext(IContext.ECoreScope.MESSAGE, context);
               in.reset();
               IChain chain = result == ESequencerResult.STOP
                       ? sequencerKGateComponentFactory.onNewMessage(context)
                       : sequencerKGateComponentFactory.onUnhandledMessage(context);
               chain.run(inputPoint, outputPoint, messageContext, callingChain);
               in.clear();
           }

        }

//        in.reset();
//        onUnhandledChainFactory.newChain(context).run(inputPoint, outputPoint, context, callingChain);

    }


}
