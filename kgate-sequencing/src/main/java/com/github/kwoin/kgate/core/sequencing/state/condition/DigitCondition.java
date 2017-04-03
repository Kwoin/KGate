package com.github.kwoin.kgate.core.sequencing.state.condition;

/**
 * @author P. WILLEMET
 */
public class DigitCondition implements ICondition {


    private static final byte ZERO = (byte)'0';
    private static final byte NINE = (byte)'9';


    @Override
    public boolean accept(byte b) {

        return b >= ZERO && b <= NINE;

    }
}
