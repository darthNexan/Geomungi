package com.pablo.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import com.pablo.input.SummaryScreenInput;



/**
 * Created by Dennis on 29/03/2018.
 */

public class SummaryScreen implements Screen {

    private int resultToBeDisplayed;
    private GestureDetector detector;
    private Camera camera;
    private SpriteBatch batch;
    private MyGdxGame game;
    private BasicGameType type;


    public SummaryScreen(MyGdxGame game, BasicGameType type ){

        detector = new GestureDetector(new SummaryScreenInput(this));
        resultToBeDisplayed = 0;
        this.game = game;
        this.batch = game.getBatch();
        this.camera = game.getCamera();
        this.type = type;



    }



    /**
     * Called when this screen becomes the current screen for a game.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(detector);

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     *
     */
    @Override
    public void resume() {

    }

    /**
     *
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }

    public void changeResultToDisplay(int value){
        if (resultToBeDisplayed>1 && value <0){
            resultToBeDisplayed += value;
        }
        else if (resultToBeDisplayed<type.userResults.size()-1 && value>0){
            resultToBeDisplayed += value;
        }
    }
}
