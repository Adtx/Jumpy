package com.jumpy.kid;

/**
 * Created by adt on 02/02/2017.
 */

public class PlayerState {

    public boolean colliding;
    public boolean justScored;

    public PlayerState(boolean colliding, boolean justScored){
        this.colliding = colliding;
        this.justScored = justScored;
    }
}
