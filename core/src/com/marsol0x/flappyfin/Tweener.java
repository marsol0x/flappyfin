package com.marsol0x.flappyfin;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tweener {
    private Rectangle obj;
    private Rectangle end;
    private Vector2 speed;
    private float seconds;

    private boolean isTweening;

    // The obj is what we will be manipulating as part of the tween,
    // it should also include the end location
    public Tweener(Rectangle start, Rectangle obj, float seconds) {
        this.obj = obj;
        end = new Rectangle(obj);
        this.seconds = seconds;

        // Reset object position
        obj.setPosition(start.x, start.y);

        // Set speed
        speed = new Vector2(1f * (end.x - start.x) / seconds, 1f * (end.y - start.y) / seconds);

        isTweening = true;
    }

    public void update(float delta) {
        if (!isTweening) return;

        seconds -= delta;
        if (seconds <= 0) {
            // we're done!
            isTweening = false;
            return;
        }

        obj.setX(obj.x + (speed.x * delta));
        obj.setY(obj.y + (speed.y * delta));
    }

    public boolean isTweening() {
        return isTweening;
    }
}
