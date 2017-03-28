package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.gateway.SequencerKGateComponentsFactory;
import com.github.kwoin.kgate.http.sequencing.HttpMessageStateMachineSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpGatewayComponentsFactory extends SequencerKGateComponentsFactory {


    public HttpGatewayComponentsFactory() {

        super(HttpMessageStateMachineSequencer.class);

    }

}
