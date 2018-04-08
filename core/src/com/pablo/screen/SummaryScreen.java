package com.pablo.screen;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import java.util.ArrayList;
/**
 * Created by Dennis on 29/03/2018.
 */

public class SummaryScreen implements Screen {
    private MyGdxGame game;
    private Camera camera;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private ArrayList<BasicGameType> types;

    public SummaryScreen(MyGdxGame game){
        this.game = game;
        this.camera = game.getCamera();
        this.batch = game.getBatch();
        this.renderer = new ShapeRenderer();
        this.types = game.gameTypes();

    }
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

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
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
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
}
