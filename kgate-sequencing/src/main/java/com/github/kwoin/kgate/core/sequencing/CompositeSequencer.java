package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class CompositeSequencer implements ISequencer {


    private List<ISequencer> sequencersComponents;
    private List<ISequencer> copy;
    private IContext context;
    private IoPoint inputPoint;


    public CompositeSequencer() {

        sequencersComponents = new ArrayList<>();
        copy = new ArrayList<>();

    }


    public CompositeSequencer addSequencerComponent(ISequencer sequencerComponent) {

        sequencersComponents.add(sequencerComponent);
        return this;

    }


    @Override
    public void init(IContext context, IoPoint inputPoint) {

        this.context = context;
        this.inputPoint = inputPoint;

        Collections.copy(copy, sequencersComponents);

        for (ISequencer sequencersComponent : sequencersComponents)
            sequencersComponent.init(context, inputPoint);

    }


    @Override
    public void reset() {

        Collections.copy(copy, sequencersComponents);

        for (ISequencer sequencersComponent : sequencersComponents)
            sequencersComponent.reset();

    }


    @Override
    public IContext getContext() {

        return context;

    }


    @Override
    public IoPoint getInputPoint() {

        return inputPoint;

    }


    @Override
    public ESequencerResult push(byte b) {

        ESequencerResult result = ESequencerResult.STOP;

        Iterator<ISequencer> it = copy.iterator();

        ISequencer sequencer;
        ESequencerResult sequencerResult;
        while(it.hasNext()) {

            sequencer = it.next();
            sequencerResult = sequencer.push(b);

            if(sequencerResult == ESequencerResult.STOP)
                it.remove();

            if(sequencerResult.getPriority() > result.getPriority())
                result = sequencerResult;

        }

        if(result != ESequencerResult.CONTINUE)
            Collections.copy(copy, sequencersComponents);

        return result;

    }


}
