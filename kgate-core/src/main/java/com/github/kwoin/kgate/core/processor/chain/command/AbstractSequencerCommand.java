package com.github.kwoin.kgate.core.processor.chain.command;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ISequencer;
import com.github.kwoin.kgate.core.socket.KGateInputStream;
import com.github.kwoin.kgate.core.socket.KGateSocket;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class AbstractSequencerCommand implements ICommand {


    protected ISequencerFactory sequencerFactory;
    protected IChainFactory onSeparatorChainFactory;
    protected IChainFactory onUnhandledChainFactory;


    public AbstractSequencerCommand(ISequencerFactory sequencerFactory, IChainFactory onSeparatorChainFactory, IChainFactory onUnhandledChainFactory) {

        this.sequencerFactory = sequencerFactory;
        this.onSeparatorChainFactory = onSeparatorChainFactory;
        this.onUnhandledChainFactory = onUnhandledChainFactory;

    }


    @Override
    public void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException {

        ISequencer sequencer = sequencerFactory.newSequencer(context);
        KGateInputStream in = ((KGateInputStream) source.getInputStream());
        int i;

        while((i = in.read()) != -1) {

           ESequencerResult result = sequencer.push((byte) i);

           if(result != ESequencerResult.CONTINUE) {
               IContext messageContext = new DefaultContext(IContext.ECoreScope.MESSAGE, context);
               in.reset();
               IChainFactory chainFactory = result == ESequencerResult.STOP
                       ? onUnhandledChainFactory
                       : onSeparatorChainFactory;
               chainFactory.newChain().run(source, target, messageContext, callingChain);
               in.clear();
           }

        }

        in.reset();
        onUnhandledChainFactory.newChain().run(source, target, context, callingChain);

    }


    public void setOnSeparatorChainFactory(IChainFactory onSeparatorChainFactory) {

        this.onSeparatorChainFactory = onSeparatorChainFactory;

    }


    public void setOnUnhandledChainFactory(IChainFactory onUnhandledChainFactory) {

        this.onUnhandledChainFactory = onUnhandledChainFactory;

    }


    public interface ISequencerFactory {


        ISequencer newSequencer(IContext context);


    }


}
