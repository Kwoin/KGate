package com.github.kwoin.kgate.core.sequencer;

import com.github.kwoin.kgate.core.context.IContext;


/**
 * @author P. WILLEMET
 */
public class SessionSequencerFactory extends DefaultSequencerFactory {


    private static final String SEQUENCER_FIELD = "kgate.core.sequencer.instance";


    public SessionSequencerFactory(Class<? extends ISequencer> sequencerClass) {

        super(sequencerClass);

    }


    @Override
    public ISequencer newSequencer(IContext context) {


        ISequencer sequencer = (ISequencer) context.getVariable(IContext.ECoreScope.SESSION, SEQUENCER_FIELD);
        if(sequencer == null) {
            sequencer = super.newSequencer(context);
            context.setVariable(IContext.ECoreScope.SESSION, SEQUENCER_FIELD, sequencer);
        }

        return sequencer;

    }

}
