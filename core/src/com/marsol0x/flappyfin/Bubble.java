package com.marsol0x.flappyfin;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bubble implements Poolable {
    private Rectangle bubbleObj;
    private boolean active;
    private float activeTime;

    public Bubble() {
        bubbleObj = new Rectangle();
        active = false;
        activeTime = 0f;
    }

    public void init(float x, float y, float w, float h) {
        bubbleObj.set(x, y, w, h);
        active = true;
    }

    @Override
    public void reset() {
        bubbleObj.setPosition(0, 0);
        active = false;
        activeTime = 0f;
    }

    public boolean isActive() { return active; }
    public final float getX() { return bubbleObj.x; }
    public final float getY() { return bubbleObj.y; }
    public void setX(float x) { bubbleObj.x = x; }
    public void setY(float y) { bubbleObj.y = y; }
    public final float getActiveTime() { return activeTime; }
    public void update(float delta) { activeTime += delta; }
}
