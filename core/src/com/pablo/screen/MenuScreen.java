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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.pablo.game.MyGdxGame;
import com.pablo.gameutils.GameInfo;
import com.pablo.gameutils.Tuple2;
import com.pablo.input.MenuScreenInput;

import java.util.ArrayList;

import static java.lang.Math.ceil;

/**
 * Created by Dennis on 28/03/2018.
 */

public class MenuScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    Camera camera;
    public MyGdxGame game;

    ShapeRenderer shapeRenderer;
    public Rectangle[] textBox;

    public ArrayList<String> levelNames;
    GestureDetector detector;



    public MenuScreen(MyGdxGame game){
        this.batch = game.getBatch();
        this.camera = game.getCamera();
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        detector =new GestureDetector(new MenuScreenInput(this));
        Gdx.input.setInputProcessor(detector);

        init();
    }

    private void generateFont(){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Karma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        Tuple2<Integer,Float> scale = scaleHelper(6);
        parameter.color = Color.WHITE;
        parameter.size = scale.x1;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2f;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.mono=true;
        parameter.mono = true;

        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        font.getData().setScale(scale.x2);

    }
    private Tuple2<Integer,Float> scaleHelper(final int size){
        Integer sizeRes = (int)ceil(size * (Gdx.graphics.getHeight() / GameInfo.CAMERA_HEIGHT));
        Float scaleRes =GameInfo.CAMERA_HEIGHT/Gdx.graphics.getHeight();
        return new Tuple2<Integer,Float>(sizeRes, scaleRes);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(detector);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);



        for (Rectangle aTextBox : textBox) {

            shapeRenderer.rect(aTextBox.x, aTextBox.y, aTextBox.width, aTextBox.height);


        }

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i<textBox.length;i++){
            Rectangle aTextBox = textBox[i];


            font.draw(batch,levelNames.get(i),aTextBox.x + aTextBox.width/4,aTextBox.y + aTextBox.height/2,
                    aTextBox.width, Align.left, true);


        }

        batch.end();
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


    public void readJson(){

        levelNames = new ArrayList<String>();
        JsonReader reader = new JsonReader();

        JsonValue value = reader.parse(Gdx.files.internal("BasicFeedback.json"));

        JsonValue.JsonIterator iterator = value.iterator();
        String currentValue =null;

        while (iterator.hasNext()){
            currentValue = iterator.next().getString("Header");
            levelNames.add(currentValue);
        }

    }

    public void createRectangles(){
        if (levelNames != null) {
            textBox = new Rectangle[levelNames.size()];
            int i = 0;

            float x = GameInfo.CAMERA_WIDTH/2;
            float y = GameInfo.CAMERA_HEIGHT - 10f;
            float width =80f ;
            float height =30f ;


            while ( i < textBox.length){
                textBox[i] = new Rectangle(x - width/2, y,width,height);

                y -= (height + 5f);
                i++;


            }

        }

    }

    public void init(){
        readJson();
        System.out.println("Json read");
        createRectangles();
        System.out.println("Rectangles created");
        generateFont();
        System.out.println("Font generated");
    }
}
