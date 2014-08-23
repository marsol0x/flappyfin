package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Player extends InputAdapter {
    private final Vector2 GRAVITY = new Vector2(0, 9.8f);
    private Vector2 playerVelocity;
    private Vector2 playerPos;
    private float hopDelay = 0f;

    private boolean isHopping = false;

    private boolean dead = false;

    public Player (float x, float y) {
        playerVelocity = new Vector2(0, 0);
        playerPos = new Vector2(x, y);
    }

    public void update(float delta) {
        if (isHopping || hopDelay > 0f) {
            playerPos.y += playerVelocity.y * delta;
            if (playerPos.y > Gdx.graphics.getHeight()) {
                playerPos.y = Gdx.graphics.getHeight();
            }
            hopDelay -= delta;
        } else {
            playerVelocity.add(0, GRAVITY.y);
            playerPos.y -= playerVelocity.y * delta;
        }

        if (isHopping && hopDelay <= 0) {
            isHopping = false;
            playerVelocity.y = 0f;
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

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (button != Input.Buttons.LEFT) return false;

        isHopping = true;
        playerVelocity.sub(0, GRAVITY.y);
        hopDelay = 0.2f;

        return true;
    }
}
