// For pre-compiled version, please see:
// https://sepr-topright.github.io/SEPR/documentation/assessment4/game.zip

package io.github.teamfractal.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import io.github.teamfractal.RoboticonQuest;

import static com.badlogic.gdx.Gdx.files;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Added by Christian Beddows (Top Right Corner) to display the capture the chancellor image as the program icon
		config.addIcon("icon/128x128.png", Files.FileType.Internal);
		config.addIcon("icon/64x64.png", Files.FileType.Internal);
		config.addIcon("icon/32x32.png", Files.FileType.Internal);
		config.addIcon("icon/16x16.png", Files.FileType.Internal);
		
		
		config.backgroundFPS = 1;
		config.vSyncEnabled = true;
		config.title = "Roboticon Quest - by Top Right Corner";
		config.resizable = false;

		new LwjglApplication(new RoboticonQuest(), config);
	}
}
