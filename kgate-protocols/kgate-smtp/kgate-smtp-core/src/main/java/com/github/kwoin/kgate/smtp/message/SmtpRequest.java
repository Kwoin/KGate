package com.github.kwoin.kgate.smtp.message;

import com.github.kwoin.kgate.core.message.Message;


/**
 * @author P. WILLEMET
 */
public abstract class SmtpRequest extends Message {


    public SmtpRequest(byte[] original) {

        super(original);

    }


}
