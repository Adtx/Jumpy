package com.jumpy.kid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jumpy.kid.screens.MenuScreen;
import com.jumpy.kid.screens.PlayScreen;

public class Jumpy extends Game {
	public static final int VIEWPORT_WIDTH = 800;
	public static final int VIEWPORT_HEIGHT = 512;
	public static final String TITLE = "Jumpy";
	public static final int BACKGROUND_WIDTH = 1024;

	public SpriteBatch batch;
	public OrthographicCamera cam;
	public Viewport viewport;
	public Texture background;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new FitViewport(800, 512, cam);
		cam.viewportWidth = viewport.getWorldWidth();
		cam.viewportHeight = viewport.getWorldHeight();
		cam.translate(cam.viewportWidth/2, cam.viewportHeight/2);
		background = new Texture("city.png");
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
