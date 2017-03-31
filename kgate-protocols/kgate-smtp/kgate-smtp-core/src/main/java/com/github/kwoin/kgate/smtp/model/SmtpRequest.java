package com.github.kwoin.kgate.smtp.model;

/**
 * @author P. WILLEMET
 */
public class SmtpRequest extends SmtpMessage {


    private String command;
    private String commandValue = "";


    public String getCommand() {

        return command;

    }


    public void setCommand(String command) {

        this.command = command;

    }


    public String getCommandValue() {

        return commandValue;

    }


    public void setCommandValue(String commandValue) {

        this.commandValue = commandValue;

    }
}
