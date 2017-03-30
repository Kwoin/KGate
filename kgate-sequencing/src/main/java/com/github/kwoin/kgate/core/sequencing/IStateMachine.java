package com.github.kwoin.kgate.core.sequencing;

import com.github.kwoin.kgate.core.sequencing.state.AbstractState;


/**
 * @author P. WILLEMET
 */
public interface IStateMachine {

    int CUT = -1;
    int STOP = -2;

    int getCurrentStateIndex();

    void setCurrentStateIndex(int currentStateIndex);

    AbstractState[] getStates();


}
