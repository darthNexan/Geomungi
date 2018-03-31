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


/** TODO add previous section variable to allow switching back to the previous screen
 * Allows a user to select a puzzle
 * Created by Dennis on 28/03/2018.
 */

public class LevelSelectionScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Camera camera;
    public MyGdxGame game;

    private ShapeRenderer shapeRenderer;
    public Rectangle[] textBox;

    private ArrayList<String> levelNames;
    private GestureDetector detector;


    /**
     * creates a menu screen
     * @param game
     */

    public LevelSelectionScreen(MyGdxGame game){
        this.batch = game.getBatch();
        this.camera = game.getCamera();
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();

        detector = new GestureDetector(new MenuScreenInput(this));

        //Gdx.input.setInputProcessor(detector);
        init();
    }


    /**
     * generates the fonts
     */
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

    /**
     *
     * @param size the size that the font should be
     * @return the first value is the sie that the font should be generated with
     *          the second value is the scale that should be applied to the font so that it can be displayed correctly
     */
    private Tuple2<Integer,Float> scaleHelper(final int size){
        Integer sizeRes = (int)ceil(size * (Gdx.graphics.getHeight() / GameInfo.CAMERA_HEIGHT));
        Float scaleRes =GameInfo.CAMERA_HEIGHT/Gdx.graphics.getHeight();
        return new Tuple2<Integer,Float>(sizeRes, scaleRes);
    }


    /**
     * If this screen is put in control set the input processor as detector
     */
    @Override
    public void show() {

        Gdx.input.setCatchBackKey(false);
        Gdx.input.setInputProcessor(detector);
    }

    /**
     * Render the scene
     * @param delta
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
        for (Rectangle aTextBox : textBox) {

            if (aTextBox.y > -1*(aTextBox.height) && aTextBox.y<GameInfo.CAMERA_HEIGHT) // if the box is not on the screen  don't display
                shapeRenderer.rect(aTextBox.x, aTextBox.y, aTextBox.width, aTextBox.height);

        }

        shapeRenderer.end();

        //renders the text
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i<textBox.length;i++){
            Rectangle aTextBox = textBox[i];

            if (aTextBox.y > -1*aTextBox.height && aTextBox.y<GameInfo.CAMERA_HEIGHT) //if the box is not on the screen don't display the text
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


    /**
     * Get menu titles
     */

    public void readJson(){

        levelNames = new ArrayList<String>();
        JsonReader reader = new JsonReader();

        JsonValue value = reader.parse(Gdx.files.internal("BasicFeedback.json"));


        for (JsonValue aValue : value) {
            levelNames.add(aValue.getString("Header"));
        }

    }

    public void createRectangles(){
        if (levelNames != null) {
            textBox = new Rectangle[levelNames.size()];
            int i = 0;

            float x = GameInfo.CAMERA_WIDTH/2;
            float y = GameInfo.CAMERA_HEIGHT - 30f;
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
