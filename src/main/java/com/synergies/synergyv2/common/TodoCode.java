package com.synergies.synergyv2.common;

public enum TodoCode {
    Y(1),
    N( 0);

    private int checkInteger;
    TodoCode(int checkInteger) {
        this.checkInteger = checkInteger;
    }

    public int getInteger(){
        return this.checkInteger;
    }
}
