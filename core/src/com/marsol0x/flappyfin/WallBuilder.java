package com.marsol0x.flappyfin;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class WallBuilder {
    private Texture rockSheet;
    private TextureRegion[][] rockTiles;
    private final int TILE_SIZE = 16; // Size rockSheet height/width divided by 3

    private Array<WallPiece> pieces;
    private final float SPEED = 45f;

    private final Pool<WallPiece> wallPool = new Pool<WallPiece>() {
        @Override
        protected WallPiece newObject() {
            return new WallPiece();
        }
    };

    public WallBuilder() {
        rockSheet = new Texture(Gdx.files.internal("rock_sheet.png"));
        rockTiles = TextureRegion.split(rockSheet, TILE_SIZE, TILE_SIZE);
        pieces = new Array<WallPiece>();
    }

    public void buildWall(float x, float y, int w, int h) {
        // A wall must be at least 2 wide and 2 tall, otherwise it will look
        // funny
        if (w < 2) return;
        if (h < 2) return;

        Array<WallPiece> wall = new Array<WallPiece>();

        // Filler everywhere
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                wall.add(getTile(rockTiles[1][1],
                        x + (i * TILE_SIZE),
                        y + (j * TILE_SIZE)
                        ));
            }
        }

        // Set special tiles
        float rightX = x + (w - 1) * TILE_SIZE;
        float topY = y + (h - 1) * TILE_SIZE;
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

        pieces.addAll(wall);
    }

    private WallPiece getTile(TextureRegion img, float x, float y) {
        WallPiece wp = wallPool.obtain();
        wp.init(img, x, y);

        return wp;
    }

    public void update(float delta) {
        if (pieces.size == 0) return;
        Iterator<WallPiece> iter = pieces.iterator();
        WallPiece wp;
        while (iter.hasNext()) {
            wp = iter.next();
            wp.setX(wp.getX() - (delta * SPEED));

            if (wp.getX() < -TILE_SIZE) {
                pieces.removeValue(wp, true);
                wallPool.free(wp);
            }
        }
    }

    public boolean checkCollision(Rectangle obj) {
        for (WallPiece wp : pieces) {
            if (wp.getRect().overlaps(obj)) {
                return true;
            }
        }
        return false;
    }

    public final Array<WallPiece> getPieces() {
        return pieces;
    }

    public void dispose() {
        rockSheet.dispose();
    }
}
