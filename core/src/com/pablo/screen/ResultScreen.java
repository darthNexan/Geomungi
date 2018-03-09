package com.pablo.screen;

import com.badlogic.gdx.Screen;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.Tuple2;
import com.pablo.gameutils.Tuple3;
import com.pablo.gameutils.Tuple4;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * Created by Dennis on 08/03/2018.
 */

public class ResultScreen implements Screen {
    private BasicGameType type;
    private Tuple2<Boolean,Boolean> res0;
    private Tuple3<Boolean,Boolean,Boolean> res1;
    private Tuple4<Boolean,Boolean,Boolean,Boolean> res2;
    private int score;
    private String[] messages;

    public ResultScreen(BasicGameType type, Tuple2<Boolean, Boolean> res0,
                        Tuple3<Boolean,Boolean,Boolean> res1, Tuple4<Boolean,Boolean,Boolean,Boolean> res2){

        this.type = type;
        this.res0 = res0;
        this.res1 = res1;
        this.res2 = res2;

       //messages = new String[res0 == null? 3 : res1 ==null? 4:5];
        this.score = 0;
    }

    private void calc(){
        if (type.isParallel()){
            calcScoreRes0();
        }
        else if (type.isAngle()){
            calcScoreRes0();
        }
        else if (type.isShape()){
            if (!type.isTriangle() && !type.isQuad()){
                calcScoreRes0();
            }
            else if (type.isTriangle() && type.getSpecializedCategory() <= 5){
                calcScoreRes1();
            }
            else if (type.isQuad() && type.getSpecializedCategory() <= 5){
                if (type.isType0()) calcScoreSquare();
                else if (type.isType1()) calcScoreRect();
                else if (type.isType3()) calcScoreRhom();
                else if (type.isType4()) calcScoreParallel();
                else calcScoreRes1();
            }
            else {
                calcScoreRes0();
            }
        }
    }

    private void calcScoreRes1(){

        messages = new String[4];
        score += res1.x1 ? 10:0;
        score += res1.x2 ? 10:0;
        score += res1.x3 ? 10:0;
        messages[0] = Integer.toString(res1.x1?10:0);
        messages[1] = Integer.toString(res1.x2?10:0);
        messages[2] = Integer.toString(res1.x3?10:0);
        messages[3] = Integer.toString(score);
    }


    private void calcScoreRes0(){
        messages = new String[3];
        score += res0.x1? 10:0;
        messages[0] = Integer.toString(res0.x1? 10:0);
        score += res0.x2? 10:0;
        messages[1] = Integer.toString(res0.x2? 10:0);
        messages[3] = Integer.toString(score);
    }

    private void calcScoreSquare(){
        messages = new String[5];
        score += res2.x1?10:0;
        score += res2.x2?10:0;
        score += res2.x3?10:0;
        score += res2.x4?10:0;

        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);
        messages[2] = Integer.toString(res2.x3?10:0);
        messages[3] = Integer.toString(res2.x4?10:0);
        messages[4] = Integer.toString(score);
    }

    private void calcScoreRect(){
        messages = new String[4];
        score += res2.x1?10:0;
        score += res2.x2?10:0;
        score += res2.x3?10:0;


        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);
        messages[2] = Integer.toString(res2.x3?10:0);
        messages[3] = Integer.toString(score);
    }

    private void calcScoreRhom(){
        messages = new String[4];
        score += res2.x1?10:0;
        score += res2.x2?10:0;

        score += res2.x4?10:0;

        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);
        messages[2] = Integer.toString(res2.x4?10:0);
        messages[3] = Integer.toString(score);
    }


    private void calcScoreParallel(){
        messages = new String[3];
        score += res2.x1?10:0;
        score += res2.x2?10:0;


        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);

        messages[3] = Integer.toString(score);
    }


    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
