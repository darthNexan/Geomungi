package com.pablo.gameutils;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Dennis on 18/02/2018.
 * This class is used for generating points used withing the game.
 */

public class ShapeGeneration {


    /**
     * Used to generate points given a basic game type
     * @param type the basic game type to be generated
     * @return Points that define the curent puzzle
     * @exception IllegalArgumentException if the basic game type is not correctly defined
     */
    public static Vector<Vector2> generate(BasicGameType  type) throws IllegalArgumentException{
        
        Vector<Vector2> res;
        if (type.isParallel()){
            float angle = 40f + GameInfo.random.nextInt(50);
            res = generateParallelogram(angle,50f,50f);

        }
        else if (type.isAngle()){
            
            float angle = type.isAcute()? GameInfo.random.nextInt(20) + 40 : type.isObtuse()?
                    GameInfo.random.nextInt(60) + 100 : 90f;
            res= generateTriangle(40f,40f,angle);
        }
        else if (type.isShape()){

            if (type.isTriangle()){
                if (type.isType0()) {
                    Gdx.app.log("Type is:", "Acute Angle");
                    res = generateTriangle(50f,50f,60);
                }
                else if (type.isType1()){
                    float angle = 40f + GameInfo.random.nextInt(50);
                    res = generateTriangle(50f,50f,angle);
                }
                else if (type.isType2()){
                    float angle = 90f + GameInfo.random.nextInt(20);
                    res = generateTriangle(50f,35f, angle);
                }
                else if (type.isType3()){
                    float angle = 20f + GameInfo.random.nextInt(30);
                    res = generateTriangle(60,60,angle);
                }
                else if (type.isType4()){
                    float angle = 100f + GameInfo.random.nextInt(50);
                    res = generateTriangle(60,50,angle);
                }
                else if (type.isType5()){
                    float side0 = GameInfo.random.nextInt(30) + 30f;
                    float side1 =GameInfo.random.nextInt(30) + 30f;
                    res = generateTriangle(side0,side1,90f);
                }
                else{
                    res = generateShape(3);
                }
            }
            else if (type.isQuad()){
                if (type.isType0()){
                    res = generateParallelogram(90f,50f,50f);
                }
                else if (type.isType1()){
                    res = generateParallelogram(90f,50f,60f);
                }
                else if (type.isType2()){
                    res = generateKitePoints(40f,60f);
                }
                else if (type.isType3()){
                    float angle = 40f + GameInfo.random.nextInt(50);
                    res = generateParallelogram(angle,50f,50f);
                }
                else if (type.isType4()){
                    float angle = 40f + GameInfo.random.nextInt(50);
                    res = generateParallelogram(angle,40f,60f);
                }
                else if (type.isType5()){
                    res = generateTrapezium(40f,60f,50f);
                }
                else{

                    res = generateShape(4);
                }
            }
            else {
                res = generateShape(type.shapeType);
            }
        }
        else throw new IllegalArgumentException("Basic Game Type is not correctly defined");

        addPoints(res,2);
        return res;
    }//generate
    /**
     * @return a list of points that make up a pair of parallel lines
     */
    public static Vector<Vector2> generateParallelPoints(){
        int i = 0;
        Random random = GameInfo.random;
        Vector<Vector2> vec = new Vector<Vector2>();

        Vector2 identity = new Vector2(1,0);
        final int Scale =(int)Math.floor(GameInfo.CAMERA_WIDTH/2);


        // System.out.println(i);

        while (i<=2){


            Vector2 temp0 = new Vector2();
            Vector2 temp1 = new Vector2();


            temp0.x = random.nextInt( (int)GameInfo.CAMERA_WIDTH );//selects a point within the fov
            temp0.y = random.nextInt( (int)GameInfo.CAMERA_HEIGHT);

//           // temp = findSection(useXSection);

            temp1.x =temp0.x;
            temp1.y =temp0.y;

            do {
                temp1.x =temp0.x;
                temp1.y =temp0.y;
                identity.x =1;
                identity.y =0;

                identity.scl(Scale);

                identity.rotate(random.nextInt(360));
                temp1.add(identity);

            }while (!checkVisibility(temp1));//ensures that the point is within the screen



            vec.add(temp0);
            vec.add(temp1);


            Vector2 temp2 = new Vector2(temp0.x,temp0.y);
            Vector2 temp3 = new Vector2(temp1.x, temp1.y);


            do {

                temp2.x = temp0.x;
                temp2.y = temp0.y;
                temp3.x = temp1.x;
                temp3.y = temp1.y;

                identity.x =1;
                identity.y=1;

                identity.scl(Scale/2);
                identity.rotate(random.nextInt(360));
                temp2.add(identity);
                temp3.add(identity);
                if(checkVisibility(temp2) && checkVisibility(temp3)) {

                    vec.add(temp2);
                    vec.add(temp3);

                    break;
                }

            }while (true);
            i+=2;

        }





        return vec;
    }//parallel points

    /**
     * TODO finish this function so that the shape can be of any size and orientation
     * @param length
     * @param width
     * @return
     */
    private static Vector<Vector2> generateKitePoints(float length, float width){
        Vector<Vector2> v = new Vector<Vector2>();
        Vector2 temp0, temp1,temp2,temp3;

        do {
            float angleToUse = (float) GameInfo.random.nextInt(360);
            angleToUse = 0f;
            int scale = (int) GameInfo.CAMERA_WIDTH / 5;
            float topAngle = (float) GameInfo.random.nextInt(25) + 20;
            Vector2 lineRight = new Vector2(1, 0);
            Vector2 lineLeft = new Vector2(1, 0);
            lineRight.scl(scale);
            lineLeft.scl(scale);
            lineRight.rotate(topAngle + 270);
            lineLeft.rotate(270 - topAngle);

            temp0 = new Vector2(GameInfo.random.nextInt(6 * (int) GameInfo.CAMERA_WIDTH) / 8 +
                    GameInfo.CAMERA_WIDTH / 8, GameInfo.random.nextInt((int) GameInfo.CAMERA_HEIGHT - (int) GameInfo.CAMERA_HEIGHT / 2) +
                    GameInfo.CAMERA_HEIGHT / 2);

            temp1 = new Vector2(temp0.x, temp0.y);
            temp1.sub(0, scale * 2.2f);

            temp2 = new Vector2(temp0.x, temp0.y);
            temp2.add(lineRight);
            temp3 = new Vector2(temp0.x, temp0.y);
            temp3.add(lineLeft);
        }while (!checkVisibility(temp0) || !checkVisibility(temp1) || !checkVisibility(temp2) ||!checkVisibility(temp3));

        v.add(temp0);
        v.add(temp1);
        v.add(temp2);
        v.add(temp3);

        return v;
    }//generateKite

    /**
     * Generates Parallelogram
     * @param angle0
     * @param base
     * @param height
     * @return
     */

    public static Vector<Vector2> generateParallelogram(float angle0,  final float base, final float height){


        Vector2 translationVectorX = new Vector2(base,0);
        Vector<Vector2> vector2s = new Vector<Vector2>();

        Vector2 temp0 = new Vector2( GameInfo.random.nextInt((int)GameInfo.CAMERA_WIDTH),
                GameInfo.random.nextInt((int)GameInfo.CAMERA_HEIGHT));
        Vector2 temp1 = new Vector2(0, 0);
        Vector2 temp2 = new Vector2(0, 0);
        Vector2 temp3 = new Vector2(0, 0);
        do {
            float angleInUse = GameInfo.random.nextInt(45);
            vector2s.clear();
            translationVectorX.set(base,0);
            //noinspection SuspiciousNameCombination
            Vector2 translationVectorY = new Vector2(height, 0);

            translationVectorY.rotate(angleInUse);
            translationVectorY.rotate(angle0);

            translationVectorX.rotate(angleInUse);

            temp0.set( GameInfo.random.nextInt((int)GameInfo.CAMERA_WIDTH),
                    GameInfo.random.nextInt((int)GameInfo.CAMERA_HEIGHT));
            temp1.set(0, 0);
            temp2.set(0, 0);
            temp3.set(0, 0);

            temp1.add(temp0).add(translationVectorX);
            temp2.add(temp0).add(translationVectorY);
            temp3.add(temp1).add(translationVectorY);


            vector2s.add(temp0);
            vector2s.add(temp1);
            vector2s.add(temp2);
            vector2s.add(temp3);
        }while (!checkAllVisibility(vector2s));
        return vector2s;

    }

    /**
     * Generates trapezium
     * @param a
     * @param b
     * @param height
     * @return
     */
    public static Vector<Vector2> generateTrapezium (float a, float b, float height) {

        Vector<Vector2> v = new Vector<Vector2>();


        Vector2 topLine = new Vector2(a,0);

        Vector2 bottomLine = new Vector2(b,0);

        Vector2 h = new Vector2(0, height);
        Vector2 temp0 = new Vector2(0,0);
        Vector2 temp1 = new Vector2(0,0);
        Vector2 temp2 = new Vector2(0f,0f);
        Vector2 temp3 = new Vector2(0f,0f);


        do {
            float angleToUse = 45*GameInfo.random.nextFloat();

            float x = GameInfo.random.nextInt((int)GameInfo.CAMERA_WIDTH);
            float y = GameInfo.random.nextInt( (int)GameInfo.CAMERA_HEIGHT);
            topLine.x=a;
            topLine.y=0;

            bottomLine.x = b;
            bottomLine.y =0;

            h.x=0;
            h.y=height;
            h.rotate(angleToUse);

            temp0.x=x;
            temp0.y=y;

            temp1.x=x;
            temp1.y=y;

            temp2.x=0;
            temp2.y=0;

            temp3.x=0;
            temp3.y=0;



            temp1.add(topLine);

            temp2.add(h).add(temp0);

            temp3.add(temp2).add(bottomLine);
        }while (!checkVisibility(temp0) || !checkVisibility(temp1) ||
                !checkVisibility(temp2) || !checkVisibility(temp3));


        v.add(temp0);
        v.add(temp1);
        v.add(temp2);
        v.add(temp3);
        return v;
    }

    /**
     * Generates triangle
     * @param a
     * @param b
     * @param angle0
     * @return
     */
    public static Vector <Vector2> generateTriangle(float a, float b,  float angle0){

        Vector2 initalPoint = new Vector2();
        Vector2 sideA = new Vector2(1,0);
        Vector2 sideB = new Vector2(1,0);

        Vector2 point0 = new Vector2();
        Vector2 point1 = new Vector2();

        do{

            int angleToUse = GameInfo.random.nextInt(180);
            initalPoint.x = GameInfo.random.nextInt((int) Math.floor(GameInfo.CAMERA_WIDTH));
            initalPoint.y = GameInfo.random.nextInt((int) Math.floor(GameInfo.CAMERA_HEIGHT));

            sideA.x =1;
            sideA.y=0;
            sideB.x =1;
            sideB.y=0;
            point0.x =0;
            point1.x =0;
            point1.y=0;
            point0.y=0;

            sideA.scl(a);
            sideA.rotate(angleToUse);
            sideB.scl(b);
            sideB.rotate(angleToUse);

            sideB.rotate(angle0);

            point0.add(initalPoint).add(sideA);
            point1.add(initalPoint).add(sideB);

        }while (!checkVisibility(point0) || !checkVisibility(point1) || !checkVisibility(initalPoint));

        Vector<Vector2> vector = new Vector<Vector2>();
        vector.add(point0);
        vector.add(point1);
        vector.add(initalPoint);
        return vector;
    }

    /**
     * Generates a generic shape given the number of points
     * @param sides
     * @return
     */
    public static Vector<Vector2> generateShape(final int sides){

        final float totalAngles = 180f + (sides - 3) * 180f;
        Vector<Vector2> v = new Vector<Vector2>();

        do {
            int tempSides = sides;
            float tempAngles  = totalAngles;

            v.clear();
            Vector2 initialPoint = new Vector2();

            initialPoint.x = GameInfo.random.nextInt((int) Math.floor(GameInfo.CAMERA_WIDTH));
            initialPoint.y = GameInfo.random.nextInt((int) Math.floor(GameInfo.CAMERA_HEIGHT));

            tempSides --;
            v.add(initialPoint);

            Vector2 line = new Vector2(1,0);

            while (tempSides > 0){
                float angle = sides > 1 ? GameInfo.random.nextInt(140) + 30f: tempAngles;
                tempAngles-= angle;
                line.setLength(GameInfo.random.nextInt(20) + 20f);
                line.rotate(angle);
                Vector2 temp = new Vector2(v.lastElement());
                temp.add(line);
                v.add(temp);
                tempSides--;
            }
        }while (!checkAllVisibility(v));

        return v;
    }


    /**
     * Checks the visibility of all the points in vector v
     * @param v
     * @return
     */
    private static boolean checkAllVisibility(final Vector<Vector2> v){

        boolean res = true;
        int i = 0;
        while (res && i<v.size()){
            res = checkVisibility(v.get(i));
            System.out.println(res);
            i++;
        }
        return res;
    }

    /**
     * Checks the visibility of the point vector2
     * @param vector2
     * @return
     */
    private static boolean checkVisibility(Vector2 vector2){

        return vector2.x > (GameInfo.CAMERA_WIDTH * (1f/8f)) && vector2.x< (GameInfo.CAMERA_WIDTH * (7f/8f)) &&
                vector2.y > (GameInfo.CAMERA_HEIGHT * (1f/8f)) && vector2.y < (GameInfo.CAMERA_HEIGHT * (7f/8f)) ;
    }


    /**
     * Adds points to make the solution less obvious
     * @param v
     * @param num
     */
    private static void addPoints(Vector<Vector2> v, int num){

        int initialSize = v.size();
        do {
            Vector2 temp = new Vector2(GameInfo.CAMERA_WIDTH * GameInfo.random.nextFloat(), GameInfo.CAMERA_HEIGHT * GameInfo.random.nextFloat());
            if (checkVisibility(temp) && checkSpacing(v,temp,10f))
                v.add(temp);

        }while (v.size() < num + initialSize);

    }


    /**
     * Ensures that there is a distance greater than the min distance between every point in v and  a.
     * @param v
     * @param a
     * @param minDistance
     * @return
     */
    private static boolean checkSpacing(Vector<Vector2> v, Vector2 a, float minDistance){

        for (int i = 0; i < v.size();i++){

            if (v.get(i).dst(a) < minDistance)
                return false;
        }

        return true;

    }
}
