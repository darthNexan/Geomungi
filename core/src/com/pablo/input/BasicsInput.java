package com.pablo.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.UISprite;

import static java.lang.Math.abs;

/**
 * Created by Dennis on 07/02/2018.
 */

public class BasicsInput extends InputAdapter {

    private Vector2 pos;
    private UISprite uiSprite;
    public BasicsInput(Vector2 pos, UISprite uiSprite){
        this.uiSprite = uiSprite;
        this.pos = pos;
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button){


        float newX = abs(x * GameInfo.CAMERA_WIDTH / Gdx.graphics.getWidth());
        float newY = GameInfo.CAMERA_HEIGHT - abs(y * GameInfo.CAMERA_HEIGHT / Gdx.graphics.getHeight());
        uiSprite.setClicked(newX, newY);

        if (!uiSprite.isClicked()) {
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
