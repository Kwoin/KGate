package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.command.LoggerCommand;
import com.github.kwoin.kgate.test.DummyServer;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author P. WILLEMET
 */
public class HttpGatewayTest {

    private final Logger logger = LoggerFactory.getLogger(HttpGatewayTest.class);
    static final HttpGateway gateway = new HttpGateway();


    @BeforeAll
    static void initAll() {

        KGateConfig.getConfig().setProperty("kgate.core.client.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.client.port", "8080");
        KGateConfig.getConfig().setProperty("kgate.core.server.host", "127.0.0.1");
        KGateConfig.getConfig().setProperty("kgate.core.server.port", "80");
        gateway.addClientToServerCommandFactory(LoggerCommand::new);
        gateway.addServerToClientCommandFactory(LoggerCommand::new);

    }


    @AfterEach
    void tearDown() {

        gateway.stop();

    }


    @Test
    void testRequestSequencer() throws IOException, URISyntaxException, InterruptedException {

        DummyServer server = new DummyServer();
        ServerCallback callback = new ServerCallback("normalHttpResponse.http");
        URL url = HttpGatewayTest.class.getClassLoader().getResource("normalHttpResponse.http");
        File file = new File(url.toURI());
        int responseSize = ((int) file.length());
        byte[] responseBuffer = new byte[responseSize];

        InputStream in = HttpGatewayTest.class.getClassLoader().getResourceAsStream("normalHttpRequest.http");
        byte[] bytes = IOUtils.toByteArray(in);
        callback.setBytes(bytes.length);
        server.start(callback, false);
        gateway.start();
        Socket socket = new Socket("127.0.0.1", 80);
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
        socket.getInputStream().read(responseBuffer);
        String received = callback.getResult();
        assertEquals(bytes.length, received.length());

        in = HttpGatewayTest.class.getClassLoader().getResourceAsStream("chunkedHttpRequest.http");
        bytes = IOUtils.toByteArray(in);
        callback.setBytes(bytes.length);
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
        socket.getInputStream().read(responseBuffer);
        received = callback.getResult();
        assertEquals(bytes.length, received.length());

        in = HttpGatewayTest.class.getClassLoader().getResourceAsStream("nobodyHttpRequest.http");
        bytes = IOUtils.toByteArray(in);
        callback.setBytes(bytes.length);
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
        socket.getInputStream().read(responseBuffer);
        received = callback.getResult();
        assertEquals(bytes.length, received.length());

        server.stop();

    }


    @Test
    void testResponseSequencer() throws URISyntaxException, IOException {

        DummyServer server = new DummyServer();
        ServerCallback callback = new ServerCallback("chunkedHttpResponse.http");
        InputStream responseIn = HttpGatewayTest.class.getClassLoader().getResourceAsStream("chunkedHttpResponse.http");
        byte[] expectedResponse = IOUtils.toByteArray(responseIn);
        byte[] responseBuffer = new byte[expectedResponse.length];

        InputStream in = HttpGatewayTest.class.getClassLoader().getResourceAsStream("normalHttpRequest.http");
        byte[] httpRequestbytes = IOUtils.toByteArray(in);
        callback.setBytes(httpRequestbytes.length);
        server.start(callback, false);
        gateway.start();
        Socket socket = new Socket("127.0.0.1", 80);
        socket.getOutputStream().write(httpRequestbytes);
        socket.getOutputStream().flush();
        socket.getInputStream().read(responseBuffer);
        assertArrayEquals(expectedResponse, responseBuffer);

        callback.setReturnFileName("nobodyHttpResponse.http");
        responseIn = HttpGatewayTest.class.getClassLoader().getResourceAsStream("nobodyHttpResponse.http");
        expectedResponse = IOUtils.toByteArray(responseIn);
        responseBuffer = new byte[expectedResponse.length];
        socket.getOutputStream().write(httpRequestbytes);
        socket.getOutputStream().flush();
        socket.getInputStream().read(responseBuffer);
        assertArrayEquals(expectedResponse, responseBuffer);

        server.stop();

    }


    @Test
    void testRealCase() throws Exception {

        KGateConfig.getConfig().setProperty("kgate.core.client.host", "172.217.16.206");
        KGateConfig.getConfig().setProperty("kgate.core.client.port", "80");

        gateway.start();
        HttpClient client = new HttpClient();
        client.start();
        ContentResponse contentResponse = client.GET("http://127.0.0.1/");
        assertNotNull(contentResponse);
        contentResponse = client.newRequest("http://127.0.0.1/")
                .method("HEAD")
                .send();
        assertNotNull(contentResponse);

        client.stop();


    }


    class ServerCallback implements DummyServer.Callback {


        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private int bytes;
        private String result;
        private String returnFileName;


        public ServerCallback(String returnFileName) {

            this.returnFileName = returnFileName;

        }


        public void setBytes(int bytes) {

            this.bytes = bytes;

        }


        public String getResult() {

            return result;

        }


        public void setReturnFileName(String returnFileName) {

            this.returnFileName = returnFileName;

        }


        @Override
        public void execute(Socket socket) {

            try {
                while (true) {
                    for (int i = 0; i < bytes; i++)
                        baos.write(socket.getInputStream().read());
                    result = baos.toString();
                    baos.reset();
                    InputStream in = HttpGatewayTest.class.getClassLoader().getResourceAsStream(returnFileName);
                    socket.getOutputStream().write(IOUtils.toByteArray(in));
                }
            } catch (SocketException e) {
                logger.debug("Socket has been closed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
