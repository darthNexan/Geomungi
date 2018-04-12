package com.pablo.screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Align;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Transition;
import com.pablo.gameutils.Tuple4;
import com.pablo.input.SummaryScreenInput;



/**
 * Created by Dennis on 29/03/2018.
 */

public class SummaryScreen extends AbstractResultClass {

    private int resultToBeDisplayed;
    private GestureDetector detector;
    private boolean resultsExist;


    public SummaryScreen(MyGdxGame game, BasicGameType type ){

        super(game,type,type.getResults().isEmpty() ? new Tuple4<Boolean,Boolean,Boolean,Boolean>(false,false,false,false) : type.getResults().get(0));
        resultsExist = !type.getResults().isEmpty();
        detector = new GestureDetector(new SummaryScreenInput(this));
        resultToBeDisplayed = 0;

    }



    /**
     * Called when this screen becomes the current screen for a game.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(detector);
        Gdx.input.setCatchBackKey(true);

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        checkInput();
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


        initializeRes(type.getResults().isEmpty()? new Tuple4<Boolean,Boolean,Boolean,Boolean>(false,false,false,false):type.getResults().get(resultToBeDisplayed));
        calc();
    }

    @Override
    protected void drawText(SpriteBatch batch) {
        if (resultsExist){
            float initialX =GameInfo.CAMERA_WIDTH/8;
            float initialY = GameInfo.CAMERA_HEIGHT*7/8;
            fontLg.draw(batch,header + " Score",initialX,initialY);

            float deltaX = 6*GameInfo.CAMERA_WIDTH/8;

            int i;
            float deltaY = GameInfo.CAMERA_HEIGHT/10;
            for (i =0;i<messages.length;i++){
                if (i < modeSpecificMessages.length)
                    fontReg.draw(batch,modeSpecificMessages[i],initialX,initialY-(deltaY*(i+1)));

                fontReg.draw(batch,messages[i],initialX+deltaX,initialY-(deltaY*(i+1)));
            }

            drawFeedback(initialX,initialY -(deltaY*(modeSpecificMessages.length)),batch);
        }
        else {
            fontLg.draw(batch,"There are no results to display", GameInfo.CAMERA_WIDTH,GameInfo.CAMERA_HEIGHT/2,GameInfo.CAMERA_WIDTH, Align.left,true);
        }
    }

    private void checkInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            Transition.changeToSummarySelectionScreen(game,false);
        }
    }
}
