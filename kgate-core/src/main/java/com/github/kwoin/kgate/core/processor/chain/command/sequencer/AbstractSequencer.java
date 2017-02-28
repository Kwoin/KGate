package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.core.socket.KGateInputStream;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * @author P. WILLEMET
 */
public class AbstractSequencer implements ICommand, ISequencer {


    protected Map<ISequencer, ESequencerResult> sequencerComponents;
    protected IChainFactory onSeparatorChainFactory;
    protected IChainFactory onUnhandledChainFactory;


    public AbstractSequencer() {

        sequencerComponents = new HashMap<>();

    }


    @Override
    public void run(Socket source, Socket target, IContext context, IChain callingChain) throws IOException {

        KGateInputStream in = ((KGateInputStream) source.getInputStream());
        int i;

        while((i = in.read()) != -1) {

           ESequencerResult result = push((byte) i);
           if(result != ESequencerResult.CONTINUE) {
               IContext messageContext = new DefaultContext(IContext.ECoreScope.MESSAGE, context);
               in.reset();
               IChainFactory chainFactory = result == ESequencerResult.STOP
                       ? onUnhandledChainFactory
                       : onSeparatorChainFactory;
               chainFactory.newChain().run(source, target, messageContext, callingChain);
               in.clear();
               resetSequencerComponents();
           }

        }

        in.reset();
        onUnhandledChainFactory.newChain().run(source, target, context, callingChain);

    }


    @Override
    public ESequencerResult push(byte b) {

        ESequencerResult result = ESequencerResult.STOP;
        for (ISequencer sequencer : sequencerComponents.keySet()) {
            if(sequencerComponents.get(sequencer) == ESequencerResult.CONTINUE) {
                ESequencerResult componentResult = sequencer.push(b);
                sequencerComponents.put(sequencer, sequencer.push(b));
                if(componentResult.getPriority() > result.getPriority())
                    result = componentResult;
            }
        }

        return result;

    }


    protected void resetSequencerComponents() {

        for (ISequencer sequencer : sequencerComponents.keySet()) {
            sequencerComponents.put(sequencer, ESequencerResult.CONTINUE);
        }

    }


    public void setOnSeparatorChainFactory(IChainFactory onSeparatorChainFactory) {

        this.onSeparatorChainFactory = onSeparatorChainFactory;

    }


    public void setOnUnhandledChainFactory(IChainFactory onUnhandledChainFactory) {

        this.onUnhandledChainFactory = onUnhandledChainFactory;

    }


    public void addSequencerComponent(ISequencer sequencer) {

        sequencerComponents.put(sequencer, ESequencerResult.CONTINUE);

    }




}
