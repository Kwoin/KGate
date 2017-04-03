package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.ESequencerScope;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.KGateInputStream;
import com.github.kwoin.kgate.core.sequencing.ESequencerResult;
import com.github.kwoin.kgate.http.model.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class HttpRequestSequencerTest {


    HttpRequestSequencer httpRequestSequencer;
    IContext sequencerContext;


    @BeforeEach
    void init() {

        IContext sessionContext = new DefaultContext(IContext.ECoreScope.SESSION);
        IContext messageContext = new DefaultContext(IContext.ECoreScope.MESSAGE, sessionContext);
        sequencerContext = new DefaultContext(ESequencerScope.SEQUENCER, messageContext);
        httpRequestSequencer = new HttpRequestSequencer();
        httpRequestSequencer.init(sequencerContext, null);

    }


    KGateInputStream getResource(String resourceName) {

        return new KGateInputStream(HttpRequestSequencerTest.class.getClassLoader().getResourceAsStream(resourceName));

    }


    ESequencerResult read(KGateInputStream in) throws IOException {

        ESequencerResult result = null;
        int read;
        while((read = in.read()) > -1) {
            result = httpRequestSequencer.push((byte) read);
            if(result != ESequencerResult.CONTINUE)
                httpRequestSequencer.reset();
            if(result == ESequencerResult.STOP)
                break;
        }
        return result;

    }


    @Test
    public void testNormalRequest() throws IOException {

        KGateInputStream resource = getResource("normalHttpRequest.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        System.out.println("time : " + (stop - start));
        assertEquals(ESequencerResult.CUT, sequencerResult);
        HttpRequest request = (HttpRequest) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("POST", request.getMethod()),
                () -> assertEquals("/cgi-bin/process.cgi", request.getRequestUri()),
                () -> assertEquals("HTTP/1.1", request.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("User-Agent", "Mozilla/4.0");
                    put("Host", "www.tutorialspoint.com");
                    put("Content-Type", "application/x-www-form-urlencoded");
                    put("Content-Length", "49");
                    put("Accept-Language", "en-us");
                    put("Accept-Encoding", "gzip, deflate");
                    put("Connection", "Keep-Alive");
                }}, request.getHeaders()),
                () -> assertEquals("licenseID=string&content=string&/paramsXML=string", request.getBody())
        );

    }


    @Test
    public void testChunkedRequest() throws IOException {

        KGateInputStream resource = getResource("chunkedHttpRequest.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        System.out.println("time : " + (stop - start));
        assertEquals(ESequencerResult.CUT, sequencerResult);
        HttpRequest request = (HttpRequest) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("POST", request.getMethod()),
                () -> assertEquals("/cgi-bin/process.cgi", request.getRequestUri()),
                () -> assertEquals("HTTP/1.1", request.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("User-Agent", "Mozilla/4.0");
                    put("Host", "www.tutorialspoint.com");
                    put("Content-Type", "application/x-www-form-urlencoded");
                    put("Accept-Language", "en-us");
                    put("Accept-Encoding", "gzip, deflate");
                    put("Connection", "Keep-Alive");
                    put("Transfer-Encoding", "chunked");
                }}, request.getHeaders()),
                () -> assertEquals("kwoin\r\nkwoin.", request.getBody())
        );

    }


    @Test
    public void testNoBodyRequest() throws IOException {

        KGateInputStream resource = getResource("nobodyHttpRequest.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        HttpRequest request = (HttpRequest) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("GET", request.getMethod()),
                () -> assertEquals("/cgi-bin/process.cgi", request.getRequestUri()),
                () -> assertEquals("HTTP/1.1", request.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("User-Agent", "Mozilla/4.0");
                    put("Host", "www.tutorialspoint.com");
                    put("Content-Type", "application/x-www-form-urlencoded");
                    put("Accept-Language", "en-us");
                    put("Accept-Encoding", "gzip, deflate");
                    put("Connection", "Keep-Alive");
                }}, request.getHeaders()),
                () -> assertEquals("", request.getBody())
        );

    }


    @Test
    public void testMultipleHttpMessages() throws IOException {

        KGateInputStream resource = getResource("multipleHttpRequests.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        HttpRequest request = (HttpRequest) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("GET", request.getMethod()),
                () -> assertEquals("/cgi-bin/process.cgi", request.getRequestUri()),
                () -> assertEquals("HTTP/1.1", request.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("User-Agent", "Mozilla/4.0");
                    put("Host", "www.tutorialspoint.com");
                    put("Content-Type", "application/x-www-form-urlencoded");
                    put("Accept-Language", "en-us");
                    put("Accept-Encoding", "gzip, deflate");
                    put("Connection", "Keep-Alive");
                }}, request.getHeaders()),
                () -> assertEquals("", request.getBody())
        );

    }


    @Test
    public void testInvalidRequest() throws IOException {

        KGateInputStream resource = getResource("badHttpRequest.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        assertEquals(ESequencerResult.STOP, sequencerResult);

    }

}
