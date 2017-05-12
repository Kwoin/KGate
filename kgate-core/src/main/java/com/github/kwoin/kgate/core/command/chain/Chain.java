package com.github.kwoin.kgate.core.command.chain;

import com.github.kwoin.kgate.core.command.ICommand;
import com.github.kwoin.kgate.core.message.Message;
import com.github.kwoin.kgate.core.session.Session;

import java.io.IOException;


/**
 * @author P. WILLEMET
 */
public class Chain<T extends Message> implements ICommand<T> {


    protected boolean interrupted;
    protected Iterable<ICommand<T>> commands;


    public Chain(Iterable<ICommand<T>> commands) {

        this.commands = commands;
        interrupted = false;

    }


    @Override
    public void execute(Session<T> session,
                        Chain<T> callingChain,
                        T message) throws IOException {

        for (ICommand<T> command : commands) {

            command.execute(session, this, message);

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
