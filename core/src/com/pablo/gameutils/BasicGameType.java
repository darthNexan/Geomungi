package com.pablo.gameutils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.PauseableThread;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Dennis on 07/02/2018.
 */

public class BasicGameType {



    public final int category;//General eg parallel lines, plane shape, angle, etc.
    public final int shapeType; //type of triangle, quadrilateral, pentagon, hexagon, septagon,octagon,nonagon, decagon
    public final int angleType;  //obtuse, acute, right angle
    public final int specializedCategory; //for use with scalene, equilateral, isosceles, right angle, acute and obtuse triangles
    //also for types of quadrilaterals parallelogram, rhombus, square, trapezoid, kite, rectangle
    public String header;
    public ArrayList<Tuple> userResults;// A tuple that contains the results a user got on any given level

    public String[] getFeedBackMessage() {
        return feedBackMessage;
    }

    public String[] getCriteriaHeadings() {
        return criteriaHeadings;
    }

    private String[] feedBackMessage;
    private String[] criteriaHeadings;
    /**
     * Returns a fully constructed game mode
     */
    public BasicGameType(){
        Random random = GameInfo.random;

        userResults = new ArrayList<Tuple>();
        category = random.nextInt(3);

        if(category == 1){
            shapeType = random.nextInt(8) + 3;
            angleType = -1;
            if (shapeType == 0 || shapeType ==1){
                specializedCategory = random.nextInt(6);
            }
            else {
                specializedCategory = -1;
            }
        }
        else if(category ==2){
            angleType = random.nextInt(3);
            shapeType = -1;
            specializedCategory =-1;
        }
        else {
            angleType = -1;
            shapeType = -1;
            specializedCategory =-1;
        }

    }//BasicGameType Constructor

    public BasicGameType(int gameCategory, int shapeType, int angleType, int specializedCategory) {
        this.userResults = new ArrayList<Tuple>();
        this.category = gameCategory;
        this.shapeType = shapeType;
        this.angleType = angleType;
        this.specializedCategory = specializedCategory;
    }

    public boolean isParallel(){
        return category == 0;
    }

    public boolean isShape(){
        return category ==1;
    }

    public boolean isAngle(){
        return category ==2;
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
        return shapeType==3;
    }

    public boolean isQuad(){
        return shapeType==4;
    }

    public boolean isPentagon(){
        return shapeType==5;
    }

    public boolean isHexagon(){
        return shapeType==6;
    }

    public boolean isSeptagon(){
        return shapeType==7;
    }

    public boolean isOctagon(){
        return shapeType==8;
    }

    public boolean isNonagon(){
        return shapeType==9;
    }

    public boolean isDecagon(){
        return shapeType==10;
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


    public static ArrayList<BasicGameType> getGameTypes(){


        JsonReader reader = new JsonReader();

        JsonValue jsonValue = reader.parse(Gdx.files.internal("basics.json"));
        if (jsonValue.has("GameMode")){
            jsonValue = jsonValue.get("GameMode").get(0);
            //System.out.println(jsonValue);
        }
        else throw new IllegalStateException("Invalid Json");

        if (jsonValue.getString("Section").equals("Basic")) {
            jsonValue = jsonValue.get("Levels");
        }

        JsonValue.JsonIterator iterator = jsonValue.iterator();

        ArrayList<BasicGameType> list = new ArrayList<BasicGameType>();

        while (iterator.hasNext()){
            JsonValue value = iterator.next();
            BasicGameType temp = new BasicGameType(value.getInt("category"), value.getInt("shapeType"), value.getInt("angleType"),value.getInt("specializedCategory"));
            temp.header = value.getString("Header");
            temp.criteriaHeadings = value.get("Labels").asStringArray();
            temp.feedBackMessage = value.get("FeedBack").asStringArray();
            list.add(temp);
        }

        return list;
    }

    public int getSpecializedCategory() {
        return specializedCategory;
    }

    public void addToResults(Tuple tuple){
        userResults.add(tuple);
    }
    public ArrayList<Tuple> getResults(){
        return userResults;
    }

}//BasicGameType

