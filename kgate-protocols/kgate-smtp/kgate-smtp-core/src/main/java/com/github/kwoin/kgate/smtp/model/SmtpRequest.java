package com.github.kwoin.kgate.smtp.model;

/**
 * @author P. WILLEMET
 */
public class SmtpRequest extends SmtpMessage {


    private String command;
    private String params = "";


    public String getCommand() {

        return command;

    }


    public void setCommand(String command) {

        this.command = command;

    }


    public String getParams() {

        return params;

    }


    public void setParams(String params) {

        this.params = params;

    }
}
