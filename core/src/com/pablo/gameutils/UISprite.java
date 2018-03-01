package com.pablo.gameutils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Dennis on 19/02/2018.
 */

public class UISprite extends Sprite {


    public UISprite(Texture texture){
        super(texture);
    }
    private boolean clicked=false;

    /**
     * checks if the button was activated
     * @return
     */
    public boolean isClicked(){
        boolean temp = clicked;
        reset();

        return temp;
    }

    /**
     *
     * @param x
     * @param y
     */
    public boolean setClicked(float x, float y){

        clicked = getBoundingRectangle().contains(x,y);

        return clicked;
    }

    public void reset(){
        clicked = false;
    }


}
