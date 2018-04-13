package com.pablo.gameutils;

import java.util.ArrayList;
import java.util.List;

/*
@startuml

Tuple3 <|-- Tuple
class Tuple3<X,Y,Z>{
x1:X
x2:Y
x3:Z

}
@enduml
 */
/**
 * Created by Dennis on 25/02/2018.
 */

public class Tuple3<X,Y,Z> extends Tuple{

    public final X x1;
    public final Y x2;
    public final Z x3;

    public Tuple3(X x1, Y x2, Z x3){
        this.x1 =x1;
        this.x2 =x2;
        this.x3 =x3;
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

    @Override
    public List<Class> getTypes() {
        ArrayList<Class> list = new ArrayList<Class>();
        list.add(x1.getClass());
        list.add(x2.getClass());
        list.add(x3.getClass());
        return list;
    }
}
