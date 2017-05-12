package com.github.kwoin.kgate.smtp.message;

import javax.mail.Message;


/**
 * @author P. WILLEMET
 */
public class SmtpData extends SmtpRequest {


    private Message mail;


    public SmtpData(byte[] original, Message mail) {

        super(original);
        this.mail = mail;

    }


    public Message getMail() {

        return mail;

    }


}
