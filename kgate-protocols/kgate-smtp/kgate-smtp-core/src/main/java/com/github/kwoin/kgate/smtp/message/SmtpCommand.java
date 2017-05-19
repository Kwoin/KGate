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


    public void setCommand(String command) {

        this.command = command;

    }


    public @Nullable String getParameters() {

        return parameters;

    }


    public void setParameters(@Nullable String parameters) {

        this.parameters = parameters;

    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder()
                .append(command);

        if(parameters != null)
            sb.append(" ").append(parameters);

        return sb.append("\r\n")
                .toString();

    }


    @Override
    protected byte[] toByteArray() {

        return toString().getBytes();

    }
}
