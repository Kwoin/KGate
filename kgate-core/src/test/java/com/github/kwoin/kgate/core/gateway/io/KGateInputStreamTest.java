package com.github.kwoin.kgate.core.gateway.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class KGateInputStreamTest {


    @Test
    public void testShort() throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream("kwoin".getBytes());
        KGateInputStream in = new KGateInputStream(bais);

        byte[] buf = new byte[3];
        in.read(buf);
        assertEquals('k', buf[0]);
        assertEquals('w', buf[1]);
        assertEquals('o', buf[2]);

        in.reset();
        buf = new byte[5];
        in.read(buf);
        assertEquals('k', buf[0]);
        assertEquals('w', buf[1]);
        assertEquals('o', buf[2]);
        assertEquals(0, buf[3]);
        assertEquals(0, buf[4]);

        in.clear();
        assertEquals('i', in.read());

    }


    @Test
    public void testLong() throws IOException {

        byte[] test = new byte[1026];
        for (int i = 0; i < test.length; i++) {
            test[i] = (byte)('a' + i);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(test);
        KGateInputStream in = new KGateInputStream(bais);

        byte[] buf = new byte[3];
        in.read(buf);
        assertEquals('a', buf[0]);
        assertEquals('b', buf[1]);
        assertEquals('c', buf[2]);

        in.reset();
        buf = new byte[1026];
        in.read(buf);
        assertEquals('a', buf[0]);
        assertEquals('b', buf[1]);
        assertEquals('c', buf[2]);
        assertEquals(0, buf[3]);
        assertEquals(0, buf[4]);

        in.clear();
        in.read(buf, 3, 1023);
        assertEquals((byte)('a'+1025), buf[1025]);
        assertEquals(-1, in.read());

        buf = new byte[3];
        int len = in.read(buf);
        assertEquals(0, len);

    }


}
