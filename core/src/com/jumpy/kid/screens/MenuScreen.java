package com.jumpy.kid.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.jumpy.kid.Jumpy;

/**
 * Created by act on 09-09-2016.
 */
public class MenuScreen implements Screen{

    private Jumpy game;
    private float backgroundOffset, backgroundStartPoint1, backgroundStartPoint2;

    public MenuScreen(Jumpy game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    private void handleInput(){
        if(Gdx.input.justTouched())
            game.setScreen(new PlayScreen(game));
    }

    private void update(){
        float holder;
        //Atualizar as posicoes dos backgrounds para efeito de parallax
        backgroundOffset += 0.5f;
        backgroundStartPoint1 = game.cam.position.x - game.cam.viewportWidth/2;
        backgroundStartPoint2 = backgroundStartPoint1 + Jumpy.BACKGROUND_WIDTH;
        if(backgroundOffset > Jumpy.BACKGROUND_WIDTH){
            backgroundOffset = 0;
            holder = backgroundStartPoint1;
            backgroundStartPoint1 = backgroundStartPoint2;
            backgroundStartPoint2 = holder;
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();
        game.batch.draw(game.background, backgroundStartPoint1 - backgroundOffset, 0);
        game.batch.draw(game.background, backgroundStartPoint2 - backgroundOffset, 0);
        game.batch.end();
        handleInput();
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
}
