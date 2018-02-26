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
        return clicked;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setClicked(float x, float y){
        System.out.println("Ran");
        clicked = getBoundingRectangle().contains(x,y);
    }

    public void reset(){
        clicked = false;
    }


}
