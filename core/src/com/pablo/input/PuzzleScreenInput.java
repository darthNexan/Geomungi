package com.pablo.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Transition;
import com.pablo.gameutils.UISprite;
import com.pablo.screen.BasicMiniGameScreen;

import static java.lang.Math.abs;

/**
 * Created by Dennis on 07/02/2018.
 */

public class PuzzleScreenInput extends InputAdapter {


    private Vector2 pos;
    private UISprite uiSprite;
    private UISprite uiSprite2;
    public PuzzleScreenInput(Vector2 pos, BasicMiniGameScreen screen){
        this.uiSprite =  screen.XButton;
        this.uiSprite2 = screen.checkButton;
        this.pos = pos;
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button){


        float newX = abs(x * GameInfo.CAMERA_WIDTH / Gdx.graphics.getWidth());
        float newY = GameInfo.CAMERA_HEIGHT - abs(y * GameInfo.CAMERA_HEIGHT / Gdx.graphics.getHeight());
        boolean res0 = uiSprite.setClicked(newX, newY);
        boolean res1 =uiSprite2.setClicked(newX,newY);

        if (!res0 && !res1) {
            pos.x = newX;
            pos.y = newY ;
        }
        else {
            pos.x = -1;
            pos.y = -1;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer){
        pos.x =  abs(x * GameInfo.CAMERA_WIDTH/ Gdx.graphics.getWidth());
        pos.y = GameInfo.CAMERA_HEIGHT - abs(y * GameInfo.CAMERA_HEIGHT / Gdx.graphics.getHeight());

        return false;
    }


    @Override
    public boolean touchUp(int x, int y, int pointer, int button){
        //System.out.println("aefba");
        pos.x = -1;
        pos.y = -1;
        return false;
    }

}

