package com.jumpy.kid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import com.jumpy.kid.Jumpy;
import com.jumpy.kid.PlayerState;
import com.jumpy.kid.sprites.Kid;
import com.jumpy.kid.sprites.Platform;

/**
 * Created by act on 09-09-2016.
 */
public class PlayScreen implements Screen{
    public static final int PLATFORM_MAX_HEIGHT = Jumpy.VIEWPORT_HEIGHT/2;
    public static final int PLATFORM_HORIZONTAL_GAP = 55;
    public static final int PLATFORM_VERTICAL_GAP = 15;
    private static final int PLATFORM_COUNT = 5;

    private Jumpy game;
    private int score;
    private String scoreText;
    private BitmapFont font;
    private float backgroundOffset, backgroundStartPoint1, backgroundStartPoint2;
    private ArrayList<Platform> platforms, bridge;
    private Rectangle bridgeBounds;
    private Kid kid;
    private Sound sound;

    public PlayScreen(Jumpy game) {
        this.game = game;
        game.cam.position.x = Jumpy.VIEWPORT_WIDTH/2;
        score = 0;
        scoreText = String.valueOf(score);
        font = new BitmapFont();
        backgroundOffset = 0;
        platforms = new ArrayList<Platform>();
        bridge = new ArrayList<Platform>();
        bridgeBounds = new Rectangle(0, game.cam.viewportHeight/2,
                game.cam.viewportWidth - PLATFORM_HORIZONTAL_GAP, Platform.HEIGHT);
        kid = new Kid(game.cam.position.x, bridgeBounds.y + bridgeBounds.height, game.cam);

        platforms.add(new Platform(game.cam.viewportWidth, game.cam.viewportHeight/2));
        for (int i = 1; i < PLATFORM_COUNT; i++) {
            Platform p = platforms.get(i-1);
            platforms.add(
                    new Platform(p.position.x + Platform.WIDTH + PLATFORM_HORIZONTAL_GAP,
                            Platform.getPlatformHeight(p.position.y))
            );
        }
        float bridgePlatformXPos = 0;
        while(bridgePlatformXPos + Platform.WIDTH <= game.cam.viewportWidth - PLATFORM_HORIZONTAL_GAP) {
            bridge.add(new Platform(bridgePlatformXPos, game.cam.viewportHeight/2));
            bridgePlatformXPos += Platform.WIDTH;
        }
        float overlap = (bridgePlatformXPos + Platform.WIDTH) - (game.cam.viewportWidth - PLATFORM_HORIZONTAL_GAP);
        bridgePlatformXPos = game.cam.viewportWidth - PLATFORM_HORIZONTAL_GAP - overlap - Platform.WIDTH;
        bridge.add(new Platform(bridgePlatformXPos, game.cam.viewportHeight/2));

        sound = Gdx.audio.newSound(Gdx.files.internal("sfx_point.wav"));
    }

    @Override
    public void show() {

    }

    private void update(){
        float x, y, holder;
        game.cam.position.x += 5;

        kid.update(Gdx.graphics.getDeltaTime());

        PlayerState player = kid.handleCollision(platforms, bridgeBounds);

        if(player.colliding && Gdx.input.justTouched())
            kid.jump();

        // Reposicionar a plataforma que ja passou pela camara
        for(int i = 0; i < PLATFORM_COUNT; i++){
            Platform p = platforms.get(i);
            if (p.position.x + Platform.WIDTH < game.cam.position.x - (game.cam.viewportWidth/2)){
                int index = (i-1+PLATFORM_COUNT) % PLATFORM_COUNT;
                x = p.position.x + (Platform.WIDTH + PLATFORM_HORIZONTAL_GAP) * PLATFORM_COUNT;
                y = Platform.getPlatformHeight(platforms.get(index).position.y);
                p.setPosition(x, y);
            }
        }
        // Atualizar as posicoes dos backgrounds para efeito de parallax
        backgroundOffset += 0.5f;
        backgroundStartPoint1 = game.cam.position.x - game.cam.viewportWidth/2;
        backgroundStartPoint2 = backgroundStartPoint1 + Jumpy.BACKGROUND_WIDTH;
        if(backgroundOffset > Jumpy.BACKGROUND_WIDTH){
            backgroundOffset = 0;
            holder = backgroundStartPoint1;
            backgroundStartPoint1 = backgroundStartPoint2;
            backgroundStartPoint2 = holder;
        }

        if(player.justScored){
            score++;
            scoreText = String.valueOf(score);
            sound.play(0.2f);
        }
    }

    @Override
    public void render(float delta) {
        update();
        if(kid.position.y + Kid.HEIGHT > 0) {
            game.cam.update();
            //Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.setProjectionMatrix(game.cam.combined);
            game.batch.begin();
            game.batch.draw(game.background, backgroundStartPoint1 - backgroundOffset, 0);
            game.batch.draw(game.background, backgroundStartPoint2 - backgroundOffset, 0);

            game.batch.draw(kid.texture, kid.position.x, kid.position.y);
            if (game.cam.position.x - (game.cam.viewportWidth / 2) < game.cam.viewportWidth)
                for (Platform p : bridge)
                    game.batch.draw(Platform.texture, p.position.x, p.position.y);

            // Desenha plataformas
            for (Platform p : platforms)
                game.batch.draw(Platform.texture, p.position.x, p.position.y);
            // Mostrar o score
            font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            font.draw(game.batch, scoreText, game.cam.position.x, Jumpy.VIEWPORT_HEIGHT - 15);
            game.batch.end();
        }
        else{
            dispose();
            game.setScreen(new GameOverScreen(game, score));
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
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
        for(Platform p : platforms)
            p.dispose();
        for(Platform p : bridge)
            p.dispose();
        kid.dispose();
        sound.dispose();
        font.dispose();
        bridgeBounds = null;
    }
}