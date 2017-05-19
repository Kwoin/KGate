package com.github.kwoin.kgate.core.context;

import java.util.HashMap;


/**
 * @author P. WILLEMET
 */
public class Context extends HashMap<String, Object> {


    public <T> T getProperty(Object key) {

        return (T) super.get(key);

    }


}
