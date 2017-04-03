package com.github.kwoin.kgate.core.gateway.command;

import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import com.github.kwoin.kgate.core.gateway.io.KGateInputStream;
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
class SimpleLoggerCommandTest {


    @Test
    void run() throws Exception {

        SimpleLoggerCommand simpleLoggerCommand = new SimpleLoggerCommand("test");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        ByteArrayInputStream bais = new ByteArrayInputStream("ab".getBytes());
        System.setIn(bais);
        IoPoint point = new MockInputPoint();

        simpleLoggerCommand.run(point, point, null, null);

        String str = baos.toString();
        assertTrue(str.contains("test"));
        assertTrue(str.contains("ab"));

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