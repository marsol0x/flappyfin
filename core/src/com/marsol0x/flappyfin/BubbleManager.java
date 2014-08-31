package com.marsol0x.flappyfin;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class BubbleManager {
    private Texture bubbleImg;
    private final int BUBBLES_VISIBLE = 4;
    private final float BUBBLE_SPEED = 10f;
    private final float BUBBLE_TIME = 5f;

    private float bubbleLastAdded;
    private Array<Bubble> bubbles;

    private final Pool<Bubble> bubblePool = new Pool<Bubble>() {
        @Override
        protected Bubble newObject() {
            return new Bubble();
        }
    };

    public BubbleManager() {
        bubbleImg = new Texture(Gdx.files.internal("bubble.png"));
        bubbles = new Array<Bubble>();
        bubbleLastAdded = 0f;
    }

    public void update(float delta) {
        if (bubbleLastAdded >= BUBBLE_TIME) {
            addBubbleGroup();
            bubbleLastAdded = 0f;
        }
        bubbleLastAdded += delta;

        for (Bubble b : bubbles) {
            b.setY(b.getY() + BUBBLE_SPEED * delta);
            b.setX(b.getX() + MathUtils.cos(b.getActiveTime() / 2 * MathUtils.PI) / 8);
            b.update(delta);
        }

        // Remove bubbles that leave the screen
        Iterator<Bubble> iter = bubbles.iterator();
        Bubble b;
        while (iter.hasNext()) {
            b = iter.next();
            if (b.getY() > Gdx.graphics.getHeight()) {
                b.reset();
                bubbles.removeValue(b, true);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Bubble b : bubbles) {
            batch.draw(bubbleImg, b.getX(), b.getY());
        }
    }

    public void addBubbleGroup() {
        float x = MathUtils.random(10, Gdx.graphics.getWidth() - 10);
        float y = 0;
        for (int i = 0; i < BUBBLES_VISIBLE; i++) {
            Bubble b = bubblePool.obtain();
            b.init(x, y, bubbleImg.getWidth(), bubbleImg.getHeight());
            y -= bubbleImg.getHeight() + 3;
            b.update(MathUtils.random(0, 2 * MathUtils.PI));
            bubbles.add(b);
        }
    }

}
