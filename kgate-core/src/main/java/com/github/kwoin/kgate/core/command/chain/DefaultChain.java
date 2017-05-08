package com.github.kwoin.kgate.core.command.chain;

import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.message.Message;

import java.net.Socket;


/**
 * @author P. WILLEMET
 */
public class DefaultChain<T extends Message> implements ICommand<T> {


    protected boolean interrupted;
    protected Iterable<ICommand<T>> commands;


    public DefaultChain(Iterable<ICommand<T>> commands) {

        this.commands = commands;
        interrupted = false;

    }


    @Override
    public void execute(Socket input,
                        Socket output,
                        DefaultChain<T> callingChain,
                        T message) {

        for (ICommand<T> command : commands) {

            command.execute(input, output, this, message);

            if(interrupted) {
                if(callingChain != null)
                    callingChain.interrupt();
                break;
            }

        }

    }


    public void interrupt() {

        interrupted = true;

    }


}
