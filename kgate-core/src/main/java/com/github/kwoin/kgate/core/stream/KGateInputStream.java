package com.github.kwoin.kgate.core.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class KGateInputStream extends FilterInputStream {


    private static final int BUFFER_SIZE = 1024;
    private final List<int[]> buffers = new ArrayList<>();
    private int writeCursor;
    private int readCursor;
    private boolean isBufferizing;


    public KGateInputStream(InputStream inputStream) {

        super(inputStream);
        addBuffer();
        this.readCursor = 0;
        this.isBufferizing = true;

    }


    private void bufferize(int c) {

        if(writeCursor == BUFFER_SIZE)
            addBuffer();

        int[] buffer = buffers.get(buffers.size() - 1);
        buffer[writeCursor++] = c;

    }


    private void addBuffer() {

        buffers.add(new int[BUFFER_SIZE]);
        writeCursor = 0;

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

        if(isBufferizing) {
            c = in.read();
            bufferize(c);
            if( c == -1) {
                isBufferizing = false;
                readCursor = (buffers.size() - 1) * BUFFER_SIZE + writeCursor - 1;
            }
        } else {
            int bufferIndex = readCursor / BUFFER_SIZE;
            int byteIndex = readCursor % BUFFER_SIZE;
            c = buffers.get(bufferIndex)[byteIndex];
            if( c != -1)
                readCursor++;

        }

        return c;

    }


    @Override
    public synchronized void mark(int readlimit) {

        // no op

    }


    @Override
    public synchronized void reset() throws IOException {

        readCursor = 0;

    }


    @Override
    public boolean markSupported() {

        return false;

    }


    @Override
    public void close() throws IOException {

        in.close();

    }



}
