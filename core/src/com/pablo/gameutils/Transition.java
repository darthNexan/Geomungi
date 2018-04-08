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

    private static volatile boolean isDelayed;//used to block access to methods if an appreciatable amount of time has not elapsed
    static {
        isDelayed=false;

    }


    /**
     * Used to delay until the next screen transition.
     * This is not the ideal solution.
     */
    private static class TimingRunnable implements Runnable{

        private double startTime;
        private float delay;

        /**
         * Runnable constructor
         * @param delay in seconds
         */
        TimingRunnable(float delay){
            this.startTime = System.currentTimeMillis();
            this.delay = delay;
        }
        @Override
        public void run() {
            double currentT = System.currentTimeMillis();

            while ((currentT - startTime)/1000 < delay){
                currentT = System.currentTimeMillis();
            }

            Transition.isDelayed = false;
        }
    }

    /**
     * Spawns a thread that measures the time until the next transition
     *
     */
    private static void delay(){
        isDelayed=true;
        Runnable runnable = new TimingRunnable(0.5f);
        Thread th = new Thread(runnable);
        th.setPriority(Thread.MIN_PRIORITY);
        th.start();
    }

    public static void changeToMenuScreen(MyGdxGame game, boolean shouldDelay){
        if (!isDelayed) {
            MenuScreen screen = new MenuScreen(game);
            game.setScreen(screen);
            if (shouldDelay) delay();
        }
    }


    public static void changeToLevelSelectionScreen(MyGdxGame game, boolean shouldDelay){
        if (!isDelayed) {
            LevelSelectionScreen screen = new LevelSelectionScreen(game);
            game.setScreen(screen);
            if (shouldDelay) delay();
        }
    }

    public static void changeToBasicMiniGameScreen(MyGdxGame game, int selectedStage){
        if (!isDelayed) {
            BasicMiniGameScreen screen = new BasicMiniGameScreen(game);
            screen.setStage(selectedStage);

            game.setScreen(screen);
        }
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
