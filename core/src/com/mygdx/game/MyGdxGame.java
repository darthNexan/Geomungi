package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGdxGame extends Game {
	private  SpriteBatch batch;
	private Texture img;
	private Sprite sprite;
	private int i =0;
	private OrthographicCamera camera;



    /**
     * First method called
    */

    @Override
	public void create () {
		batch = new SpriteBatch();
		//Screen screen = new MainMenu(this);
		//setScreen(screen);
		img = new Texture("bg.png");
		sprite = new Sprite(img);

		sprite.setSize(30,30*16/9);
		//sprite.rotate(90);
		sprite.setPosition(0,0);
		camera = new OrthographicCamera(30,30*16/9);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		//camera.rotate(90);
		camera.update();


	}

    /**
     * Game loop
     */
	@Override
	public void render () {
	//	super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//batch.draw(img, 0, 0);
		sprite.draw(batch);

		System.out.println(i);
		i++;

		batch.end();
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
