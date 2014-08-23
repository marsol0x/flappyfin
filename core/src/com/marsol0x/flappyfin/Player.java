package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private final float SPEED = 120f;
    private Vector2 playerPos;
    private float hopDelay = 0f;

    private boolean dead = false;

    public Player (float x, float y) {
        playerPos = new Vector2(x, y);
    }

    public void update(float delta) {
        if (hopDelay > 0) {
            hopDelay -= delta;
        }

        if (Gdx.input.justTouched()) {
            hopDelay = 0.2f;
        }

        if (hopDelay > 0f) {
            playerPos.y += SPEED * delta;
            if (playerPos.y > Gdx.graphics.getHeight()) {
                playerPos.y = Gdx.graphics.getHeight();
            }
        } else {
            playerPos.y -= SPEED * delta / 2f;
        }

        if (playerPos.y < 0) {
            dead = true;
        }
    }

    public float getX() {
        return playerPos.x;
    }

    public float getY() {
        return playerPos.y;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        dead = true;
    }
}
