package com.github.kwoin.kgate.http.gateway;

import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.processor.chain.DefaultChain;
import com.github.kwoin.kgate.core.processor.chain.IChain;
import com.github.kwoin.kgate.core.processor.chain.IChainFactory;
import com.github.kwoin.kgate.core.processor.command.ICommand;
import com.github.kwoin.kgate.core.processor.command.ICommandListFactory;
import com.github.kwoin.kgate.core.processor.command.SimpleLoggerCommand;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpGatewayTest {


    private static final Server server = new Server(7071);


    @BeforeClass
    public static void beforeClass() throws Exception {

        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
                httpServletResponse.setContentType("text/html; charset=utf-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.getWriter().println("<h1>Hello World</h1>");
                request.setHandled(true);
            }
        });
        server.start();

    }


    @AfterClass
    public static void afterClass() throws Exception {

        server.stop();

    }


    @Test
    public void testSimpleGateway() throws Exception {

        HttpGateway gateway = new HttpGateway();
        gateway.setHttpChainFactory(new IChainFactory() {
            @Override
            public IChain newChain(IContext context) {
                IChain chain = new DefaultChain();
                chain.setCommandListFactory(new ICommandListFactory() {
                    @Override
                    public List<ICommand> newCommandList(IContext context) {
                        return Arrays.asList(new SimpleLoggerCommand());
                    }
                });
                return chain;
            }
        });

        gateway.start();

        HttpClient client = new HttpClient();
        client.start();
        ContentResponse cr = client.GET("http://127.0.0.1:7070");
        Assert.assertEquals("<h1>Hello World</h1>\r\n", cr.getContentAsString());

        client.stop();
        gateway.stop();

    }


}
