package com.github.kwoin.kgate.http.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.context.DefaultContext;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.processor.chain.command.sequencer.StateMachineSequencer;
import com.github.kwoin.kgate.core.socket.KGateInputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class HttpMessageStateMachineSequencerTest {


    private static final StateMachineSequencer stateMachineSequencer = new HttpMessageStateMachineSequencer(new DefaultContext(IContext.ECoreScope.APPLICATION));


    private KGateInputStream getResource(String resourceName) {

        return new KGateInputStream(HttpMessageStateMachineSequencerTest.class.getClassLoader().getResourceAsStream(resourceName));

    }

    private void printRead(KGateInputStream in) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        in.reset();
        int read;
        while((read = in.read()) >= 0)
            baos.write(read);

        System.out.println(baos.toString());

    }


    private ESequencerResult read(KGateInputStream in) throws IOException {

        ESequencerResult result = null;
        int read;
        while((read = in.read()) > -1) {
            result = stateMachineSequencer.push((byte) read);
        }
        return result;

    }


    @Test
    public void testMonoContentLengthMessage() throws IOException {

        KGateInputStream resource = getResource("normalHttpMessage.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        Assert.assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        printRead(resource);

    }


    @Test
    public void testMonoChunkedMessage() throws IOException {

        KGateInputStream resource = getResource("chunkedHttpMessage.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        Assert.assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        printRead(resource);

    }


    @Test
    public void testMonoNoBodyResponse() throws IOException {

        KGateInputStream resource = getResource("204HttpResponse.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        Assert.assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        printRead(resource);

    }


    @Test
    public void testMultipleHttpMessages() throws IOException {

        KGateInputStream resource = getResource("multipleHttpMessages.http");
        long start = System.currentTimeMillis();
        ESequencerResult sequencerResult = read(resource);
        long stop = System.currentTimeMillis();
        Assert.assertEquals(ESequencerResult.CUT, sequencerResult);
        System.out.println("time : " + (stop - start));
        printRead(resource);

    }


}
