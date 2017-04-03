package com.github.kwoin.kgate.core.gateway.command.chain;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.gateway.io.KGateInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
class DefaultChainTest {


    int read;
    DefaultChain chain;
    IoPoint point;


    @BeforeEach
    void init() {

        System.setIn(new ByteArrayInputStream("ab".getBytes()));
        point = new MockInputPoint();
        chain = new DefaultChain(
                (inputPoint, outputPoint, context, callingChain) -> read = inputPoint.getInputStream().read(),
                (inputPoint, outputPoint, context, callingChain) -> read = inputPoint.getInputStream().read());

    }


    @Test
    void run() throws Exception {

        chain.run(point, point, null, null);
        assertEquals('a', read);

    }


    @Test
    void interruptChain() throws Exception {

        chain.getCommands().add(0, new ICommand() {
            @Override
            public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {
                callingChain.interruptChain();
            }
        });

        chain.run(point, point, null, null);
        assertEquals(0, read);

    }


    class MockInputPoint implements IoPoint {

        private final KGateInputStream kgis = new KGateInputStream(System.in);

        @Override
        public KGateInputStream getInputStream() throws Exception {

            return kgis;

        }

        @Override
        public OutputStream getOutputStream() throws Exception {

            return null;

        }

        @Override
        public void close() throws IOException {

        }

    }

}