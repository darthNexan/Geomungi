package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dennis on 04/02/2018.
 */

public class MainMenu implements Screen {

    private MyGdxGame game;
    private Texture img;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    ShapeRenderer line1;

    private Vector2 currentPoint;
    private ArrayList<Vector2> points;
    private ArrayList<Vector2> selectedPoints;
    private boolean isComplete = false;

    public MainMenu(MyGdxGame game){
        this.game=game;
        this.img = new Texture("bg.png");
        this.batch= game.getBatch();
        this.line1 = new ShapeRenderer();
        this.camera = game.getCamera();
        init();

    }

    private void init(){
        points.clear();
        selectedPoints.clear();
        Random rand = GameInfo.random;
        final int maxX = (int)Math.ceil(camera.viewportWidth);
        final int maxY = (int)Math.ceil(camera.viewportHeight);

        for (int i = 0; i < 10; i++) {
            float x = rand.nextInt(maxX);
            float y = rand.nextInt(maxY);

            points.add(new Vector2(x,y));
        }

    }
    @Override
    public void show() {

    }

    /**
     * update function performs computation based on the rules of the game.
     * If an angle needs to be made only three points need to be selected.
     * If a shape needs to be made the shape needs to be connected
     */
    @SuppressWarnings({"ConstantConditions", "StatementWithEmptyBody"})
    private void update(){

        isComplete = isAngle() && selectedPoints.size() == 3 ||
                isShape() && !selectedPoints.isEmpty() && selectedPoints.get(0) == selectedPoints.get(selectedPoints.size() -1)
                || isLinePair() && selectedPoints.size() ==4;


        if(isComplete && testSolution()){


            init();
        }
        else if(isComplete){


            init();
        }
        else {

        }

    }

    @SuppressWarnings("ConstantConditions")
    private boolean testSolution(){

        if( isComplete){
            //test  the conditions here
        }

        return false;

    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img,0,0);
        batch.end();

        line1.begin(ShapeRenderer.ShapeType.Filled);
        line1.setColor(Color.BLACK);

        line1.rectLine(0,0,GameInfo.WIDTH,GameInfo.HEIGTH,3);
        line1.end();

        line1.begin(ShapeRenderer.ShapeType.Line);
        line1.setColor(Color.BLACK);
        line1.cone(GameInfo.WIDTH/2 -100,GameInfo.HEIGTH+100,0,20,20);

        line1.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /**
     * TODO Edit this method to make it functional
     *
     * @return Tests if the current game is a shape.
     */
    private boolean isShape(){
        return false;
    }

    /**
     * TODO Edit this method to make it functional
     * @return Tests if the current game is an angle.
     */

    private boolean isAngle(){
        return false;
    }

    private boolean isLinePair(){return false;}

    /**
     *
     * @param v1 end of one line
     * @param v2 the point of intersection
     * @param v3 end of one line
     * @return Angle at v2
     */
    private float calculateAngle(Vector2 v1, Vector2 v2, Vector2 v3){

        Vector2 vector1 = v1.sub(v2);
        Vector2 vector2 = v2.sub(v3);
        return vector1.angle(vector2);
    }

    private boolean isParallel(Vector2 v1, Vector2 v2, Vector2 v3, Vector2 v4){
        Vector2 vector1 = v1.sub(v2);
        Vector2 vector2 = v3.sub(v4);
        return vector1.hasOppositeDirection(vector2) || vector1.hasSameDirection(vector2);
    }


}
