package com.jumpy.kid.sprites;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jumpy.kid.screens.PlayScreen;
import com.jumpy.kid.PlayerState;

import java.util.List;

/**
 * Created by act on 12-09-2016.
 */
public class Kid {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 44;
    private static final int GRAVITY = -22;

    public Vector2 position;
    public Vector2 velocity;
    public Rectangle bounds;
    public Camera cam;
    public Texture texture, jumping, falling, idle;
    public Platform lastPlatform, currentPlatform;

    public Kid(float x, float y, Camera cam){
        jumping = new Texture("kid-jump-up.png");
        falling = new Texture("kid-jump-fall.png");
        idle = new Texture("kid-idle-1.png");
        texture = idle;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
        this.cam = cam;
        currentPlatform = new Platform(0,0);
    }

    public void update(float dt){
        position.x = cam.position.x;
        velocity.add(0, GRAVITY);
        velocity.scl(dt);
        position.add(0, velocity.y);
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
        if(position.x > currentPlatform.position.x + Platform.WIDTH)
            currentPlatform = new Platform(0,0);
    }

    public void jump(){
        velocity.y = 350;
        texture = jumping;
    }

    public PlayerState handleCollision(List<Platform> platforms, Rectangle bridge){
        boolean heightOK,leftBoundOnPlatform, rightBoundOnPlatform;
        PlayerState playerstate = new PlayerState(false,false);
        if(cam.position.x <= cam.viewportWidth - PlayScreen.PLATFORM_HORIZONTAL_GAP) {
            if (bounds.overlaps(bridge)) {
                position.y = bridge.y + bridge.height;
                bounds.setHeight(position.y);
                playerstate.colliding = true;
                texture = idle;
            }
            else if(velocity.y < 0) texture = falling;
        }
        else{
             for(Platform p : platforms) {
                 heightOK = (bounds.y <= p.bounds.y + Platform.HEIGHT) && (bounds.y >= p.bounds.y);
                 leftBoundOnPlatform =  (bounds.x > p.bounds.x && bounds.x < p.bounds.x + Platform.WIDTH);
                 rightBoundOnPlatform = (bounds.x + WIDTH > p.bounds.x && bounds.x + WIDTH < p.bounds.x + Platform.WIDTH);

                 if(heightOK && (leftBoundOnPlatform || rightBoundOnPlatform)){
                     position.y = p.position.y + Platform.HEIGHT;
                     bounds.setHeight(position.y);
                     playerstate.colliding = true;
                     texture = idle;
                     if(lastPlatform != currentPlatform){
                         playerstate.justScored = true;
                         currentPlatform = p;
                         lastPlatform = currentPlatform;
                     }
                     break;
                 }
                 else if(velocity.y < 0) texture = falling;
             }
        }
        return playerstate;
    }

    //public boolean onPlatform(Platform platform){
    //    return (position.x >= platform.position.x && position.x <= platform.position.x + Platform.WIDTH
    //            && position.y == platform.position.y + Platform.HEIGHT);
    //}

    public void dispose(){
        texture.dispose();
        position = null;
        velocity = null;
        bounds = null;
    }
}
