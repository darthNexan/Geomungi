package com.pablo.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Transition;
import com.pablo.gameutils.Utilities;

import static java.lang.Math.abs;
import static java.lang.Math.floor;


/**
 * Created by Dennis on 29/03/2018.
 */

public class MenuScreen implements Screen {


    private MyGdxGame game;
    private GestureDetector detector;
    private SpriteBatch batch;
    private Camera camera;
    private BitmapFont font;

    private Rectangle[] textBoxes;
    private String[] options;
    private ShapeRenderer shapeRenderer;

    public MenuScreen(MyGdxGame game){
        this.game = game;
        batch = game.getBatch();
        shapeRenderer = new ShapeRenderer();
        camera = game.getCamera();
        font = Utilities.generateFont(6, Color.BLACK, Color.WHITE, 2f);

        options = new String[]{"Basics", "View Progress"};

        float rectangleHeight = 30f;
        float rectangleWidth = 80f;
        float x = GameInfo.CAMERA_WIDTH/2;
        float y = GameInfo.CAMERA_HEIGHT/2;

        textBoxes= new Rectangle[]{
                new Rectangle(x - rectangleWidth/2, y + rectangleHeight + 10f, rectangleWidth, rectangleHeight),
                new Rectangle(x - rectangleWidth/2, y - (rectangleHeight + 10f), rectangleWidth, rectangleHeight),
        };

        Gdx.input.setInputProcessor(null);

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);



        //render text box for menu items
        for (Rectangle aTextBox : textBoxes) {

            if (aTextBox.y > -1*(aTextBox.height) && aTextBox.y<GameInfo.CAMERA_HEIGHT) // if the box is not on the screen  don't display
                shapeRenderer.rect(aTextBox.x, aTextBox.y, aTextBox.width, aTextBox.height);

        }

        shapeRenderer.end();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i =0 ; i< textBoxes.length;i++){
            Rectangle aTextBox = textBoxes[i];

            if (aTextBox.y > -1*aTextBox.height && aTextBox.y<GameInfo.CAMERA_HEIGHT) //if the box is not on the screen don't display the text
                font.draw(batch,options[i],aTextBox.x + aTextBox.width/4,aTextBox.y + aTextBox.height/2,
                        aTextBox.width, Align.left, true);
        }

        batch.end();


        checkInput();



    }

    /**
     * @param width
     * @param height
     *
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     *
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
        shapeRenderer.dispose();
    }


    /**
     * Called in the render loop and checks if the user interacts with the screen
     */
    public void checkInput(){
        float x =  Gdx.input.getX();
        float y = Gdx.input.getY();
        float newX = abs(x * GameInfo.CAMERA_WIDTH / Gdx.graphics.getWidth());
        float newY = GameInfo.CAMERA_HEIGHT - abs(y * GameInfo.CAMERA_HEIGHT / Gdx.graphics.getHeight());

        if (textBoxes[0].contains(newX,newY)) {
            Transition.changeToLevelSelectionScreen(game,true);
        }
        else if (textBoxes[1].contains(newX,newY)) {

        }
    }
}
