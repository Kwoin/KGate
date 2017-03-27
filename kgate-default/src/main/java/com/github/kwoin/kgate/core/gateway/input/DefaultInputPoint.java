package com.github.kwoin.kgate.core.gateway.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class DefaultInputPoint extends InputPoint {


    private static final int BUFFER_SIZE = 1024;
    private final List<int[]> buffers = new ArrayList<>();
    private int writeCursor;
    private int readCursor;
    private boolean useBuffer;


    public DefaultInputPoint(InputStream inputStream) {

        super(inputStream);
        clear();

    }


    private void bufferize(int c) {

        if(writeCursor == BUFFER_SIZE)
            addBuffer();

        int[] buffer = buffers.get(writeCursor / BUFFER_SIZE);
        buffer[writeCursor % BUFFER_SIZE] = c;
        writeCursor++;

    }


    private void addBuffer() {

        buffers.add(new int[BUFFER_SIZE]);

    }


    @Override
    public long skip(long n) throws IOException {

        return 0;

    }


    @Override
    public int read(byte[] b, int off, int len) throws IOException {

        if (b == null)
            throw new NullPointerException();
        else if (off < 0 || len < 0 || len > b.length - off)
            throw new IndexOutOfBoundsException();
        else if (len == 0)
            return 0;

        int c;
        int k = 0;
        while(k < len) {
            c = read();
            if(c == -1)
                break;
            b[k + off] = (byte) c;
            k++;
        }

        return k;

    }


    @Override
    public int read() throws IOException {

        int c = -1;

        if(useBuffer) {
            int bufferIndex = readCursor / BUFFER_SIZE;
            int byteIndex = readCursor % BUFFER_SIZE;
            c = buffers.get(bufferIndex)[byteIndex];
            if(c != -1)
                readCursor++;
        } else {
            c = in.read();
            bufferize(c);
        }

        return c;

    }


    @Override
    public synchronized void reset() throws IOException {

        if(!useBuffer)
            bufferize(-1);

        readCursor = 0;
        useBuffer = true;

    }


    @Override
    public void close() throws IOException {

        in.close();

    }


    @Override
    public int available() throws IOException {

        return useBuffer ? writeCursor : in.available();

    }


    public void clear() {

        buffers.clear();
        addBuffer();
        writeCursor = 0;
        readCursor = 0;
        useBuffer = false;

    }



}
