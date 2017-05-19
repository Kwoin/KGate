package com.github.kwoin.kgate.smtp.message;


import com.github.kwoin.kgate.core.message.Message;


/**
 * @author P. WILLEMET
 */
public class SmtpResponse extends Message {


    private int code;
    private String reasonPhrase;

    public SmtpResponse(byte[] original, int code, String reasonPhrase) {

        super(original);
        this.code = code;
        this.reasonPhrase = reasonPhrase;

    }


    public int getCode() {

        return code;

    }


    public void setCode(int code) {

        this.code = code;

    }


    public String getReasonPhrase() {

        return reasonPhrase;

    }


    public void setReasonPhrase(String reasonPhrase) {

        this.reasonPhrase = reasonPhrase;

    }


    @Override
    public String toString() {

        String[] reasonPhraseLines = reasonPhrase.split("\r\n");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < reasonPhraseLines.length; i++)
            sb.append(code)
                    .append(i == reasonPhraseLines.length - 1 ? " " : "-")
                    .append(reasonPhraseLines[i]);

        return sb.append("\r\n")
                .toString();

    }


    @Override
    protected byte[] toByteArray() {

        return toString().getBytes();

    }

}
