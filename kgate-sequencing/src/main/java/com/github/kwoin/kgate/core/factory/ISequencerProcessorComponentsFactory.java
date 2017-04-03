package com.github.kwoin.kgate.core.factory;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public interface ISequencerProcessorComponentsFactory extends IProcessorComponentsFactory {


    void setRequestSequencerCommandComponentsFactory(@Nullable ISequencerCommandComponentsFactory requestSequencerCommandComponentsFactory);

    @Nullable
    ISequencerCommandComponentsFactory getRequestSequencerCommandComponentsFactory();

    void setResponseSequencerCommandComponentsFactory(@Nullable ISequencerCommandComponentsFactory responseSequencerCommandComponentsFactory);

    @Nullable
    ISequencerCommandComponentsFactory getResponseSequencerCommandComponentsFactory();


}
