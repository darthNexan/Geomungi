package com.pablo.gameutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 26/02/2018.
 */

/*
@startuml
Tuple4<|-- Tuple
class Tuple4<W,X,Y,Z>{
x1:W
x2:X
x3:Y
x4:Z
}
@enduml
 */

public class Tuple4 <W,X,Y,Z> extends Tuple{
    public final W x1;
    public final X x2;
    public final Y x3;
    public final Z x4;


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

    @Override
    public List<Class> getTypes(){
        ArrayList<Class> list = new ArrayList<Class>();
        list.add(x1.getClass());
        list.add(x2.getClass());
        list.add(x3.getClass());
        list.add(x4.getClass());

        return list;

    }
}
