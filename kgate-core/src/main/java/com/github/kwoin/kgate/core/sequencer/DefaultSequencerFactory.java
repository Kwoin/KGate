package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public class DefaultSequencerFactory implements ISequencerFactory {


    protected Class<? extends ISequencer> sequencerClass;


    public DefaultSequencerFactory(Class<? extends ISequencer> sequencerClass) {

        this.sequencerClass = sequencerClass;

    }


    @Override
    public ISequencer newSequencer(IContext context) {

        try {
            return sequencerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
