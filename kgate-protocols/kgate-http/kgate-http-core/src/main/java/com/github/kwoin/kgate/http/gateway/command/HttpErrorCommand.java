package com.github.kwoin.kgate.http.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.http.model.HttpResponse;
import com.github.kwoin.kgate.http.sequencing.HttpMessageSequencer;


/**
 * @author P. WILLEMET
 */
public class HttpErrorCommand implements ICommand {


    @Override
    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

        HttpResponse response = (HttpResponse) context.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_SEQUENCER_ERROR_FIELD);
        if(response == null) {

            response = new HttpResponse();
            response.setHttpVersion("HTTP/1.1");
            response.setStatusCode("500");
            response.setReasonPhrase("SyMaPri Internal Error");

        }

        for (byte b : response.toString().getBytes()) {
            inputPoint.getOutputStream().write(b);
        }

        inputPoint.close();
        outputPoint.close();

    }


}
