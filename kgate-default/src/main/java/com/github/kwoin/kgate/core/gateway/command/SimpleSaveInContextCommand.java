package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;

import java.io.ByteArrayOutputStream;


/**
 * @author P. WILLEMET
 */
public class SimpleSaveInContextCommand implements ICommand {


    protected IContext.EScope scope;
    protected String contextField;
    private static final int BUFFER_SIZE = 1024;


    public SimpleSaveInContextCommand(IContext.EScope scope, String contextField) {

        this.scope = scope;
        this.contextField = contextField;

    }

    @Override
    public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

        byte[] buf = new byte[BUFFER_SIZE];
        int len;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((len = inputPoint.getInputStream().read(buf)) > 0)
            baos.write(buf, 0, len);

        context.setVariable(scope, contextField, baos.toString());


    }


}
