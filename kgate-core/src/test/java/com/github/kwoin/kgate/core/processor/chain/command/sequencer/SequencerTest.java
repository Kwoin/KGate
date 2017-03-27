package com.github.kwoin.kgate.core.processor.chain.command.sequencer;

import com.github.kwoin.kgate.core.configuration.KGateConfig;
import com.github.kwoin.kgate.core.context.IContext;
import com.github.kwoin.kgate.core.ex.KGateServerException;
import com.github.kwoin.kgate.core.gateway.DefaultGateway;
import com.github.kwoin.kgate.core.gateway.IGateway;
import com.github.kwoin.kgate.core.gateway.chain.DefaultChain;
import com.github.kwoin.kgate.core.gateway.chain.IChain;
import com.github.kwoin.kgate.core.gateway.chain.IChainFactory;
import com.github.kwoin.kgate.core.gateway.chain.SequencerChain;
import com.github.kwoin.kgate.core.gateway.command.ICommand;
import com.github.kwoin.kgate.core.gateway.command.ICommandListFactory;
import com.github.kwoin.kgate.core.gateway.command.SequencerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleLoggerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleRelayerCommand;
import com.github.kwoin.kgate.core.gateway.command.SimpleSaveInContextCommand;
import com.github.kwoin.kgate.core.sequencer.ESequencerResult;
import com.github.kwoin.kgate.core.sequencer.ISequencer;
import com.github.kwoin.kgate.core.sequencer.ISequencerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class SequencerTest {


    @Test
    public void testSequencer() throws IOException, KGateServerException, InterruptedException {

        IGateway gateway = new DefaultGateway();
        gateway.setSourceToTargetChainFactory(new IChainFactory() {
            @Override
            public IChain newChain(IContext context) {
                return new SequencerChain(new MockSequencer());
            }
        });

        ServerSocket server = new ServerSocket(KGateConfig.getConfig().getInt("kgate.core.client.port"));
        server.setSoTimeout(1000);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket target = server.accept();
                    int i;
                    while((i = target.getInputStream().read()) != '!')
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
        source.getOutputStream().write("|MOUH@H@@HAAHA@|@|@AA|!".getBytes());
        source.close();

        t.join();

        gateway.stop();
        server.close();
        source.close();

        Assert.assertEquals("!", gateway.getContext().getVariable(IContext.ECoreScope.APPLICATION, "test.unhandled"));
        Assert.assertEquals("AA|", gateway.getContext().getVariable(IContext.ECoreScope.APPLICATION, "test.separator"));

    }


    public class MockSequencer extends SequencerCommand {


        public MockSequencer() {

            super(new ISequencerFactory() {
                @Override
                public ISequencer newSequencer(IContext context) {
                    return new ISequencer() {
                        @Override
                        public void start(IContext context) {

                        }


                        @Override
                        public ESequencerResult push(byte b) {

                            switch(b) {
                                case '|':
                                    return ESequencerResult.CUT;
                                case '@':
                                    return ESequencerResult.STOP;
                                default:
                                    return ESequencerResult.CONTINUE;
                            }

                        }


                        @Override
                        public void reset() {

                        }
                    };

                }

            }, new IChainFactory() {
                        @Override
                        public IChain newChain(IContext context) {
                            IChain chain = new DefaultChain();
                            chain.setCommandListFactory(new ICommandListFactory() {
                                @Override
                                public List<ICommand> newCommandList(IContext context1) {
                                    return Arrays.asList(
                                            new SimpleLoggerCommand(),
                                            new SimpleSaveInContextCommand(IContext.ECoreScope.APPLICATION, "test.separator"),
                                            new SimpleRelayerCommand()
                                    );
                                }
                            });
                            return chain;
                        }
             }, new IChainFactory() {
                @Override
                public IChain newChain(IContext context) {
                    IChain chain = new DefaultChain();
                    chain.setCommandListFactory(new ICommandListFactory() {
                        @Override
                        public List<ICommand> newCommandList(IContext context1) {
                            return Arrays.asList(
                                    new SimpleLoggerCommand(),
                                    new SimpleSaveInContextCommand(IContext.ECoreScope.APPLICATION, "test.unhandled"),
                                    new SimpleRelayerCommand()
                            );
                        }
                    });
                    return chain;
                }
            });


        }


    }


}
