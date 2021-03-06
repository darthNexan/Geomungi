package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pablo.gameutils.GameInfo;
import com.pablo.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS=60;
		config.width= GameInfo.WIDTH;
		config.height=GameInfo.HEIGTH;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
