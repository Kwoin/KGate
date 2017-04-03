package com.github.kwoin.kgate.http.sequencing;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.ESequencerScope;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.gateway.io.KGateInputStream;
import com.github.kwoin.kgate.core.sequencing.ESequencerResult;
import com.github.kwoin.kgate.http.model.HttpRequest;
import com.github.kwoin.kgate.http.model.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class HttpResponseSequencerTest {


    HttpResponseSequencer httpResponseSequencer;
    IContext sequencerContext;


    @BeforeEach
    void init() {

        IContext sessionContext = new DefaultContext(IContext.ECoreScope.SESSION);
        IContext messageContext = new DefaultContext(IContext.ECoreScope.MESSAGE, sessionContext);
        sequencerContext = new DefaultContext(ESequencerScope.SEQUENCER, messageContext);
        httpResponseSequencer = new HttpResponseSequencer();
        httpResponseSequencer.init(sequencerContext, null);
        HttpRequest request = new HttpRequest();
        request.setMethod("GET");
        sequencerContext.setVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD, request);

    }


    KGateInputStream getResource(String resourceName) {

        return new KGateInputStream(HttpResponseSequencerTest.class.getClassLoader().getResourceAsStream(resourceName));

    }


    ESequencerResult read(KGateInputStream in) throws IOException {

        ESequencerResult result = null;
        int read;
        while((read = in.read()) > -1) {
            result = httpResponseSequencer.push((byte) read);
            if(result != ESequencerResult.CONTINUE)
                httpResponseSequencer.reset();
            if(result == ESequencerResult.STOP)
                break;
        }
        return result;

    }


    @Test
    public void testNormalResponse() throws IOException {

        KGateInputStream resource = getResource("normalHttpResponse.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        System.out.println("time : " + (stop - start));
        assertEquals(ESequencerResult.CUT, sequencerResult);
        HttpResponse response = (HttpResponse) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("200", response.getStatusCode()),
                () -> assertEquals("OK", response.getReasonPhrase()),
                () -> assertEquals("HTTP/1.1", response.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("Date", "Mon, 27 Jul 2009 12:28:53 GMT");
                    put("Server", "Apache/2.2.14 (Win32)");
                    put("Last-Modified", "Wed, 22 Jul 2009 19:15:56 GMT");
                    put("Content-Length", "10");
                    put("Content-Type", "text/html");
                    put("Connection", "Closed");
                }}, response.getHeaders()),
                () -> assertEquals("Kwoin!!!!!", response.getBody())
        );

    }


    @Test
    public void testChunkedResponse() throws IOException {

        KGateInputStream resource = getResource("chunkedHttpResponse.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        System.out.println("time : " + (stop - start));
        assertEquals(ESequencerResult.CUT, sequencerResult);
        HttpResponse response = (HttpResponse) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("200", response.getStatusCode()),
                () -> assertEquals("OK", response.getReasonPhrase()),
                () -> assertEquals("HTTP/1.1", response.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("Date", "Mon, 27 Jul 2009 12:28:53 GMT");
                    put("Server", "Apache/2.2.14 (Win32)");
                    put("Last-Modified", "Wed, 22 Jul 2009 19:15:56 GMT");
                    put("Content-Length", "10");
                    put("Content-Type", "text/html");
                    put("Connection", "Closed");
                    put("Transfer-Encoding", "gzip, chunked");
                }}, response.getHeaders()),
                () -> assertEquals("Kwoin!!!!!\r\nKwoin!!!!! kwoin", response.getBody())
        );

    }


    @Test
    public void testNoBodyResponse() throws IOException {

        KGateInputStream resource = getResource("nobodyHttpResponse.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        HttpResponse response = (HttpResponse) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("204", response.getStatusCode()),
                () -> assertEquals("OK", response.getReasonPhrase()),
                () -> assertEquals("HTTP/1.1", response.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("Date", "Mon, 27 Jul 2009 12:28:53 GMT");
                    put("Server", "Apache/2.2.14 (Win32)");
                    put("Last-Modified", "Wed, 22 Jul 2009 19:15:56 GMT");
                    put("Content-Length", "88");
                    put("Content-Type", "text/html");
                    put("Connection", "Closed");
                }}, response.getHeaders()),
                () -> assertEquals("", response.getBody())
        );

    }


    @Test
    public void testMultipleHttpReponses() throws IOException {

        KGateInputStream resource = getResource("multipleHttpResponses.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        HttpResponse response = (HttpResponse) sequencerContext.getVariable(IContext.ECoreScope.SESSION, HttpMessageSequencer.HTTP_LAST_MESSAGE_FIELD);
        assertAll(
                () -> assertEquals("204", response.getStatusCode()),
                () -> assertEquals("OK", response.getReasonPhrase()),
                () -> assertEquals("HTTP/1.1", response.getHttpVersion()),
                () -> assertEquals(new HashMap<String, String>() {{
                    put("Date", "Mon, 27 Jul 2009 12:28:53 GMT");
                    put("Server", "Apache/2.2.14 (Win32)");
                    put("Last-Modified", "Wed, 22 Jul 2009 19:15:56 GMT");
                    put("Content-Length", "88");
                    put("Content-Type", "text/html");
                    put("Connection", "Closed");
                }}, response.getHeaders()),
                () -> assertEquals("", response.getBody())
        );

    }


}
