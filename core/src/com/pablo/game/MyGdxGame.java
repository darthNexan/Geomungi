package com.pablo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pablo.screen.BasicMiniGameScreen;

public class MyGdxGame extends Game {
	private  SpriteBatch batch;

	private OrthographicCamera camera;



    /**
     * First method called
    */

    @Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(com.pablo.gameutils.GameInfo.CAMERA_WIDTH, com.pablo.gameutils.GameInfo.CAMERA_HEIGHT);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();



		Screen screen = new BasicMiniGameScreen(this);
		setScreen(screen);


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
}
