package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.chain.IChain;
import com.github.kwoin.kgate.core.gateway.socket.KGateSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


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
    public void run(KGateSocket source, KGateSocket target, IContext context, IChain callingChain) throws IOException {

        byte[] buf = new byte[BUFFER_SIZE];
        int len;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((len = source.getInputStream().read(buf)) > 0)
            baos.write(buf, 0, len);

        context.setVariable(scope, contextField, baos.toString());


    }


}
