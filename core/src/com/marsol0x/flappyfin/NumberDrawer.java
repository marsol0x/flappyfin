package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberDrawer {
    private static Texture[] numImgs;

    public static void init() {
        numImgs = new Texture[10];
        for (int i = 0; i < 9; i++) {
            numImgs[i] = new Texture(Gdx.files.internal("numbers/" + i + ".png"));
        }
    }

    public static void draw(SpriteBatch batch, int num, int x, int y) {
        Color oldColor = batch.getColor();

        String numStr = Integer.toString(num);
        for (int i = 0; i < numStr.length(); i++) {
            int n = Integer.parseInt(String.valueOf(numStr.charAt(i)));
            // Add a silly shadow
            batch.setColor(Color.GRAY);
            batch.draw(numImgs[n], x + 1, y + 1);
            batch.setColor(oldColor);
            batch.draw(numImgs[n], x, y);
            x += numImgs[n].getWidth();
        }
        batch.setColor(oldColor);
    }

    public static void dispose() {
        for (Texture t : numImgs) {
            t.dispose();
        }
    }
}
