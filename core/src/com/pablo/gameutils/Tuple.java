package com.pablo.gameutils;

import java.util.List;

/**
 * Created by Dennis on 30/03/2018.
 */

/*
@startuml
abstract class Tuple{
getTypes:List<Class>
}
Tuple --|> Tuple4
Tuple --|> Tuple3
Tuple --|> Tuple2

@enduml
 */

public abstract class Tuple {

    public abstract List<Class> getTypes();
}
