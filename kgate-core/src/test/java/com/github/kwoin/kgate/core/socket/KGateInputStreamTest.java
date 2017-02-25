package com.github.kwoin.kgate.core.socket;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;


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
        Assert.assertEquals('k', buf[0]);
        Assert.assertEquals('w', buf[1]);
        Assert.assertEquals('o', buf[2]);

        in.reset();
        buf = new byte[5];
        in.read(buf);
        Assert.assertEquals('k', buf[0]);
        Assert.assertEquals('w', buf[1]);
        Assert.assertEquals('o', buf[2]);
        Assert.assertEquals('i', buf[3]);
        Assert.assertEquals('n', buf[4]);
        Assert.assertEquals(-1, in.read());

        in.reset();
        in.clear();
        Assert.assertEquals(-1, in.read());

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
        Assert.assertEquals('a', buf[0]);
        Assert.assertEquals('b', buf[1]);
        Assert.assertEquals('c', buf[2]);

        in.reset();
        buf = new byte[1026];
        in.read(buf);
        Assert.assertEquals('a', buf[0]);
        Assert.assertEquals('b', buf[1]);
        Assert.assertEquals('c', buf[2]);
        Assert.assertEquals('d', buf[3]);
        Assert.assertEquals('e', buf[4]);
        Assert.assertEquals((byte)('a'+1025), buf[1025]);
        Assert.assertEquals(-1, in.read());

        in.reset();
        in.clear();
        Assert.assertEquals(-1, in.read());

        buf = new byte[3];
        int len = in.read(buf);
        Assert.assertEquals(0, len);

    }


}
