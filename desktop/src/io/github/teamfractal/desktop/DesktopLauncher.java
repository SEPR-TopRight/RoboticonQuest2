package io.github.teamfractal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.exception.NotCommonResourceException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		/*
		config.width = 1280;
        config.height = 720;
        config.resizable = false;
        */
		config.backgroundFPS = 1;
		config.vSyncEnabled = true;

		new LwjglApplication(new RoboticonQuest(), config);
	}
}
