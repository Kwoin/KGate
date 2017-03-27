package com.github.kwoin.kgate.sequencing;

/**
 * @author P. WILLEMET
 */
public interface IStateMachine {

    int CUT = -1;
    int STOP = -2;

    int getCurrentStateIndex();

    void setCurrentStateIndex(int currentStateIndex);


}
