package com.jumpy.kid.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jumpy.kid.screens.PlayScreen;

import java.util.Random;

/**
 * Created by act on 09-09-2016.
 */
public class Platform {

    public static final float WIDTH = 128;
    public static final float HEIGHT = 33;
    public static final Texture texture = new Texture("platform.png");

    public Vector2 position;
    public Rectangle bounds;

    public Platform(float x, float y){
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public static float getPlatformHeight(float leftNeighborHeight){
        float height;
        int[] coefficients = {-1, 0, 1};
        Random rand = new Random();
        int coeff = coefficients[rand.nextInt(3)];
        if(coeff == -1) {
            height = leftNeighborHeight - PlayScreen.PLATFORM_VERTICAL_GAP - Platform.HEIGHT;
            if(height < 0)
                height = leftNeighborHeight + Platform.HEIGHT + PlayScreen.PLATFORM_VERTICAL_GAP;
        }
        else if(coeff == 1) {
            height = leftNeighborHeight + Platform.HEIGHT + PlayScreen.PLATFORM_VERTICAL_GAP;
            if(height + Platform.HEIGHT > PlayScreen.PLATFORM_MAX_HEIGHT)
                height = leftNeighborHeight - PlayScreen.PLATFORM_VERTICAL_GAP - Platform.HEIGHT;
        }
        else height = leftNeighborHeight;
        return height;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
        bounds.setPosition(x, y);
    }

    public void dispose(){
        position = null;
        bounds = null;
    }
}
