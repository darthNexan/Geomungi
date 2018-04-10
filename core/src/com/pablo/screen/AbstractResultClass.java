package com.pablo.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Tuple;
import com.pablo.gameutils.Tuple2;
import com.pablo.gameutils.Tuple3;
import com.pablo.gameutils.Tuple4;

import java.util.List;

import static java.lang.Math.ceil;

/**
 * Created by Dennis on 08/04/2018.
 */

@SuppressWarnings("WeakerAccess")
abstract public class AbstractResultClass implements Screen {

    protected BasicGameType type;
    protected Tuple2<Boolean,Boolean> tuple2;
    protected Tuple3<Boolean,Boolean,Boolean> tuple3;
    protected Tuple4<Boolean,Boolean,Boolean,Boolean> tuple4;
    protected int score;
    protected MyGdxGame game;
    protected BasicMiniGameScreen prevScreen;
    protected String[] modeSpecificMessages;
    protected String[] modeSpecificFeedback;
    protected String header;
    protected String[] messages;

    protected BitmapFont fontReg;
    protected BitmapFont fontLg;

    protected Camera camera;
    protected boolean isCorrect;
    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch batch;

    protected AbstractResultClass(MyGdxGame game, BasicGameType type, Tuple result){
        this.game = game;
        this.type = type;
        initializeRes(result);
        this.camera = game.getCamera();
        this.shapeRenderer = new ShapeRenderer();
        this.batch = game.getBatch();
        calc();
        generateBitmapFont();
        getText();


    }


    @SuppressWarnings("unchecked")
    protected void initializeRes(Tuple tuple){
        boolean isCorrectType = true;
        List<Class> listOfTypes = tuple.getTypes();
        for (int i = 0; i<listOfTypes.size() && isCorrectType;i++){
            isCorrectType= listOfTypes.get(i).equals(Boolean.class);
        }


        if (tuple instanceof Tuple2 && isCorrectType) tuple2 = (Tuple2<Boolean, Boolean>) tuple;
        else if (tuple instanceof Tuple3 && isCorrectType) tuple3 = (Tuple3<Boolean,Boolean,Boolean>)tuple;
        else if (tuple instanceof Tuple4 && isCorrectType) tuple4 = (Tuple4<Boolean, Boolean, Boolean, Boolean>) tuple;
    }



    /**
     * generates the fonts to be displayed
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void generateBitmapFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param= new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.incremental=true;
        Tuple2<Integer,Float> lgScale = scaleHelper(6);
        Tuple2<Integer,Float> regScale = scaleHelper(4);
        //param.shadowOffsetX = 1;
        //param.shadowOffsetY = 1;
        //param.shadowColor = Color.DARK_GRAY;

        param.minFilter = Texture.TextureFilter.Linear;
        param.magFilter = Texture.TextureFilter.Linear;
        param.mono = true;
        param.kerning = true;


        param.color = Color.BLACK;
        param.size = regScale.x1;
        fontReg = generator.generateFont(param);
        fontReg.getData().setScale(regScale.x2,regScale.x2);
        fontReg.getData().padRight = -0.1f;
        fontReg.getData().padLeft = -0.1f;

        param.color = Color.BLACK;
        param.size = lgScale.x1 ;


        fontLg = generator.generateFont(param);
        fontLg.getData().setScale(lgScale.x2,lgScale.x2);
        fontLg.getData().padLeft=-0.1f;
        fontLg.getData().padRight=-0.1f;
        fontReg.setUseIntegerPositions(false);
        fontLg.setUseIntegerPositions(false);

//        fontLg.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        fontLg.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fontReg.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        generator.dispose();

    }

    private Tuple2<Integer,Float> scaleHelper(final int size){
        Integer sizeRes = (int)ceil(size * (Gdx.graphics.getHeight() / GameInfo.CAMERA_HEIGHT));
        Float scaleRes =GameInfo.CAMERA_HEIGHT/Gdx.graphics.getHeight();
        return new Tuple2<Integer,Float>(sizeRes, scaleRes);
    }
    /**
     * Calculate score
     */
    protected void calc(){
        if (type.isParallel()){
            calcScoreRes0();
        }
        else if (type.isAngle()){
            calcScoreRes0();
        }
        else if (type.isShape()){
            if (!type.isTriangle() && !type.isQuad()){
                calcScoreRes0();
            }
            else if (type.isTriangle() && type.getSpecializedCategory() <= 5){
                calcScoreRes1();
            }
            else if (type.isQuad() && type.getSpecializedCategory() <= 5){
                if (type.isType0()) calcScoreSquare();
                else if (type.isType1()) calcScoreRect();
                else if (type.isType3()) calcScoreRhombus();
                else if (type.isType4()) calcScoreParallelogram();
                else calcScoreRes1();
            }
            else {
                calcScoreRes0();
            }
        }


        //TODO implement in PuzzleResultScreenSubclass
        // displayHintSprite = prevScreen.numberOfAttempts ==0 && !isCorrect;
    }

    /**
     * Calculates the score  and generates a messsage to be displayed.
     */

    private void calcScoreRes1(){

        messages = new String[4];
        score += tuple3.x1 ? 10:0;
        score += tuple3.x2 ? 10:0;
        score += tuple3.x3 ? 10:0;
        messages[0] = Integer.toString(tuple3.x1?10:0);
        messages[1] = Integer.toString(tuple3.x2?10:0);
        messages[2] = Integer.toString(tuple3.x3?10:0);
        messages[3] = Integer.toString(score);
        isCorrect = tuple3.x1 && tuple3.x2 && tuple3.x3;
    }


    private void calcScoreRes0(){

        messages = new String[3];
        score += tuple2.x1? 10:0;
        messages[0] = Integer.toString(tuple2.x1? 10:0);
        score += tuple2.x2? 10:0;
        messages[1] = Integer.toString(tuple2.x2? 10:0);
        messages[2] = Integer.toString(score);

        isCorrect = tuple2.x1 && tuple2.x2;
    }

    private void calcScoreSquare(){

        messages = new String[5];
        score += tuple4.x1?10:0;
        score += tuple4.x2?10:0;
        score += tuple4.x3?10:0;
        score += tuple4.x4?10:0;

        messages[0] = Integer.toString(tuple4.x1?10:0);
        messages[1] = Integer.toString(tuple4.x2?10:0);
        messages[2] = Integer.toString(tuple4.x3?10:0);
        messages[3] = Integer.toString(tuple4.x4?10:0);
        messages[4] = Integer.toString(score);

        isCorrect = tuple4.x1 && tuple4.x2 && tuple4.x3 && tuple4.x4;
    }

    private void calcScoreRect(){
        messages = new String[4];
        score += tuple4.x1?10:0;
        score += tuple4.x2?10:0;
        score += tuple4.x3?10:0;


        messages[0] = Integer.toString(tuple4.x1?10:0);
        messages[1] = Integer.toString(tuple4.x2?10:0);
        messages[2] = Integer.toString(tuple4.x3?10:0);
        messages[3] = Integer.toString(score);

        isCorrect =tuple4.x1 && tuple4.x2 && tuple4.x3;
    }

    private void calcScoreRhombus(){

        messages = new String[4];
        score += tuple4.x1?10:0;
        score += tuple4.x2?10:0;
        score += tuple4.x4?10:0;

        messages[0] = Integer.toString(tuple4.x1?10:0);
        messages[1] = Integer.toString(tuple4.x2?10:0);
        messages[2] = Integer.toString(tuple4.x4?10:0);
        messages[3] = Integer.toString(score);

        isCorrect = tuple4.x1 && tuple4.x2 && tuple4.x4;
    }


    private void calcScoreParallelogram(){

        messages = new String[3];
        score += tuple4.x1?10:0;
        score += tuple4.x2?10:0;
        messages[0] = Integer.toString(tuple4.x1?10:0);
        messages[1] = Integer.toString(tuple4.x2?10:0);

        messages[2] = Integer.toString(score);


        isCorrect = tuple4.x1 && tuple4.x2;
    }


    private void getText(){
        modeSpecificMessages = type.getMessages();
        modeSpecificFeedback = type.getFeedback();
        header = type.header;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0,0, GameInfo.CAMERA_WIDTH,GameInfo.CAMERA_HEIGHT);
        shapeRenderer.end();

    }


    /**
     * Lays out the text on the screen
     * @param batch the sprite batch used to draw the texture.
     */
    protected void drawText(SpriteBatch batch){
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


    }

    protected void drawFeedback(float x, float y, SpriteBatch batch){
        String outputMessage = null;
        for(int i =0;i<modeSpecificFeedback.length;i++){
            if (messages[i].equals("0")){
                outputMessage = modeSpecificFeedback[i];
                break;
            }

        }

        fontReg.draw(batch,"Hint: "+outputMessage,x,y);
    }
}
