package com.pablo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pablo.gameutils.BasicGameType;
import com.pablo.screen.LevelSelectionScreen;
import com.pablo.screen.MenuScreen;

import java.util.ArrayList;

/*
Plant UML comment
@startuml
class MyGdxGame
class MenuScreen
class BasicGameType
class ShapeGeneration
class ShapeIdentification

Tuple --|> Tuple2
Tuple --|> Tuple3
Tuple --|> Tuple4


MyGdxGame -- MenuScreen: >
MyGdxGame -- BasicGameType: >
MenuScreen -- SummaryScreen: >
MenuScreen -- LevelSelectionScreen: >
LevelSelectionScreen -- BasicMiniGameScreen: >
LevelSelectionScreen -- MenuScreenInput: >
BasicMiniGameScreen -- ResultScreen: >
BasicMiniGameScreen -- ShapeGeneration: >
BasicMiniGameScreen -- ShapeIdentification: >
BasicMiniGameScreen -- UISprite: >
BasicMiniGameScreen -- BasicInput: >
MenuScreen -- Utilities: >
BasicGameType --* Tuple
@enduml
 */

/**
 * @author Dennis Guye
 *
 */
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
		levelSelectionScreen = new LevelSelectionScreen(this);

		gameTypes = BasicGameType.getGameTypes();
		Gdx.input.setCatchBackKey(true);
		setScreen(new MenuScreen(this));


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
