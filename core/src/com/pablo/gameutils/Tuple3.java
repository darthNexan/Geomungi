package com.pablo.gameutils;

/**
 * Created by Dennis on 25/02/2018.
 */

public class Tuple3<X,Y,Z> {

    private final X x1;
    private final Y x2;
    private final Z x3;
    @SuppressWarnings("SuspiciousNameCombination")
    public Tuple3(X x, Y y, Z z){
        x1= x;
        x2 =y;
        x3=z;
    }


    public X getX1() {
        return x1;
    }

    public Y getX2() {
        return x2;
    }

    public Z getX3() {
        return x3;
    }
}
