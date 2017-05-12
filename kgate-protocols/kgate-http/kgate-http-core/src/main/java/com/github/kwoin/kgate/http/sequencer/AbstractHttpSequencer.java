package com.github.kwoin.kgate.http.sequencer;

import com.github.kwoin.kgate.core.sequencer.AbstractSequencer;
import com.github.kwoin.kgate.http.message.HttpHeader;
import com.github.kwoin.kgate.http.message.HttpMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public abstract class AbstractHttpSequencer<T extends HttpMessage> extends AbstractSequencer<T> {


    private static final byte[] END_OF_FIRST_LINE = "\r\n".getBytes();
    private static final byte[] END_OF_HEADERS = "\r\n\r\n".getBytes();
    private static final String HEADERS_SEPARATOR = "\r\n";
    private static final byte[] CHUNK_SEPARATOR = "\r\n".getBytes();

    protected boolean chunked;
    protected int contentLength;


    @Override
    protected T readNextMessage() throws IOException {

        String firstLine = new String(readUntil(END_OF_FIRST_LINE, false));
        String[] headerLines = new String(readUntil(END_OF_HEADERS, false)).split(HEADERS_SEPARATOR);
        HttpHeader[] headers = new HttpHeader[headerLines.length];
        for (int i = 0; i < headerLines.length; i++) {
            String[] splittedHeaderLine = headerLines[i].split("\\s*:\\s*", 2);
            headers[i] = new HttpHeader(splittedHeaderLine[0], splittedHeaderLine[1]);
        }

        String content = null;
        if(hasContent(firstLine)) {

            resolveReadContentMode(headers);

            if (chunked) {

                ByteArrayOutputStream contentBaos = new ByteArrayOutputStream();
                int chunkSize = 0;
                do {
                    String chunkIntro = new String(readUntil(CHUNK_SEPARATOR, false));
                    String chunkSizeStr = chunkIntro.split(" ")[0];
                    chunkSize = Integer.parseInt(chunkSizeStr, 16);
                    byte[] chunkContent = readBytes(chunkSize);
                    contentBaos.write(chunkContent);
                    readBytes(CHUNK_SEPARATOR.length);
                } while (chunkSize != 0);
                content = contentBaos.toString();

            } else {

                content = new String(readBytes(contentLength));

            }

        }

        byte[] original = baos.toByteArray();
        return newHttpMessage(original, firstLine, headers, content);

    }


    @Override
    protected void resetState() {

        contentLength = 0;
        chunked = false;

    }


    protected void resolveReadContentMode(HttpHeader[] headers) {

        for (HttpHeader header : headers) {
            if(header.getKey().equalsIgnoreCase("content-length"))
                contentLength = Integer.parseInt(header.getValue());
            if(header.getKey().equalsIgnoreCase("transfer-encoding"))
                chunked = header.getValue().matches(".*?chunked\\s*$");
        }

    }


    protected abstract boolean hasContent(String firstLine);


    protected abstract T newHttpMessage(byte[] original, String firstLine, HttpHeader[] headers, String content);


}
