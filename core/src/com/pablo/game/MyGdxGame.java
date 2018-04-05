package com.pablo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.Transition;
import com.pablo.screen.LevelSelectionScreen;

import java.util.ArrayList;

public class MyGdxGame extends Game {
	private  SpriteBatch batch;

	private OrthographicCamera camera;
	private LevelSelectionScreen levelSelectionScreen;


	private ArrayList<BasicGameType> gameTypes;

	public ArrayList<BasicGameType> gameTypes(){
		return gameTypes;
	}


    /**
     * First method called
    */

    @Override
	public void create () {

		BasicGameType.getGameTypes();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(com.pablo.gameutils.GameInfo.CAMERA_WIDTH, com.pablo.gameutils.GameInfo.CAMERA_HEIGHT);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();


		gameTypes = BasicGameType.getGameTypes();

		Transition.changeToMenuScreen(this,false);


	}

    /**
     * Game loop
     */
	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	public SpriteBatch getBatch(){
		return this.batch;
	}
	public OrthographicCamera getCamera(){
		return this.camera;
	}
	public void backToMenu(){
		this.setScreen(levelSelectionScreen);
	}

}
