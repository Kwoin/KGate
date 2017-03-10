package com.github.kwoin.kgate.http.processor.command;

import com.github.kwoin.kgate.core.context.EDirection;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.command.ICommand;
import com.github.kwoin.kgate.core.socket.KGateSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class HttpReadRequestMethodCommand implements ICommand {


    public static String REQUEST_METHOD_FIELD = "http.request.method";


    @Override
    public void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException {

        if(context.getVariable(IContext.ECoreScope.SESSION, EDirection.DIRECTION_FIELD) == EDirection.REQUEST) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read;
            while((read = source.getInputStream().read()) != ' ')
                baos.write(read);
            context.setVariable(IContext.ECoreScope.SESSION, REQUEST_METHOD_FIELD, baos.toString());

        }

    }

}
