package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.factory.ISequencerCommandComponentsFactory;
import com.github.kwoin.kgate.core.gateway.DefaultSequencerGateway;
import com.github.kwoin.kgate.core.gateway.ISequencerGateway;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleLoggerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.command.chain.IChain;
import com.github.kwoin.kgate.core.gateway.io.IoPoint;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author P. WILLEMET
 */
public class SequencerTest {


    @Test
    public void testSequencer() throws IOException, KGateServerException, InterruptedException {

        ISequencerGateway gateway = new DefaultSequencerGateway();
        gateway.getGatewayFactorySet().getProcessorComponentsFactory().setRequestSequencerCommandComponentsFactory(new MockSequencerCommandComponentsFactory());

        ServerSocket server = new ServerSocket(KGateConfig.getConfig().getInt("kgate.core.client.port"));
        server.setSoTimeout(1000);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket target = server.accept();
                    int i;
                    while((i = target.getInputStream().read()) != -1)
                        System.out.println(Character.valueOf((char)i));
                    System.out.println(Character.valueOf((char)i));
                    target.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        gateway.start();

        Socket source = new Socket(KGateConfig.getConfig().getString("kgate.core.server.host"), KGateConfig.getConfig().getInt("kgate.core.server.port"));
        source.getOutputStream().write("Je suis le méch@nt Test".getBytes());
        source.getOutputStream().write("|MOUH@H@@HAAHA@|@|@AAA@!".getBytes());
        source.close();

        t.join();

        gateway.stop();
        server.close();
        source.close();

        assertEquals("|", gateway.getContext().getVariable(IContext.ECoreScope.APPLICATION, "test.newMessage"));
        assertEquals("AAA@", gateway.getContext().getVariable(IContext.ECoreScope.APPLICATION, "test.unhandledMessage"));

    }


    class MockSequencerCommandComponentsFactory implements ISequencerCommandComponentsFactory {


        @Override
        public ISequencer newSequencer(IContext context) {

            return new MockSequencer();

        }


        @Override
        public IChain onNewMessage(IContext context) {

            return new DefaultChain(
                    new SimpleLoggerCommand("new Message"),
                    new SaveInContextCommand("test.newMessage"),
                    new SimpleRelayerCommand()
            );

        }


        @Override
        public IChain onUnhandledMessage(IContext context) {

            return new DefaultChain(
                    new SimpleLoggerCommand("Unhandled Message"),
                    new SaveInContextCommand("test.unhandledMessage"),
                    new SimpleRelayerCommand()
            );

        }

    }


    class MockSequencer implements ISequencer {


        @Override
        public void init(IContext context, IoPoint ioPoint) {

        }


        @Override
        public void reset() {

        }


        @Override
        public IContext getContext() {

            return null;
        }


        @Override
        public IoPoint getInputPoint() {

            return null;
        }


        @Override
        public ESequencerResult push(byte b) {

            switch(b) {
                case '|':
                    return ESequencerResult.CUT;
                case '@':
                    return ESequencerResult.STOP;
                case '!':
                    return ESequencerResult.CONTINUE;
                default:
                    return ESequencerResult.CONTINUE;
            }

        }

    }


    class SaveInContextCommand implements ICommand {


        private String field;


        public SaveInContextCommand(String field) {

            this.field = field;

        }

        @Override
        public void run(IoPoint inputPoint, IoPoint outputPoint, IContext context, IChain callingChain) throws Exception {

            byte[] buf = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            while((len = inputPoint.getInputStream().read(buf)) > 0)
                baos.write(buf, 0, len);

            context.setVariable(IContext.ECoreScope.APPLICATION, field, baos.toString());

        }

    }


}
