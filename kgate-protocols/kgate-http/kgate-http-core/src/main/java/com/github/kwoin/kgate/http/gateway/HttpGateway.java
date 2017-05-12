package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.gateway.AbstractGateway;
import com.github.kwoin.kgate.http.message.HttpRequest;
import com.github.kwoin.kgate.http.message.HttpResponse;
import com.github.kwoin.kgate.http.sequencer.HttpRequestSequencer;
import com.github.kwoin.kgate.http.sequencer.HttpResponseSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpGateway extends AbstractGateway<HttpRequest, HttpResponse> {


    public HttpGateway() {

        clientToServerSequencerFactory = HttpRequestSequencer::new;
        serverToClientSequencerFactory = HttpResponseSequencer::new;

    }


}
