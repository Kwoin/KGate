package com.github.kwoin.kgate.smtp.message;

import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public class SmtpCommand extends SmtpRequest {


    private String command;
    private @Nullable String parameters;


    public SmtpCommand(byte[] original, String command, @Nullable String parameters) {

        super(original);
        this.command = command;
        this.parameters = parameters;

    }


    public String getCommand() {

        return command;

    }


    public @Nullable String getParameters() {

        return parameters;

    }



}
