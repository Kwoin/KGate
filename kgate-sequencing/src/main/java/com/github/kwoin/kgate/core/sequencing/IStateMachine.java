package com.github.kwoin.kgate.core.sequencing;

/**
 * @author P. WILLEMET
 */
public interface IStateMachine {

    int CUT = -1;
    int STOP = -2;

    int getCurrentStateIndex();

    void setCurrentStateIndex(int currentStateIndex);


}
