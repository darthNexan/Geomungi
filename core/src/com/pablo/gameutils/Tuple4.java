package com.pablo.gameutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 26/02/2018.
 */

public class Tuple4 <W,X,Y,Z> {
    public final W x1;
    public final X x2;
    public final Y x3;
    public final Z x4;

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

    @Override
    public boolean equals(Object o) {
        if (o == null)return false;
        else return o instanceof Tuple4 && ((Tuple4) o).x1.equals(this.x1) &&
                ((Tuple4) o).x2.equals(this.x2) && ((Tuple4) o).x3.equals(this.x3) &&
                ((Tuple4) o).x4.equals(this.x4);
    }

    @Override
    public String toString() {
        return "(" + x1 + ", " + x2 + ", " +x3 +", "+x4+")";
    }

}
