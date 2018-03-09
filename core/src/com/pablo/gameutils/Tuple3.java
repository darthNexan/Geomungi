package com.pablo.gameutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 25/02/2018.
 */

public class Tuple3<X,Y,Z> {

    public final X x1;
    public final Y x2;
    public final Z x3;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else return o instanceof Tuple3 && ((Tuple3) o).x1.equals(this.x1) && ((Tuple3) o).x2.equals(this.x2) && ((Tuple3) o).x3.equals(this.x3);
    }

    @Override
    public String toString() {
        return "(" + x1 + ", " + x2 + ", " +x3 + ")";

    }


}
