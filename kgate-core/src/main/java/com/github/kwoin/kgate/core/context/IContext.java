package com.github.kwoin.kgate.core.context;

/**
 * @author P. WILLEMET
 */
public interface IContext {


    Object getVariable(EScope scope, String key);

    void setVariable(EScope scope, String key, Object value);


    interface EScope {

        String name();

    }


    enum ECoreScope implements EScope {

        MESSAGE, SESSION, APPLICATION

    }


}
