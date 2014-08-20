package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class WallBuilder {
    private int ROCK_ROWS = 3;
    private int ROCK_COLS = 3;

    private Texture rockSheet;
    private TextureRegion[][] rockTiles;

    private final Pool<WallPiece> wallPool = new Pool<WallPiece>() {
        @Override
        protected WallPiece newObject() {
            return new WallPiece();
        }
    };

    public WallBuilder() {
        rockSheet = new Texture(Gdx.files.internal("rock_sheet.png"));
        rockTiles = TextureRegion.split(rockSheet, rockSheet.getWidth()
                / ROCK_ROWS, rockSheet.getHeight() / ROCK_COLS);
    }

    public Array<WallPiece> buildWall(float x, float y, int w, int h) {
        // A wall must be at least 2 wide and 2 tall, otherwise it will look
        // funny
        if (w < 2)
            return null;
        if (h < 2)
            return null;

        Array<WallPiece> wall = new Array<WallPiece>();

        // Filler everywhere
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                wall.add(getTile(rockTiles[1][1],
                        x + (i * rockTiles[1][1].getRegionWidth()),
                        y + (j * rockTiles[1][1].getRegionHeight())
                        ));
            }
        }

        // Set special tiles
        float rightX = x + (w - 1) * rockTiles[1][1].getRegionWidth();
        float topY = y + (h - 1) * rockTiles[1][1].getRegionHeight();
        for (WallPiece wp : wall) {
            if (wp.getX() == x) { wp.setTexture(rockTiles[1][0]); }
            if (wp.getX() == rightX) { wp.setTexture(rockTiles[1][2]); }
            if (wp.getY() == y) { wp.setTexture(rockTiles[2][1]); }
            if (wp.getY() == topY) { wp.setTexture(rockTiles[0][1]); }

            if (wp.getX() == x && wp.getY() == y) { wp.setTexture(rockTiles[2][0]); }
            if (wp.getX() == x && wp.getY() == topY) { wp.setTexture(rockTiles[0][0]); }
            if (wp.getX() == rightX && wp.getY() == y) { wp.setTexture(rockTiles[2][2]); }
            if (wp.getX() == rightX && wp.getY() == topY) { wp.setTexture(rockTiles[0][2]); }
        }


        return wall;
    }

    private WallPiece getTile(TextureRegion img, float x, float y) {
        WallPiece wp = wallPool.obtain();
        wp.init(img, x, y);

        return wp;
    }

    public void dispose() {
        rockSheet.dispose();
    }
}
