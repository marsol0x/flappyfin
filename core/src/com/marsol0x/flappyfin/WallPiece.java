package com.marsol0x.flappyfin;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class WallPiece implements Poolable {
    private Rectangle wall;
    private boolean active;
    private TextureRegion wallTexture;

    public WallPiece() {
        wall = new Rectangle();
        active = false;
    }

    public void init(TextureRegion img, float x, float y) {
        wall.setPosition(x, y);
        wallTexture = img;
        wall.setSize(wallTexture.getRegionWidth(),
                wallTexture.getRegionHeight());
        active = true;
    }

    @Override
    public void reset() {
        wall.setPosition(0, 0);
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public float getX() {
        return wall.getX();
    }

    public float getY() {
        return wall.getY();
    }

    public final TextureRegion getTexture() {
        return wallTexture;
    }

    public void setTexture(TextureRegion img) {
        wallTexture = img;
    }
}
