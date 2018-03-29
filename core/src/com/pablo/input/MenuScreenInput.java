package com.pablo.input;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pablo.gameutils.GameInfo;
import com.pablo.screen.BasicMiniGameScreen;
import com.pablo.screen.MenuScreen;

import static java.lang.Math.abs;
import static java.lang.Math.scalb;

/**
 * Created by Dennis on 28/03/2018.
 */

public class MenuScreenInput implements GestureDetector.GestureListener {

    private MenuScreen screen;

    public MenuScreenInput(MenuScreen screen){
        this.screen = screen;

    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        float newX = abs(x * GameInfo.CAMERA_WIDTH / Gdx.graphics.getWidth());
        float newY = GameInfo.CAMERA_HEIGHT - abs(y * GameInfo.CAMERA_HEIGHT / Gdx.graphics.getHeight());
        Rectangle[] boxes = screen.textBox;

        Gdx.app.log("New Y", "" + newY);

        for (int i = 0; i < boxes.length; i++ ){
            if (boxes[i].contains(newX,newY)){

                BasicMiniGameScreen screen = new BasicMiniGameScreen(this.screen.game);
                screen.setStage(i);
                Gdx.app.log("Stage", "" + screen.getStage());
                this.screen.game.setScreen(screen);

            }
        }

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {



        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        Rectangle[] boxes = screen.textBox;
        float firstBoxY = boxes[0].y + deltaY;
        float lastBpxY = boxes[boxes.length-1].y + deltaY;

        if (firstBoxY < (13*GameInfo.CAMERA_HEIGHT)/16 || lastBpxY > GameInfo.CAMERA_HEIGHT/16)
            return false;

        for (Rectangle aBox : boxes) {
            aBox.y += deltaY;
        }



        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
