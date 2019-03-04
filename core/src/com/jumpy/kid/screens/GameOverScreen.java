package com.jumpy.kid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jumpy.kid.Jumpy;

import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;

/**
 * Created by act on 09-09-2016.
 */
public class GameOverScreen implements Screen{

    private Jumpy game;
    private int bestScore, lastScore;
    private String bestScoreText, lastScoreText;
    private BitmapFont font;

    public GameOverScreen(Jumpy game, int score){
        this.game = game;
        lastScore = score;
        lastScoreText = "LAST: " + String.valueOf(lastScore);
        font = new BitmapFont();
        FileHandle file = Gdx.files.local("score.txt");
        try {
            bestScore = ByteBuffer.wrap(file.readBytes()).getInt();
        } catch (GdxRuntimeException e){}
        if(lastScore > bestScore || file.exists() == false){
            bestScore = lastScore;
            file.writeBytes(ByteBuffer.allocate(4).putInt(bestScore).array(),false);
        }
        bestScoreText = "BEST: " + String.valueOf(bestScore);
    }

    @Override
    public void show() {
    }

    private void handleInput(){
        if(Gdx.input.justTouched())
            game.setScreen(new PlayScreen(game));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();
        game.batch.draw(game.background, game.cam.position.x - (game.cam.viewportWidth/2),
                        game.cam.position.y - (game.cam.viewportHeight/2));
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(game.batch, "GAME OVER", game.cam.position.x - 40, Jumpy.VIEWPORT_HEIGHT/2 + 150);
        font.draw(game.batch, bestScoreText, game.cam.position.x - 20, Jumpy.VIEWPORT_HEIGHT/2 + 20);
        font.draw(game.batch, lastScoreText, game.cam.position.x - 20, Jumpy.VIEWPORT_HEIGHT/2);
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
