package com.jumpy.kid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jumpy.kid.Jumpy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Jumpy.VIEWPORT_WIDTH;
		config.height = Jumpy.VIEWPORT_HEIGHT;
		config.title = Jumpy.TITLE;
		new LwjglApplication(new Jumpy(), config);
	}
}
