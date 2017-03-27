package com.github.kwoin.kgate.core.gateway.input;

import java.io.FilterInputStream;
import java.io.InputStream;


/**
 * @author P. WILLEMET
 */
public abstract class InputPoint extends FilterInputStream {



    protected InputPoint(InputStream in) {

        super(in);

    }


    public abstract void clear();

}
