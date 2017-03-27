package com.github.kwoin.kgate.core.processor.command;

import com.github.kwoin.kgate.core.context.IContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author P. WILLEMET
 */
public class DefaultCommandListFactory implements ICommandListFactory {


    private List<ICommand> commandList = new ArrayList<>();


    @Override
    public List<ICommand> newCommandList(IContext context) {

        return Arrays.asList(new SimpleRelayerCommand());

    }

}
