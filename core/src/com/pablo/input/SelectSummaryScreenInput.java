package com.pablo.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Transition;
import com.pablo.screen.LevelSelectionScreen;

import static java.lang.Math.abs;

/**
 * Created by Dennis on 08/04/2018.
 */

public class SelectSummaryScreenInput implements GestureDetector.GestureListener {

    LevelSelectionScreen screen;


    public SelectSummaryScreenInput(LevelSelectionScreen screen){
       this.screen = screen;
    }

    /**
     * @param x
     * @param y
     * @param pointer
     * @param button
     *
     */
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        float newX = abs(x * GameInfo.CAMERA_WIDTH / Gdx.graphics.getWidth());
        float newY = GameInfo.CAMERA_HEIGHT - abs(y * GameInfo.CAMERA_HEIGHT / Gdx.graphics.getHeight());
        Rectangle[] boxes = screen.textBox;

        Gdx.app.log("New Y", "" + newY);

        for (int i = 0; i < boxes.length; i++ ){
            if (boxes[i].contains(newX,newY)){
                Transition.changeToSummaryScreen(i,screen.game,true);
                break;
            }
        }

        return false;
    }

    /**
     * Called when a tap occured. A tap happens if a touch went down on the screen and was lifted again without moving outside
     * of the tap square. The tap square is a rectangular area around the initial touch position as specified on construction
     * time of the {@link GestureDetector}.
     *
     * @param x
     * @param y
     * @param count  the number of taps.
     * @param button
     */
    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    /**
     * Called when the user dragged a finger over the screen and lifted it. Reports the last known velocity of the finger in
     * pixels per second.
     *
     * @param velocityX velocity on x in seconds
     * @param velocityY velocity on y in seconds
     * @param button
     */
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    /**
     * Called when the user drags a finger over the screen.
     *
     * @param x
     * @param y
     * @param deltaX the difference in pixels to the last drag event on x.
     * @param deltaY the difference in pixels to the last drag event on y.
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    /**
     * Called when no longer panning.
     *
     * @param x
     * @param y
     * @param pointer
     * @param button
     */
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    /**
     * Called when the user performs a pinch zoom gesture. The original distance is the distance in pixels when the gesture
     * started.
     *
     * @param initialDistance distance between fingers when the gesture started.
     * @param distance        current distance between fingers.
     */
    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    /**
     * Called when a user performs a pinch zoom gesture. Reports the initial positions of the two involved fingers and their
     * current positions.
     *
     * @param initialPointer1
     * @param initialPointer2
     * @param pointer1
     * @param pointer2
     */
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    /**
     * Called when no longer pinching.
     */
    @Override
    public void pinchStop() {

    }
}
