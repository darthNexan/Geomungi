package com.pablo.gameutils;

import com.pablo.game.MyGdxGame;
import com.pablo.screen.BasicMiniGameScreen;
import com.pablo.screen.LevelSelectionScreen;
import com.pablo.screen.MenuScreen;
import com.pablo.screen.PuzzleResultScreen;
import com.pablo.screen.SummaryScreen;


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
            double currentTime;


            do {
                currentTime = System.currentTimeMillis();
            } while ((currentTime - startTime) / 1000 < delay);


            Transition.isDelayed = false;// after delay has elapsed set delayed to true
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


    /**
     *
     * @param game The game instance
     * @param shouldDelay indicates whether the next transition should be delayed
     */
    public static void changeToMenuScreen(MyGdxGame game, boolean shouldDelay){
        if (!isDelayed) {
            MenuScreen screen = new MenuScreen(game);
            game.setScreen(screen);
            if (shouldDelay) delay();
        }
    }


    /**
     * Change the active screen to the level selection screen
     * @param game the game instance
     * @param shouldDelay indicates whether the next transition should be delayed
     */

    public static void changeToPuzzleSelectionScreen(MyGdxGame game, boolean shouldDelay){
        if (!isDelayed) {
            LevelSelectionScreen screen = new LevelSelectionScreen(game,SelectedScreen.Puzzle);
            game.setScreen(screen);
            if (shouldDelay) delay();
        }
    }

    /**
     * Change the active screen to the level selection screen
     * @param game the game instance
     * @param shouldDelay indicates whether the next transition should be delayed
     */

    public static void changeToSummarySelectionScreen(MyGdxGame game, boolean shouldDelay){
        if (!isDelayed) {
            LevelSelectionScreen screen = new LevelSelectionScreen(game,SelectedScreen.Summary);
            game.setScreen(screen);
            if (shouldDelay) delay();
        }
    }


    /**
     * change to the basic game screen
     * @param game
     * @param selectedStage
     */
    public static void changeToBasicMiniGameScreen(MyGdxGame game, int selectedStage){
        if (!isDelayed) {
            BasicMiniGameScreen screen = new BasicMiniGameScreen(game);
            screen.setStage(selectedStage);

            game.setScreen(screen);
        }
    }

    /**
     * Change to the summary screen
     * @param gameStage the game type to show results from
     * @param game the current game instance
     * @param shouldDelay indicates whether next transition should be delayed
     */
    public static void changeToSummaryScreen(int gameStage, MyGdxGame game, boolean shouldDelay){
        if (!isDelayed){
            SummaryScreen screen = new SummaryScreen(game,game.gameTypes().get(gameStage));

            game.setScreen(screen);
            if (shouldDelay) delay();
        }

    }



    /**
     * Change to the result screen
     * @param type The game type
     * @param game this game instance
     * @param tuple2 the students  result
     * @param previousScreen the screen that the user should return to
     */
    public static void changeToResultScreen(BasicGameType type, MyGdxGame game, Tuple2<Boolean,Boolean> tuple2, BasicMiniGameScreen previousScreen){
        if (!isDelayed) {
            PuzzleResultScreen screen = new PuzzleResultScreen(type,tuple2,game,previousScreen);
            game.setScreen(screen);
        }
    }


    public static void changeToResultScreen(BasicGameType type, MyGdxGame game, Tuple3<Boolean,Boolean,Boolean> tuple2, BasicMiniGameScreen previousScreen){
        if (!isDelayed) {
            PuzzleResultScreen screen = new PuzzleResultScreen(type,tuple2,game,previousScreen);
            game.setScreen(screen);
        }
    }


    public static void changeToResultScreen(BasicGameType type, MyGdxGame game, Tuple4<Boolean,Boolean,Boolean,Boolean> tuple2, BasicMiniGameScreen previousScreen){
        if (!isDelayed) {
            PuzzleResultScreen screen = new PuzzleResultScreen(type,tuple2,game,previousScreen);
            game.setScreen(screen);
        }
    }




}
