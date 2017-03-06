package com.github.kwoin.kgate.http.processor.chain;

import com.github.kwoin.kgate.core.processor.chain.SequencerChain;
import com.github.kwoin.kgate.core.processor.chain.command.ICommand;
import com.github.kwoin.kgate.http.processor.chain.command.HttpSequencerCommand;

import java.util.List;


/**
 * @author P. WILLEMET
 */
public class HttpChain extends SequencerChain {


    private List<ICommand> commands;


    public HttpChain(List<ICommand> commands) {

        super(new HttpSequencerCommand(commands));
        this.commands = commands;

    }


    @Override
    public List<ICommand> getCommands() {

        return commands;

    }

}
