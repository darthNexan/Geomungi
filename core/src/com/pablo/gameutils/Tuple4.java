package com.pablo.gameutils;

/**
 * Created by Dennis on 26/02/2018.
 */

public class Tuple4 <W,X,Y,Z>{
    private W x1;
    private X x2;
    private Y x3;
    private Z x4;

    public W getX1() {
        return x1;
    }

    public X getX2() {
        return x2;
    }

    public Y getX3() {
        return x3;
    }

    public Z getX4() {
        return x4;
    }

    public Tuple4(W x1, X x2, Y x3, Z x4) {

        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
    }
}
