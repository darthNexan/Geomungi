package com.pablo.gameutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 10/02/2018.
 */

public class Tuple2<X,Y> extends Tuple{
    public final X x1;
    public final Y x2;



    public Tuple2(X x1, Y x2){
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public String toString() {
        return "("+ x1.toString() + ", "+ x2.toString() +")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else {
            return o instanceof Tuple2 && ((Tuple2) o).x2.equals(this.x2) && ((Tuple2) o).x1.equals(this.x1);
        }
    }


}
