package com.github.kwoin.kgate.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author P. WILLEMET
 */
public class DefaultContext implements IContext {


    private final Logger logger = LoggerFactory.getLogger(DefaultContext.class);
    protected IContext upperContext;
    protected Map<String, Object> variables;
    protected EScope scope;


    public DefaultContext(EScope scope) {

        this(scope, null);

    }


    public DefaultContext(EScope scope, IContext upperContext) {

        this.scope = scope;
        this.upperContext = upperContext;
        variables = new HashMap();

    }


    @Override
    @Nullable
    public Object getVariable(EScope scope, String key) {

        return scope == this.scope || upperContext == null
                ? variables.get(key)
                : upperContext.getVariable(scope, key);

    }


    @Override
    public void setVariable(EScope scope, String key, @Nullable Object value) {

        if(scope == this.scope)
            variables.put(key, value);
        else if(upperContext != null)
            upperContext.setVariable(scope, key, value);
        else
            logger.warn("Scope " + scope + " unreachable! Ignoring setting for key " + key);

    }


    @Override
    public EScope getScope() {

        return scope;

    }

}
