package com.pablo.screen;

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
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.BasicGameType;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Tuple2;
import com.pablo.gameutils.Tuple3;
import com.pablo.gameutils.Tuple4;
import com.pablo.gameutils.UISprite;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;

/**
 * Created by Dennis on 08/03/2018.
 */

@SuppressWarnings("JavaDoc")
public class ResultScreen implements Screen {
    private BasicGameType type;
    private Tuple2<Boolean,Boolean> res0;
    private Tuple3<Boolean,Boolean,Boolean> res1;
    private Tuple4<Boolean,Boolean,Boolean,Boolean> res2;
    private int score;
    private String[] messages;
    private MyGdxGame game;
    private BasicMiniGameScreen prevScreen;
    private String[] modeSpecificMessages;
    private String[] modeSpecificFeedback;
    private String header;
    private UISprite nextSprite;
    private UISprite retrySprite;
    private UISprite feedbackSprite;
    private BitmapFont fontReg;
    private BitmapFont fontLg;
    private SpriteBatch batch;
    private Camera camera;
    private boolean isCorrect;
    private ShapeRenderer shapeRenderer;

    private boolean displayHintSprite;
    private boolean displayHint;


    /**
     * Default constructor used to initialized all the general attributes of the result screen
     * @param game The game class this screen belongs to
     * @param prevScreen the screen that launched this class
     * @param type The type of result to show
     */
    private ResultScreen(MyGdxGame game, BasicMiniGameScreen prevScreen, BasicGameType type){
        this.game = game;
        this.game.setScreen(this);
        this.score = 0;
        this.batch = this.game.getBatch();
        this.camera = this.game.getCamera();
        this.prevScreen = prevScreen;
        this.type = type;
        generateBitmapFont();
        initSprites();
        shapeRenderer = new ShapeRenderer();
        readModeSpecificMessages();

    }

    /**
     * The constructor for a game with a tuple 3 result
     * @param type
     * @param res1
     * @param game
     * @param prevScreen
     */
    public ResultScreen(BasicGameType type, Tuple3<Boolean, Boolean, Boolean> res1, MyGdxGame game, BasicMiniGameScreen prevScreen) {
        this(game, prevScreen,type);

        this.res1 = res1;
        calc();
        isCorrect = res1.x1 && res1.x2 && res1.x3;
        displayHintSprite=prevScreen.numberOfAttempts==0 && !isCorrect;
    }

    /**
     * The constructor for a game with a tuple 2 result
     * @param type
     * @param res0
     * @param game
     * @param prevScreen
     */
    public ResultScreen(BasicGameType type, Tuple2<Boolean, Boolean> res0 , MyGdxGame game, BasicMiniGameScreen prevScreen){
        this(game, prevScreen,type);

        this.res0 = res0;
        calc();
        isCorrect = res0.x1 && res0.x2;
        displayHintSprite=prevScreen.numberOfAttempts==0 && !isCorrect;

       //messages = new String[res0 == null? 3 : res1 ==null? 4:5];

    }

    /**
     * The constructor for a game with a tuple 4 result
     * @param type
     * @param res2
     * @param game
     * @param prevScreen
     */
    public ResultScreen(BasicGameType type, Tuple4<Boolean, Boolean, Boolean, Boolean> res2, MyGdxGame game, BasicMiniGameScreen prevScreen) {
        this(game, prevScreen,type);
        this.res2 = res2;
        calc();
        isCorrect = res2.x1 && res2.x3 && res2.x2 && res2.x4;
        displayHintSprite=prevScreen.numberOfAttempts==0 && !isCorrect;

    }

    /**
     * Initializes the sprites and lays them out on the screen
     */
    private void initSprites(){
        retrySprite = new UISprite(new Texture(Gdx.files.internal("Pictures/redoNoBG.PNG")));
        nextSprite = new UISprite(new Texture(Gdx.files.internal("Pictures/nextNoBG.PNG")));
        feedbackSprite = new UISprite(new Texture(Gdx.files.internal("Pictures/HintNoBg.png")));
        float ratio = retrySprite.getHeight()/retrySprite.getWidth();
        retrySprite.setPosition(GameInfo.CAMERA_WIDTH/5, GameInfo.CAMERA_HEIGHT/10);
        nextSprite.setPosition(GameInfo.CAMERA_WIDTH*2/5 +(GameInfo.CAMERA_WIDTH/10), GameInfo.CAMERA_HEIGHT/10);
        retrySprite.setSize(25f, 25f * ratio);
        nextSprite.setSize(25f, 25f * ratio);
       // ratio = feedbackSprite.getHeight()/retrySprite.getWidth();

        feedbackSprite.setSize(25f,25f * ratio);
        feedbackSprite.setPosition(GameInfo.CAMERA_WIDTH*2/5 +(GameInfo.CAMERA_WIDTH/10), GameInfo.CAMERA_HEIGHT/10);

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
    private void calc(){
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
    }

    /**
     * Calculates the score  and generates a messsage to be displayed.
     */

    private void calcScoreRes1(){

        messages = new String[4];
        score += res1.x1 ? 10:0;
        score += res1.x2 ? 10:0;
        score += res1.x3 ? 10:0;
        messages[0] = Integer.toString(res1.x1?10:0);
        messages[1] = Integer.toString(res1.x2?10:0);
        messages[2] = Integer.toString(res1.x3?10:0);
        messages[3] = Integer.toString(score);
    }


    private void calcScoreRes0(){

        messages = new String[3];
        score += res0.x1? 10:0;
        messages[0] = Integer.toString(res0.x1? 10:0);
        score += res0.x2? 10:0;
        messages[1] = Integer.toString(res0.x2? 10:0);
        messages[2] = Integer.toString(score);
    }

    private void calcScoreSquare(){

        messages = new String[5];
        score += res2.x1?10:0;
        score += res2.x2?10:0;
        score += res2.x3?10:0;
        score += res2.x4?10:0;

        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);
        messages[2] = Integer.toString(res2.x3?10:0);
        messages[3] = Integer.toString(res2.x4?10:0);
        messages[4] = Integer.toString(score);
    }

    private void calcScoreRect(){

        messages = new String[4];
        score += res2.x1?10:0;
        score += res2.x2?10:0;
        score += res2.x3?10:0;


        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);
        messages[2] = Integer.toString(res2.x3?10:0);
        messages[3] = Integer.toString(score);
    }

    private void calcScoreRhombus(){

        messages = new String[4];
        score += res2.x1?10:0;
        score += res2.x2?10:0;
        score += res2.x4?10:0;

        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);
        messages[2] = Integer.toString(res2.x4?10:0);
        messages[3] = Integer.toString(score);
    }


    private void calcScoreParallelogram(){

        messages = new String[3];
        score += res2.x1?10:0;
        score += res2.x2?10:0;
        messages[0] = Integer.toString(res2.x1?10:0);
        messages[1] = Integer.toString(res2.x2?10:0);

        messages[3] = Integer.toString(score);
    }


    @Override
    public void show() {


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



        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        drawText(batch);
        if (!displayHintSprite) nextSprite.draw(batch);
        else feedbackSprite.draw(batch);
        retrySprite.draw(batch);
        batch.end();


        checkInput();
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
        fontLg.dispose();
        fontReg.dispose();
        shapeRenderer.dispose();
    }

    /**
     * Checks the users inputs and takes the appropriate actions
     */
    private void checkInput(){

        if(Gdx.input.isTouched()){
            float x = (Gdx.input.getX()* GameInfo.CAMERA_WIDTH)/Gdx.graphics.getWidth();//maps the touch input to camera coordinates
            float y = GameInfo.CAMERA_HEIGHT - abs((Gdx.input.getY() * GameInfo.CAMERA_HEIGHT) / Gdx.graphics.getHeight());//maps the touch input to camera coordinates

            if (retrySprite.getBoundingRectangle().contains(x,y) && displayHint == displayHintSprite){
                if (!isCorrect) prevScreen.numberOfAttempts --;//if the correct answer was not supplied reduce the number of tries remaining
                if (displayHint) prevScreen.numberOfAttempts =GameInfo.No_OF_ATTEMPTS;
                game.setScreen(prevScreen);
                this.dispose();
            }
            else if (isCorrect&&!displayHintSprite &&nextSprite.getBoundingRectangle().contains(x,y)){
                prevScreen.totalScore += score;//increase the player score and move to the next level
                prevScreen.currStages = (prevScreen.currStages + 1) % prevScreen.NUMBER_OF_LEVELS;
                prevScreen.numberOfAttempts = GameInfo.No_OF_ATTEMPTS;
                game.setScreen(prevScreen);
                this.dispose();
            }
            else if (displayHintSprite &&feedbackSprite.getBoundingRectangle().contains(x,y)){
                displayHint = true;
            }
        }
    }//checkInput

    /**
     * Lays out the text on the screen
     * @param batch the sprite batch used to draw the texture.
     */
    private void drawText(SpriteBatch batch){
        float initialX =GameInfo.CAMERA_WIDTH/8;
        float initialY = GameInfo.CAMERA_HEIGHT*7/8;
        fontLg.draw(batch,header + " Score",initialX,initialY);

        float deltaX = 6*GameInfo.CAMERA_WIDTH/8;

        int i;
        float deltaY = GameInfo.CAMERA_HEIGHT/10;
        for (i =0;i<messages.length;i++){
            if (i < modeSpecificFeedback.length)
                fontReg.draw(batch,modeSpecificFeedback[i],initialX,initialY-(deltaY*(i+1)));

            fontReg.draw(batch,messages[i],initialX+deltaX,initialY-(deltaY*(i+1)));
        }

        if (isCorrect){
            fontReg.draw(batch,"Awesometacular!",initialX,initialY - (deltaY * (i+1)));
        }
        else if (!displayHintSprite && !isCorrect){
            fontReg.draw(batch,"Sorry could you try again?",initialX,initialY - (deltaY * (i+1)));
        }
        else if(displayHint){
            drawFeedback(initialX,initialY -(deltaY*(i+1)),batch);
        }
        else {
            fontReg.draw(batch,"Please check for feedback",initialX,initialY - (deltaY * (i+1)));

        }

    }

    private void drawFeedback(float x, float y, SpriteBatch batch){
        String outputMessage = null;
        for(int i =0;i<modeSpecificFeedback.length;i++){
            if (messages[i].equals("0")){
                outputMessage = modeSpecificFeedback[i];
                break;
            }

        }

        fontReg.draw(batch,"Hint: "+outputMessage,x,y);
    }

    private void readModeSpecificMessages(){
        JsonReader reader = new JsonReader();

        JsonValue value = reader.parse(Gdx.files.internal("BasicFeedback.json"));

        JsonValue.JsonIterator iterator = value.iterator();
        JsonValue currentValue =null;

        while (iterator.hasNext()){
            currentValue = iterator.next();
            if (currentValue.getInt("category") == type.category && currentValue.getInt("shapeType") == type.shapeType &&
                    currentValue.getInt("angleType") == type.angleType && currentValue.getInt("specializedCategory") == type.specializedCategory){
                break;
            }

        }


        if (currentValue != null ){
            modeSpecificMessages = currentValue.get("FeedBack").asStringArray();
            modeSpecificFeedback = currentValue.get("Labels").asStringArray();
            header = currentValue.getString("Header");
        }
        else {
            modeSpecificFeedback=new String[]{""};
            modeSpecificMessages=new String[]{""};
            header="";
        }

    }
}
