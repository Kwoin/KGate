package com.github.kwoin.kgate.sequencing;

import com.github.kwoin.kgate.core.context.IContext;

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


    public CompositeSequencer() {

        sequencersComponents = new ArrayList<>();
        copy = new ArrayList<>();

    }


    public CompositeSequencer addSequencerComponent(ISequencer sequencerComponent) {

        sequencersComponents.add(sequencerComponent);
        return this;

    }


    @Override
    public void init(IContext context) {

        Collections.copy(copy, sequencersComponents);

        for (ISequencer sequencersComponent : sequencersComponents)
            sequencersComponent.init(context);

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
