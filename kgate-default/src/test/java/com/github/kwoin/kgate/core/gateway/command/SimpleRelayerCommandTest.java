package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.gateway.io.KGateInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
class SimpleRelayerCommandTest {


    SimpleRelayerCommand simpleRelayerCommand = new SimpleRelayerCommand();
    MockInputPoint point;
    ByteArrayOutputStream baos;


    @BeforeEach
    void init() {

        ByteArrayInputStream bais = new ByteArrayInputStream("test".getBytes());
        baos = new ByteArrayOutputStream();
        System.setIn(bais);
        System.setOut(new PrintStream(baos));
        point = new MockInputPoint();

    }


    @Test
    void runWithSessionContext() throws Exception {

        simpleRelayerCommand.run(point, point, new DefaultContext(IContext.ECoreScope.SESSION), null);

        String str = baos.toString();
        assertEquals("test", str);
        assertTrue(point.isCloseCalled());

    }

    @Test
    void runWithMessageContext() throws Exception {

        simpleRelayerCommand.run(point, point, new DefaultContext(IContext.ECoreScope.MESSAGE), null);

        String str = baos.toString();
        assertEquals("test", str);
        assertFalse(point.isCloseCalled());

    }


    class MockInputPoint implements IoPoint {

        private final KGateInputStream kgis = new KGateInputStream(System.in);
        private boolean closeCalled;

        @Override
        public KGateInputStream getInputStream() throws Exception {

            return kgis;

        }

        @Override
        public OutputStream getOutputStream() throws Exception {

            return System.out;

        }

        @Override
        public void close() throws IOException {

            closeCalled = true;

        }

        public boolean isCloseCalled() {

            return closeCalled;

        }

    }


}