package com.marsol0x.flappyfin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.marsol0x.flappyfin.FlappyFinGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Flappy Fin";
        config.width = 250;
        config.height = 300;
        new LwjglApplication(new FlappyFinGame(), config);
    }
}
