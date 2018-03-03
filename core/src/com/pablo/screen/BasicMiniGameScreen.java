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
import com.pablo.gameutils.GameInfo;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.ShapeGeneration;
import com.pablo.gameutils.ShapeIdentification;
import com.pablo.gameutils.Tuple2;
import com.pablo.gameutils.UISprite;
import com.pablo.input.BasicsInput;

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
    public UISprite pauseButtonSprite;

    public UISprite tryAgainButton;
    public UISprite quitButton;
    public UISprite nextButton;

    private ArrayList<BasicGameType> gameStages;

    private int currStages;
    public boolean endScreen;


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
        this.pauseButtonSprite = new UISprite(new Texture("Pictures/Pause.png"));
        float ratio = pauseButtonSprite.getHeight() / pauseButtonSprite.getWidth();

        pauseButtonSprite.setSize(GameInfo.CAMERA_WIDTH/8 , (GameInfo.CAMERA_WIDTH/8) * ratio);
        bgSprite.setSize(GameInfo.CAMERA_WIDTH, GameInfo.CAMERA_HEIGHT);
        bgSprite.setPosition(0,0);
        pauseButtonSprite.setPosition( 8.5f * GameInfo.CAMERA_WIDTH/10, 9.5f * GameInfo.CAMERA_HEIGHT /10);

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


    @SuppressWarnings("ConstantConditions")
    private boolean testSolution(){

        boolean res = false;
        System.out.println(selectedPoints.size());
        if (basicGameType.isParallel() && selectedPoints.size() == 3 && basicGameType.isParallel()){

            System.out.println(selectedPoints.get(0).size());
            System.out.println(selectedPoints.get(1).size());
            System.out.println(selectedPoints.get(2).size());
            System.out.println(basicGameType.isParallel());
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
                Tuple2<Boolean,Boolean> triangleRes=null;
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
                else {
                    triangleRes = ShapeIdentification.triangleA(selectedPoints.firstElement(),
                            ShapeIdentification.RIGHT);
                }

                System.out.println(triangleRes);
                res = triangleRes.x1 && triangleRes.x2;
            }
            else if (basicGameType.isQuad()){

            }
            else {

            }

        }
        return res;

    }

    @Override
    public void render(float delta) {
        updateCurrentPoints();
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            init();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)){
            currStages ++;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render background
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bgSprite.draw(batch);
        pauseButtonSprite.draw(batch);
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


        if (pauseButtonSprite.isClicked()){
            System.out.println("Touched pause button");
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
        Vector2 vector1 = new Vector2(v1.x + v2.x, v1.x + v2.x);

        Vector2 vector2 = new Vector2(v3.x +v4.x, v3.y + v4.y);

       // System.out.println("ran");

        return vector1.hasSameDirection(vector2) || vector1.hasOppositeDirection(vector2);
    }


    private boolean updateCurrentPoints(){

        int i = 0;
        if(currentPoint.x==-1 && selectedPoints.get(selectedPoints.size()-1).size() == 1 ){
            selectedPoints.get(selectedPoints.size()-1).clear();
        }
        if (currentPoint.x ==-1 && !selectedPoints.get(selectedPoints.size()-1).isEmpty()&& basicGameType.isParallel()){

            selectedPoints.add(new Vector<Vector2>());
        }//if composed of several lines add a new vector

        Vector<Vector2> linesToUpdate = selectedPoints.get(selectedPoints.size() -1);

        while (i < points.size()){

            if (currentPoint.dst(points.get(i)) < 1.5f &&
                    ( linesToUpdate.size() == 0 || !points.get(i).equals(linesToUpdate.get(linesToUpdate.size() -1)))){

                linesToUpdate.add(new Vector2(points.get(i).x, points.get(i).y));
                Gdx.input.vibrate(20);
                return true;
            }

            i++;

        }

        return false;
    }


    private Vector2 generateVector(){
        int x,y;

        x = GameInfo.random.nextInt(10);
        y = GameInfo.random.nextInt(10);

        return new Vector2(x,y);

    }


    private BasicGameType createGame(){
        return new BasicGameType();
    }

    private Vector<Vector2> generateParallelPoints(){
        int i = 0;
        Random random = GameInfo.random;
        Vector<Vector2> vec = new Vector<Vector2>();

        Vector2 identity = new Vector2(1,0);
        final int Scale =(int)Math.floor(GameInfo.CAMERA_WIDTH/2);


       // System.out.println(i);

        while (i<=2){


            Vector2 temp0 = new Vector2();
            Vector2 temp1 = new Vector2();

            //System.out.println( xInc * temp.x1 );
            //System.out.println(yInc * temp.x2);
            temp0.x = random.nextInt( (int)GameInfo.CAMERA_WIDTH );
            temp0.y = random.nextInt( (int)GameInfo.CAMERA_HEIGHT);

//           // temp = findSection(useXSection);

            temp1.x =temp0.x;
            temp1.y =temp0.y;

            do {
                temp1.x =temp0.x;
                temp1.y =temp0.y;
                identity.x =1;
                identity.y =0;

                identity.scl(Scale);

                identity.rotate(random.nextInt(360));
                temp1.add(identity);

            }while (!checkVisibility(temp1));//ensures that the point is within the screen



            vec.add(temp0);
            vec.add(temp1);


            Vector2 temp2 = new Vector2(temp0.x,temp0.y);
            Vector2 temp3 = new Vector2(temp1.x, temp1.y);


            do {

                temp2.x = temp0.x;
                temp2.y = temp0.y;
                temp3.x = temp1.x;
                temp3.y = temp1.y;

                identity.x =1;
                identity.y=1;

                identity.scl(Scale/2);
                identity.rotate(random.nextInt(360));
                temp2.add(identity);
                temp3.add(identity);
                if(checkVisibility(temp2) && checkVisibility(temp3)) {

                    vec.add(temp2);
                    vec.add(temp3);

                    break;
                }

            }while (true);
            i+=2;

        }





        return vec;
    }//parallel points

    private Vector<Vector2> generateKitePoints(float length, float width){
        float angleToUse = (float)GameInfo.random.nextInt(360);
        angleToUse = 0f;
        int scale = (int)GameInfo.CAMERA_WIDTH/5;
        float topAngle = (float)GameInfo.random.nextInt(25) +20;
        Vector2 lineRight = new Vector2(1,0);
        Vector2 lineLeft = new Vector2(1,0);
        lineRight.scl(scale);
        lineLeft.scl(scale);
        lineRight.rotate(topAngle + 270);
        lineLeft.rotate(270-topAngle);

        Vector2 temp0 = new Vector2(GameInfo.random.nextInt(6*(int)GameInfo.CAMERA_WIDTH)/8 +
                GameInfo.CAMERA_WIDTH/8, GameInfo.random.nextInt((int)GameInfo.CAMERA_HEIGHT - (int)GameInfo.CAMERA_HEIGHT/2) +
                GameInfo.CAMERA_HEIGHT/2);

        Vector2 temp1 = new Vector2(temp0.x, temp0.y);
        temp1.sub(0,scale * 2.2f);

        Vector2 temp3 = new Vector2(temp0.x,temp0.y);
        temp3.add(lineRight);
        Vector2 temp4 = new Vector2(temp0.x,temp0.y);
        temp4.add(lineLeft);

        Vector<Vector2> v = new Vector<Vector2>();

        v.add(temp0);
        v.add(temp1);
        v.add(temp3);
        v.add(temp4);

        return v;
    }

    private Vector<Vector2> generateParallelogram(float angle0,  final float base, final float height){

        float angleInUse = GameInfo.random.nextInt(45);
        Vector2 translationVectorX = new Vector2(base,0);

        //noinspection SuspiciousNameCombination
        Vector2 translationVectorY = new Vector2(height,0);

        translationVectorY.rotate(angleInUse);
        translationVectorY.rotate(angle0);

        translationVectorX.rotate(angleInUse);

        Vector2 temp0 = new Vector2(25,25);
        Vector2 temp1 = new Vector2(0,0);
        Vector2 temp2 = new Vector2(0,0);
        Vector2 temp3 = new Vector2(0,0);

        temp1.add(temp0).add(translationVectorX);
        temp2.add(temp0).add(translationVectorY);
        temp3.add(temp1).add(translationVectorY);
        Vector<Vector2> vector2s = new Vector<Vector2>();

        vector2s.add(temp0);
        vector2s.add(temp1);
        vector2s.add(temp2);
        vector2s.add(temp3);

        return vector2s;

    }


    private Vector<Vector2> generateTrapezium (float a, float b, float height) {

        Vector<Vector2> v = new Vector<Vector2>();


        Vector2 topLine = new Vector2(a,0);

        Vector2 bottomLine = new Vector2(b,0);

        Vector2 h = new Vector2(0, height);
        Vector2 temp0 = new Vector2(0,0);
        Vector2 temp1 = new Vector2(0,0);
        Vector2 temp2 = new Vector2(0f,0f);
        Vector2 temp3 = new Vector2(0f,0f);


        do {
            float angleToUse = 45*GameInfo.random.nextFloat();

            float x = GameInfo.random.nextInt((int)GameInfo.CAMERA_WIDTH);
            float y = GameInfo.random.nextInt( (int)GameInfo.CAMERA_HEIGHT);
            topLine.x=a;
            topLine.y=0;

            bottomLine.x = b;
            bottomLine.y =0;

            h.x=0;
            h.y=height;
            h.rotate(angleToUse);

            temp0.x=x;
            temp0.y=y;

            temp1.x=x;
            temp1.y=y;

            temp2.x=0;
            temp2.y=0;

            temp3.x=0;
            temp3.y=0;



            temp1.add(topLine);

            temp2.add(h).add(temp0);

            temp3.add(temp2).add(bottomLine);
        }while (!checkVisibility(temp0) || !checkVisibility(temp1) ||
                !checkVisibility(temp2) || !checkVisibility(temp3));


        v.add(temp0);
        v.add(temp1);
        v.add(temp2);
        v.add(temp3);
            return v;
    }

    private Vector <Vector2> generateTriangle(float a, float b,  float angle0){

        Vector2 initalPoint = new Vector2();
        Vector2 sideA = new Vector2(1,0);
        Vector2 sideB = new Vector2(1,0);

        Vector2 point0 = new Vector2();
        Vector2 point1 = new Vector2();

        //noinspection StatementWithEmptyBody
        do{

            int angleToUse = GameInfo.random.nextInt(360);
            initalPoint.x = GameInfo.random.nextInt((int) Math.floor(GameInfo.CAMERA_WIDTH));
            initalPoint.y = GameInfo.random.nextInt((int) Math.floor(GameInfo.CAMERA_HEIGHT));

            sideA.x =1;
            sideA.y=0;
            sideB.x =1;
            sideB.y=0;
            point0.x =0;
            point1.x =0;
            point1.y=0;
            point0.y=0;




            sideA.scl(a);
            sideA.rotate(angleToUse);
            sideB.scl(b);
            sideB.rotate(angleToUse);

            sideB.rotate(angle0);

            point0.add(initalPoint).add(sideA);
            point1.add(initalPoint).add(sideB);




        }while (!checkVisibility(point0) || !checkVisibility(point1) || !checkVisibility(initalPoint));

        Vector<Vector2> vector = new Vector<Vector2>();
        vector.add(point0);
        vector.add(point1);
        vector.add(initalPoint);
        return vector;
    }

    private Vector2 genVector(){

        if (GameInfo.random.nextBoolean()){
            return new Vector2(GameInfo.random.nextInt((int)GameInfo.CAMERA_WIDTH/20),0);
        }
        else {
            return new Vector2(0,GameInfo.random.nextInt((int)GameInfo.CAMERA_WIDTH/20));

        }

    }

    public Tuple2<Integer,Integer> findSection(boolean[][] usedSpaces){


        int temp0,temp1;
        final int maxX = usedSpaces.length;
        final int maxY = usedSpaces[0].length;
        while (true){
            temp0 = GameInfo.random.nextInt(maxX);
            temp1 = GameInfo.random.nextInt(maxY);

            if(usedSpaces[temp0][temp1]){
               break;
            }


        }

        return new Tuple2<Integer, Integer>(temp0,temp1);
    }

    private boolean checkVisibility(Vector2 vector2){

        return vector2.x >1 && vector2.x<GameInfo.CAMERA_WIDTH && vector2.y >1 && vector2.y <GameInfo.CAMERA_HEIGHT ;
    }



}//BasicsMiniGameScreen
