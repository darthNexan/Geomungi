package com.pablo.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.pablo.gameutils.GameInfo;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.ShapeGeneration;
import com.pablo.gameutils.ShapeIdentification;
import com.pablo.gameutils.Tuple2;
import com.pablo.gameutils.Tuple3;
import com.pablo.gameutils.Tuple4;
import com.pablo.gameutils.UISprite;
import com.pablo.input.BasicsInput;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Dennis on 04/02/2018.
 */

public class BasicMiniGameScreen implements Screen {

    private MyGdxGame game;

    private Sprite bgSprite;//minigame background
    private SpriteBatch batch;//batch is used to render images
    private OrthographicCamera camera;//camera that is used to view the scene
    private ShapeRenderer line1;//used to render shapes

    private Vector2 currentPoint;//current location of finger
    private Vector<Vector2> points;//points to be used
    private Vector<Vector<Vector2>> selectedPoints;//points that have been selected
    private boolean isComplete = false;
    private BasicGameType basicGameType;

    public UISprite checkButton;
    public UISprite XButton;


    public UISprite tryAgainButton;
    public UISprite quitButton;
    public UISprite nextButton;

    private ArrayList<BasicGameType> gameStages;

    private int currStages;
    public int currScore;
    public int totalScore;



    public BasicMiniGameScreen(MyGdxGame game){
        this.game=game;
        //setting up background

        this.batch= game.getBatch();
        this.line1 = new ShapeRenderer();

        setUpSprite();

        //setting up camera
        this.camera = game.getCamera();

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.viewportWidth = GameInfo.CAMERA_WIDTH;
        camera.viewportHeight = GameInfo.CAMERA_HEIGHT;
        camera.update();
        //camera.rotate(90);


        selectedPoints = new Vector<Vector<Vector2>>();
        selectedPoints.add(new Vector<Vector2>());
        currentPoint = new Vector2(-1,-1);
        Gdx.input.setInputProcessor(new BasicsInput(currentPoint, this));
        gameStages = BasicGameType.getGameTypes();
        currStages = 0;
        init();



    }//Constructor

    /**
     * initializes sprites
     */
    private void setUpSprite(){
        Texture img = new Texture( "Pictures/bg.png");
        this.bgSprite = new Sprite(img);
        this.XButton = new UISprite(new Texture("Pictures/XNoBG.PNG"));
        float ratio = XButton.getHeight() / XButton.getWidth();

        XButton.setSize(GameInfo.CAMERA_WIDTH/8 , (GameInfo.CAMERA_WIDTH/8) * ratio);
        bgSprite.setSize(GameInfo.CAMERA_WIDTH, GameInfo.CAMERA_HEIGHT);
        bgSprite.setPosition(0,0);
        XButton.setPosition( 8.5f * GameInfo.CAMERA_WIDTH/10, 9.5f * GameInfo.CAMERA_HEIGHT /10);

        this.checkButton = new UISprite(new Texture("Pictures/tickNoBg.PNG"));
        this.checkButton.setPosition(0.5f * GameInfo.CAMERA_WIDTH/10, 9.5f * GameInfo.CAMERA_HEIGHT/10);
        this.checkButton.setSize(GameInfo.CAMERA_WIDTH/8 , (GameInfo.CAMERA_WIDTH/8) * ratio);
    }//SetUpSprite

    /**
     * Sets up new game
     */
    private void init(){
        if (currStages>gameStages.size()-1) {
            currStages =0;
        }

        basicGameType = gameStages.get(currStages);

        selectedPoints.clear();
        selectedPoints.add(new Vector<Vector2>());
        points = ShapeGeneration.generate(basicGameType);
    }
    @Override
    public void show() {

    }

    /**
     * Tests the selected points to determine whether it matches the correct result
     * @return a boolean indicating whether the puzzle was successfully completed
     */

    @SuppressWarnings("ConstantConditions")
    private boolean testSolution(){

        boolean res = false;
        System.out.println(selectedPoints.size());
        if (basicGameType.isParallel() && selectedPoints.size() == 3 && basicGameType.isParallel()){

            res = selectedPoints.get(0).size()==2 && selectedPoints.get(1).size() == 2 &&
                    selectedPoints.get(2).isEmpty() &&
                    ShapeIdentification.identifyParallelLines(selectedPoints.firstElement(), selectedPoints.get(1));

            System.out.println(res);


        }
        else if(basicGameType.isAngle()){
            boolean res0 = selectedPoints.firstElement().size() == 3;
            if (res0) {
                float angleSize = ShapeIdentification.calculateAngle(selectedPoints.firstElement().get(0),
                        selectedPoints.firstElement().get(1), selectedPoints.firstElement().get(2));

                //System.out.println(angleSize);
                res = basicGameType.isAcute() ? angleSize < 90f : basicGameType.isObtuse() ? angleSize > 90f :
                        angleSize == 90f;
            }
            System.out.println(res);
        }
        else if (basicGameType.isShape()){

            if (basicGameType.isTriangle()){
                Tuple3<Boolean,Boolean,Boolean> triangleRes=null;
                if (basicGameType.isType0()){
                    triangleRes = ShapeIdentification.triangleL(selectedPoints.firstElement(),
                            ShapeIdentification.EQUILATERAL);

                }
                else if (basicGameType.isType1()){
                    triangleRes = ShapeIdentification.triangleL(selectedPoints.firstElement(),
                            ShapeIdentification.ISOSCELES);

                }
                else if (basicGameType.isType2()){
                    triangleRes = ShapeIdentification.triangleL(selectedPoints.firstElement(),
                            ShapeIdentification.SCALENE);
                }
                else if (basicGameType.isType3()){
                    triangleRes = ShapeIdentification.triangleA(selectedPoints.firstElement(),
                            ShapeIdentification.ACUTE);
                }
                else if (basicGameType.isType4()){
                    triangleRes = ShapeIdentification.triangleA(selectedPoints.firstElement(),
                            ShapeIdentification.OBTUSE);
                }
                else if (basicGameType.isType5()) {
                    triangleRes = ShapeIdentification.triangleA(selectedPoints.firstElement(),
                            ShapeIdentification.RIGHT);
                }


                System.out.println(triangleRes);
                res = triangleRes.x1 && triangleRes.x2 && triangleRes.x3;
            }
            else if (basicGameType.isQuad()){
                Tuple4<Boolean,Boolean,Boolean,Boolean> quadRes1 = null;
                Tuple3<Boolean,Boolean,Boolean> quadRes2 = null;
                Tuple2<Boolean,Boolean> quadRes3 =null;

                if(basicGameType.isType0()){

                    quadRes1 = ShapeIdentification.checkParallelogram(selectedPoints.firstElement(),true,true);

                }else if (basicGameType.isType1()){
                    quadRes1 = ShapeIdentification.checkParallelogram(selectedPoints.firstElement(),true,false);
                }else if (basicGameType.isType2()){
                    quadRes2 = ShapeIdentification.checkKite(selectedPoints.firstElement());
                }else if(basicGameType.isType3()){
                    quadRes1 = ShapeIdentification.checkParallelogram(selectedPoints.firstElement(),false,true);
                }else if(basicGameType.isType4()){
                    quadRes1 = ShapeIdentification.checkParallelogram(selectedPoints.firstElement(),false,false);
                }else if(basicGameType.isType5()){
                    quadRes2 = ShapeIdentification.checkTrapezium(selectedPoints.firstElement());
                }
                else {
                    quadRes3 = ShapeIdentification.checkShape(selectedPoints.firstElement(), basicGameType);
                }

                if (!(quadRes1 == null)){
                    res = quadRes1.x1 && quadRes1.x2 && quadRes1.x3 && quadRes1.x4;
                    System.out.println(quadRes1);
                }
                else if (!(quadRes2 == null)){
                    res = quadRes2.x1 && quadRes2.x2 && quadRes2.x3;
                    System.out.println(quadRes2);
                }
                else {
                    res = quadRes3.x1 && quadRes3.x2;
                    System.out.println(quadRes3);
                }
            }
            else {
                Tuple2<Boolean,Boolean> shapeRes = ShapeIdentification.checkShape(selectedPoints.firstElement(),basicGameType);
                res = shapeRes.x1 && shapeRes.x2;
                System.out.println(shapeRes);
            }

        }
        return res;

    }//

    @Override
    public void render(float delta) {
        updateCurrentPoints();
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            init();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)){
            currStages ++;
            init();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bgSprite.draw(batch);
        XButton.draw(batch);
        checkButton.draw(batch);
        batch.end();

        //render lines
        line1.setProjectionMatrix(camera.combined);
        line1.setAutoShapeType(true);
        line1.begin();
        line1.set(ShapeRenderer.ShapeType.Filled);
        line1.setColor(Color.BLACK);


        //display all points on the screen
        for (Vector2 v : points){
            line1.circle(v.x,v.y,2.f,10);

        }
        int i = 0;

        // draw lines between selected points
        while (i<selectedPoints.size()){
            int j = 0;
            Vector<Vector2> line = selectedPoints.get(i);

            while (j<line.size() -1){
                line1.rectLine(line.get(j),line.get(j+1),1f);
                j++;

            }
            i++;

        }

        //draws last line from a selected point to the current finger location
        if( currentPoint.x !=-1 && !selectedPoints.get(selectedPoints.size()-1).isEmpty()){
            int tempInt = selectedPoints.get(selectedPoints.size()-1).size();
            Vector2 tempPoint =selectedPoints.get(selectedPoints.size() - 1 ).get(tempInt-1);

            line1.rectLine(currentPoint,tempPoint,0.1f);


        }


        if (XButton.isClicked()){
            System.out.println("Touched X button");
            selectedPoints.clear();
            selectedPoints.add(new Vector<Vector2>());
        }
        if (checkButton.isClicked() && !selectedPoints.isEmpty()){
            System.out.println("Touched check answer button" );
            testSolution();
        }

        line1.end();


    }//render

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


    private boolean updateCurrentPoints(){

        int i = 0;
        if(currentPoint.x==-1 && selectedPoints.get(selectedPoints.size()-1).size() == 1 ){
            selectedPoints.get(selectedPoints.size()-1).clear();
        }
        if (currentPoint.x ==-1 && !selectedPoints.get(selectedPoints.size()-1).isEmpty()&& basicGameType.isParallel()){

            selectedPoints.add(new Vector<Vector2>());
        }//if composed of several lines add a new vector

        if (currentPoint.x ==-1 && currentPoint.y ==-1) return false;

        //just added
        boolean complete = basicGameType.isShape() && selectedPoints.get(0).size()>3 &&
                selectedPoints.get(0).firstElement().equals(selectedPoints.get(0).lastElement());
        if (complete) {
           // System.out.println("ran complete shape");
            return false;
        }



        Vector<Vector2> linesToUpdate = selectedPoints.get(selectedPoints.size() -1);

        while (i < points.size()){

            if (currentPoint.dst(points.get(i)) < 1.5f &&
                    ( linesToUpdate.size() == 0 || !points.get(i).equals(linesToUpdate.get(linesToUpdate.size() -1)))){

                //if (validatePoint(linesToUpdate,points.get(i))){
                linesToUpdate.add(new Vector2(points.get(i).x, points.get(i).y));
                //}
                Gdx.input.vibrate(20);
                return true;
            }

            i++;

        }

        return false;
    }




    private boolean validatePoint(Vector<Vector2> points, Vector2 newPoint){
        if (points.firstElement().equals(newPoint)) return true;
        else{
            boolean res = true;
            for (int i = 1; i<points.size();i++){
                if (newPoint.equals(points.get(i))){
                    res = false;
                    break;
                }
            }
            return res;
        }
    }


    /**
     *
     */
    private void readjson(){

        JsonReader reader = new JsonReader();
        JsonValue value = reader.parse(Gdx.files.internal("basics.json")).get("Progress").get(0);
        //reads in the saved state for the score level  and level score
        currScore = value.getInt("levelScore");
        totalScore = value.getInt("score");
        currStages = value.getInt("Level");

    }

    private void writeJson(){

    }

}//BasicsMiniGameScreen
