package com.pablo.gameutils;

import com.pablo.game.MyGdxGame;
import com.pablo.screen.BasicMiniGameScreen;
import com.pablo.screen.LevelSelectionScreen;
import com.pablo.screen.MenuScreen;
import com.pablo.screen.ResultScreen;


/**
 *
 * To be used to transition between screen
 * Created by Dennis on 05/04/2018.
 */

public class Transition {


    private static void delay(){
        final double TIME_TO_DELAY = 1d;
        double start = System.currentTimeMillis();
        double now = System.currentTimeMillis();
        while (now - start < TIME_TO_DELAY){
            now = System.currentTimeMillis();
        }
    }

    public static void changeToMenuScreen(MyGdxGame game, boolean shouldDelay){
        MenuScreen screen = new MenuScreen(game);
        if (shouldDelay)
            delay();
        game.setScreen(screen);

    }


    public static void changeToLevelSelectionScreen(MyGdxGame game, boolean shouldDelay){
        LevelSelectionScreen screen = new LevelSelectionScreen(game);
        if (shouldDelay) delay();


        game.setScreen(screen);
    }

    public static void changeToBasicMiniGameScreen(MyGdxGame game, int selectedStage){
        BasicMiniGameScreen screen = new BasicMiniGameScreen(game);
        screen.setStage(selectedStage);

        game.setScreen(screen);
    }


    public static void changeToResultScreen(BasicGameType type, MyGdxGame game, Tuple2<Boolean,Boolean> tuple2, BasicMiniGameScreen previousScreen){
        ResultScreen screen = new ResultScreen(type,tuple2,game,previousScreen);
        game.setScreen(screen);
    }


    public static void changeToResultScreen(BasicGameType type, MyGdxGame game, Tuple3<Boolean,Boolean,Boolean> tuple2, BasicMiniGameScreen previousScreen){
        ResultScreen screen = new ResultScreen(type,tuple2,game,previousScreen);
        game.setScreen(screen);
    }


    public static void changeToResultScreen(BasicGameType type, MyGdxGame game, Tuple4<Boolean,Boolean,Boolean,Boolean> tuple2, BasicMiniGameScreen previousScreen){
        ResultScreen screen = new ResultScreen(type,tuple2,game,previousScreen);
        game.setScreen(screen);
    }


}
