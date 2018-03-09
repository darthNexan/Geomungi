package com.pablo.gameutils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Vector;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

/**
 * Contains methods used for identifying figures drawn by users.
 *
 * @author Dennis Guye
 *
 *
 */

public class ShapeIdentification {


    public final static int ACUTE;
    public final static int OBTUSE;
    public final static int RIGHT;

    public final static int EQUILATERAL;
    public final static int ISOSCELES;
    public final static int SCALENE;

    static {
        ACUTE = 0;
        OBTUSE = 1;
        RIGHT = 2;

        EQUILATERAL = 0;
        ISOSCELES = 1;
        SCALENE =2;
    }

    /**
     * Checks to see if the two floats are approximately equal (difference of less than 0.1%).
     * @param a
     * @param b
     * @return A boolean indicating whether the two values are approximately equal.
     */
    public static boolean isApproxEqual(float a, float b){
        return isApproxEqual(a,b,0.1f);
    }

    /**
     * Checks to see if the two floats are approximately equal (difference of less than margin).
     * @param a
     * @param b
     * @param marginOfDifference percentage by which the two values can differ
     * @return  A boolean indicating whether the two values are approximately equal
     */

    public static boolean isApproxEqual(float a, float b, float marginOfDifference){
        float percentDiff = abs((a-b)/b) * 100f;

        System.out.println(percentDiff);
        return percentDiff<marginOfDifference;
    }



    /**
     *
     * @param a first line
     * @param b second line
     * @return  a boolean indicating whether the lines are parallel
     */


    public static boolean identifyParallelLines(Vector<Vector2> a, Vector<Vector2> b){

        Vector2 v1 = new Vector2(a.firstElement().x - a.lastElement().x, a.firstElement().y - a.lastElement().y);
        Vector2 v2 = new Vector2(b.firstElement().x - b.lastElement().x , b.firstElement().y - b.lastElement().y);

        return v1.hasSameDirection(v2) || v1.hasOppositeDirection(v2);
    }


    /**
     * TODO TEST
     * @param points should be a copy
     * @param type the type of game just used
     * @return a boolean indicating whether the right shape was made
     */
    public static boolean identifyShape(Vector<Vector2> points, BasicGameType type){

        return filterPoints(points).x2 && points.size() == type.shapeType +1 && points.lastElement().equals(points.firstElement());
    }


    /**
     * TODO TEST
     * @param  v Point to remove duplicates
     * @return A tuple2 containing the vector passed and a boolean
     */
    private static Tuple2<Vector<Vector2>, Boolean> filterPoints(Vector<Vector2> v){

        Boolean res = true;
        for (int i = 0; i < v.size()-1 && res; i++){
            int j = i+1;

            while (j<v.size() && res){

                if(v.get(j).equals(v.get(i))){

                    if(j!=i+1){
                        res = false;

                    }
                    else{
                        v.remove(j);
                    }
                }
                else {
                    j++;
                }
            }
        }

        return new Tuple2<Vector<Vector2>, Boolean>(v,res);
    }//filterPoints

    /**
     * Compresses a collection of lines into one vector
     * @param lines lines to compress
     * @return
     */
    public static Tuple2<Vector<Vector2>, Boolean> compress(Vector<Vector<Vector2>> lines){

        if (lines.isEmpty()){
            return new Tuple2<Vector<Vector2>, Boolean>(null,false);
        }

        Vector<Vector<Vector2>> linesCopy = (Vector<Vector<Vector2>>) lines.clone();


        Vector<Vector2> points = new Vector<Vector2>();
        points.addAll(linesCopy.firstElement());
        linesCopy.remove(0);
        int i;

        do{
            i=0;

            while (i < linesCopy.size() ){

                if(linesCopy.get(i).firstElement().equals(points.lastElement())){
                    linesCopy.get(i).remove(0);
                    points.addAll(points.size() -1,linesCopy.get(i));
                    linesCopy.remove(i);
                    break;
                }
                else if( linesCopy.get(i).lastElement().equals(points.lastElement())){
                    linesCopy.get(i).remove(linesCopy.get(i).size() -1 );
                    points.addAll(points.size() -1, linesCopy.get(i));
                    linesCopy.remove(i);
                    break;
                }
                else if(linesCopy.get(i).firstElement().equals(points.firstElement())){
                    linesCopy.get(i).remove(0);
                    points.addAll(0, linesCopy.get(i));
                    linesCopy.remove(i);
                    break;
                }
                else if(linesCopy.get(i).lastElement().equals(points.firstElement()) ){
                    linesCopy.get(i).remove(linesCopy.get(i).size() - 1);
                    points.addAll(0, linesCopy.get(i));
                    linesCopy.remove(i);
                    break;
                }
                else{
                    i++;
                }
            }


        }while (!linesCopy.isEmpty() && linesCopy.size()>i);


        return new Tuple2<Vector<Vector2>, Boolean>(points, i<linesCopy.size());
    }

    /**
     * Checks the distance between the two lines
     * @param a
     * @param b
     * @return
     */

    public static float calculateLength(Vector2 a, Vector2 b){

        return a.dst(b);
    }
    /**
     * returns angle
     */
    public static float calculateAngle(Vector2 a, Vector2 b, Vector2 c){

        Vector2 ab = new Vector2(b);
        Vector2 bc = new Vector2(c);
        Vector2 ac =  new Vector2(a);

        ab.sub(a);
        bc.sub(b);
        ac.sub(c);

        float angle = abs(180f - (abs(ab.angle(ac)) + abs(bc.angle(ac))));
        //System.out.println(angle);

        return angle;
    }


    /**
     * TODO TEST
     * Checks the shape drawn. Check if the the shape is complete before running this method
     *
     * @param lines set of points that make up the shape. The first and last points must be equal
     * @param isRight checks to see if the angle is a right angle
     * @return  the first value states whether the lines are parallel
     *          the second checks if opposite lines are equal in length
     *          the third indicates if the angles are right
     *          the fourth checks if all lines are equal
     */
    public static Tuple4<Boolean,Boolean,Boolean,Boolean> checkParallelogram(Vector<Vector2> lines ,boolean isRight, boolean isRhombus){
        if (lines.size() != 5){
            return new Tuple4<Boolean, Boolean, Boolean,Boolean>(false,false,false,false);
        }

        //noinspection UnusedAssignment
        boolean res1 = false;
        boolean res2 =false;
        boolean res3 = false;
        boolean res4 = false;

        res1 = lines.firstElement().equals(lines.lastElement());
        if (res1) {//check if opposing lines are parallel
            Vector<Vector2> l1 = new Vector<Vector2>();
            l1.add(lines.get(0));
            l1.add(lines.get(1));
            Vector<Vector2> l2 = new Vector<Vector2>();
            l2.add(lines.get(2));
            l2.add(lines.get(3));


            Vector<Vector2> l3 = new Vector<Vector2>();
            l3.add(lines.get(3));
            l3.add(lines.get(4));
            Vector<Vector2> l4 = new Vector<Vector2>();
            l4.add(lines.get(1));
            l4.add(lines.get(2));

            res2 = identifyParallelLines(l1, l2) && identifyParallelLines(l3,l4);
        }

        if (isRight && res1 && res2 ){//check if angle is a right angle.... based on game mode
            res3 = abs(MathUtils.sinDeg(calculateAngle(lines.get(0), lines.get(1), lines.get(2))))
                    == MathUtils.sinDeg(90) ;
        }

        if (isRhombus && res1 && res2){
            res4 = isApproxEqual(calculateLength(lines.get(0), lines.get(1)), calculateLength(lines.get(2), lines.get(3)));
        }
        return new Tuple4<Boolean, Boolean, Boolean,Boolean>(res1,res2,res3,res4);
    }//checkParallelogram


    /**
     * Passed
     * @param points the points that make up the triangle
     * @param type the type of triangle RIGHT, OBTUSE OR SCALENE, use the class constants tto specify
     * @return A tuple indicating the type of triangle.
     *          the first indicates that the shape  is a triangle
     *          the second indicates that the triangle is of the right type
     * @throws IllegalArgumentException thrown if an invalid type is entered
     */
    public static Tuple3< Boolean,Boolean,Boolean>  triangleA(Vector<Vector2> points, final int type) throws IllegalArgumentException {


        if (type<0 || type>2){
            throw new IllegalArgumentException("Use class constants to specify the type of triangle");
        }
        if (!points.lastElement().equals(points.firstElement())){
            return new Tuple3<Boolean, Boolean,Boolean>(false,false,false);
        }
        if (points.size() != 4) {//not a triangle
            return new Tuple3<Boolean, Boolean,Boolean>(true,false, false);
        }
        else {

            Boolean res=false;
            float angle0,angle1,angle2 = 0;

            angle0 = calculateAngle(points.get(0),points.get(1), points.get(2));
            angle1 = calculateAngle(points.get(1), points.get(2),points.get(0));
            angle2 = 180f - (angle0 + angle1);

            if (type == OBTUSE){
                res = angle0 > 90f || angle1 > 90f || angle2 > 90f;
            }
            else if (type == ACUTE){
                res = angle0 <90f && angle1 <90f && angle2 <90f;
            }
            else if (type == RIGHT){
                res = isApproxEqual(90f,angle0)|| isApproxEqual(90f,angle1) || isApproxEqual(90f,angle2);
            }

            return new Tuple3<Boolean,Boolean, Boolean>(true,true, res);
        }
    }//triangleA


    /**
     * Passed
     * @param points the points used to identify the triangle
     * @param type the type of triangle either equilateral, isosceles or scalene
     * @return a tuple
     *          the first value indicates whether the shape is a triangle
     *          the second value indicates whether the triangle is of the specified type
     * @throws IllegalArgumentException thrown if an invalid type is passed to the function
     */
    public static Tuple3<Boolean,Boolean,Boolean> triangleL(Vector<Vector2> points, final int type) throws IllegalArgumentException{
        if (type<0 || type>2){
            throw new IllegalArgumentException("Use class constants to specify the type of triangle");
        }
        if (!points.lastElement().equals(points.firstElement())){
            return new Tuple3<Boolean, Boolean, Boolean>(false,false,false);
        }
        if (points.size() != 4) {//not a triangle
            return new Tuple3<Boolean,Boolean, Boolean>(true, false,false);
        }
        else {
            boolean res =false;


            float angle0,angle1,angle2 = 0;

            angle0 = calculateAngle(points.get(0),points.get(1), points.get(2));
            angle1 = calculateAngle(points.get(1), points.get(2),points.get(0));
            angle2 = 180f - (angle0 + angle1);

            System.out.println(angle0);
            System.out.println(angle1);
            System.out.println(angle2);

            if (type == EQUILATERAL){


                res = isApproxEqual(angle0,angle1) && isApproxEqual(angle1,angle2);
            }
            else if (type == ISOSCELES){
                res = isApproxEqual(angle0,angle1) || isApproxEqual(angle0,angle2) || isApproxEqual(angle1,angle2);
            }
            else if (type == RIGHT){
                res = angle0!=angle1 && angle1 !=angle2 && angle1 != angle2;
            }


            return new Tuple3<Boolean,Boolean, Boolean>(true,true,res);
        }

    }


    /**
     * TODO Test
     * @param points The points to check
     * @return a tuple indicating whether the figure is a kite. The first value states whether the shapes is a quad
     *         the second states whether the quad is a kite
     */
    public static Tuple3<Boolean,Boolean,Boolean> checkKite(Vector<Vector2> points){
        boolean res2 = points.lastElement().equals(points.firstElement());

        boolean res0= points.size() == 5 && res2;
        boolean res1 = false;

        if (res0){
            float a,b,c,d;
            a= points.get(0).dst(points.get(1));
            b = points.get(1).dst(points.get(2));
            c= points.get(2).dst(points.get(3));
            d = points.get(3).dst(points.get(4));


            res1 = (isApproxEqual(a,b) && isApproxEqual(a,b)) || (isApproxEqual(a,c) && isApproxEqual(b,d))
                    || (isApproxEqual(a,d) && isApproxEqual(b,c));


        }



        return new Tuple3<Boolean, Boolean ,Boolean>(res2,res0,res1);
    }

    /**
     * TODO TEST
     * @param points the points to check
     * @return a tuple indicating whether the figure is a kite.
     *          the first value indicates whether it is a shape
     *          the second value indicates whether it is a quad
     *          the third whether it is a trapezium
     */
    public static Tuple3<Boolean,Boolean,Boolean> checkTrapezium(Vector<Vector2> points){
        boolean res0 = points.firstElement().equals(points.lastElement());
        boolean res1 = points.size() == 5 && res0;
        boolean res2=false;

        if (res1){
            Vector<Vector2> a = new Vector<Vector2>();
            a.add(points.get(0));
            a.add(points.get(1));
            Vector<Vector2> b = new Vector<Vector2>();
            b.add(points.get(2));
            b.add(points.get(3));

            Vector<Vector2> c = new Vector<Vector2>();
            c.add(points.get(3));
            c.add(points.get(4));
            Vector<Vector2> d = new Vector<Vector2>();
            d.add(points.get(1));
            d.add(points.get(2));

            res2 = identifyParallelLines(a,b) || identifyParallelLines(c,d);
        }

        return new Tuple3<Boolean,Boolean,Boolean>(res0,res1,res2);

    }

    /**
     * Tests whether the points make up a generic shape
     * @param points points to test
     * @param gameType number of sides that make up the shape
     * @return A tuple indicating where the points make up a shape
     *          the first element indicates whether the points make up a shape
     *          the second element indicates whether there are the correct number of sides
     * @throws IllegalArgumentException if an invalid game type is passed to the function
     */
    public static Tuple2<Boolean,Boolean> checkShape(Vector<Vector2>points, BasicGameType gameType) throws IllegalArgumentException{
        if (!gameType.isShape()) throw new IllegalArgumentException("None shape type passed to check shape");
        return new Tuple2<Boolean, Boolean>(points.firstElement().equals(points.lastElement()), points.firstElement().equals(points.lastElement()) && points.size() == gameType.shapeType + 1);
    }
}
