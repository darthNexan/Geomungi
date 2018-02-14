package com.pablo.gameutils;

import com.pablo.game.GameInfo;

import java.util.Random;

/**
 * Created by Dennis on 07/02/2018.
 */

public class BasicGameType {



    public int gameCategory;//General eg parallel lines, plane shape, angle, etc.
    public int shapeType; //type of triangle, quadrilateral, pentagon, hexagon, septagon,octagon,nonagon, decagon
    public int angleType;  //obtuse, acute, right angle
    public int specializedCategory; //for use with scalene, equilateral, isosceles, right angle, acute and obtuse triangles
    //also for types of quadrilaterals parallelogram, rhombus, square, trapezoid, kite, rectangle

    /**
     * Returns a fully constructed game mode
     */
    public BasicGameType(){
        Random random = GameInfo.random;
        gameCategory=-1;
        shapeType=-1;
        angleType=-1;
        specializedCategory=-1;

        gameCategory = random.nextInt(3);

        if(gameCategory == 1){
            shapeType = random.nextInt(8);
            if (shapeType == 0 || shapeType ==1){
                specializedCategory = random.nextInt(6);
            }
        }
        else if(gameCategory ==2){
            angleType = random.nextInt(3);
        }

    }//BasicGameType Constructor


    public boolean isParallel(){
        return gameCategory == 0;
    }

    public boolean isShape(){
        return gameCategory ==1;
    }

    public boolean isAngle(){
        return gameCategory ==2;
    }



    public boolean isAcute(){
        return angleType==0;
    }

    public boolean isObtuse(){
        return angleType==1;
    }

    public boolean isRight(){
        return angleType==2;
    }



    public boolean isTriangle(){
        return shapeType==0;
    }

    public boolean isQuad(){
        return shapeType==1;
    }

    public boolean isPentagon(){
        return shapeType==2;
    }

    public boolean isHexagon(){
        return shapeType==3;
    }

    public boolean isSeptagon(){
        return shapeType==4;
    }

    public boolean isOctagon(){
        return shapeType==5;
    }

    public boolean isNonagon(){
        return shapeType==6;
    }

    public boolean isDecagon(){
        return shapeType==7;
    }


    /**
     *
     * @return indicates where the shape is a square or equilateral triangle
     */
    public boolean isType0(){
        return specializedCategory ==0;
    }

    /**
     * @return indicates whether the shape is an isosceles or a rectangle
     */
    public boolean isType1(){

        return specializedCategory ==1;

    }

    /**
     *
     * @return indicates whether the shape is a kite or a scalene triangle
     */
    public boolean isType2(){
        return specializedCategory==2;
    }

    /**
     *
     * @return indicates whether the shape is a rhombus or a acute triangle
     */
    public boolean isType3(){
        return specializedCategory ==3;
    }

    /**
     *
     * @return indicates whether the shape is a parallelogram or a obtuse triangle
     */
    public boolean isType4(){

        return specializedCategory ==4;
    }

    /**
     *
     * @return indicates whether the shape is a trapezium or a right angle triange
     */

    public boolean isType5(){
        return specializedCategory==5;
    }

}//BasicGameType

