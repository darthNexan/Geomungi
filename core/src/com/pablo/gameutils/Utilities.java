package com.pablo.gameutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static java.lang.Math.ceil;

/**
 * Created by Dennis on 28/03/2018.
 */

public final class Utilities {

    /**
     * generates a bitmap font
     * @param size the size of the font
     * @param outlineColor the color that should be used for the border
     * @param fillColor the color that shpould fill the font
     * @param borderWidth the width of a border
     * @return the generated bitmap font
     */

    public static BitmapFont generateFont(int size, Color outlineColor, Color fillColor, float borderWidth){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Karma.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        Tuple2<Integer,Float> scale = scaleHelper(size);
        parameter.color = fillColor;
        parameter.size = scale.x1;
        parameter.borderColor = outlineColor;
        parameter.borderWidth = borderWidth;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.mono=true;
        parameter.mono = true;

        BitmapFont font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        font.getData().setScale(scale.x2);
        generator.dispose();
        return font;
    }

    private static Tuple2<Integer,Float> scaleHelper(final int size){
        Integer sizeRes = (int)ceil(size * (Gdx.graphics.getHeight() / GameInfo.CAMERA_HEIGHT));
        Float scaleRes =GameInfo.CAMERA_HEIGHT/Gdx.graphics.getHeight();
        return new Tuple2<Integer,Float>(sizeRes, scaleRes);
    }
}
