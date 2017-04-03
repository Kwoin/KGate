package com.github.kwoin.kgate.core.context;


import javax.annotation.Nullable;


/**
 * @author P. WILLEMET
 */
public interface IContext {


    @Nullable
    Object getVariable(EScope scope, String key);

    void setVariable(EScope scope, String key, @Nullable Object value);

    EScope getScope();


    interface EScope {

        String name();

    }


    enum ECoreScope implements EScope {

        MESSAGE, SESSION, APPLICATION

    }


}
