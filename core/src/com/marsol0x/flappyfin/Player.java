package com.marsol0x.flappyfin;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Player extends InputAdapter {
    private final Vector2 GRAVITY = new Vector2(0, -350f);
    private final Vector2 HOP_SPEED = new Vector2(0, 150f);
    private Vector2 playerVelocity;
    private Vector2 playerPos;

    private boolean dead = false;

    public Player (float x, float y) {
        playerVelocity = new Vector2(0, 0);
        playerPos = new Vector2(x, y);
    }

    public void update(float delta) {
        playerPos.y += playerVelocity.y * delta;
        playerVelocity.y += GRAVITY.y * delta;

        if (playerPos.y < 0) {
            dead = true;
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

        playerVelocity.y = HOP_SPEED.y;

        return true;
    }
}
