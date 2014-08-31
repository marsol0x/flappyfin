package com.marsol0x.flappyfin;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    private FlappyFinGame game;

    private final int FISH_SIZE = 16;
    private Texture fishSheet;
    private TextureRegion[] fishFrames;
    private Animation fishAnimation;
    private float stateTime = 0.0f;

    private Player player;
    private Rectangle playerObj;

    private final WallBuilder wallBuilder = new WallBuilder();
    private final int WALL_STARTX = Gdx.graphics.getWidth();
    private final int MIN_WALL_HEIGHT = Gdx.graphics.getHeight() / 64; // 4 rock tiles
    private final int MAX_WALL_HEIGHT = Gdx.graphics.getHeight() / 16; // 1 rock tile
    private final float WALL_TIME = 3f;
    private float timeSinceWall = WALL_TIME;

    private int score;

    private Array<Rectangle> scoreRects;

    public GameScreen(FlappyFinGame game) {
        this.game = game;

        fishSheet = new Texture(Gdx.files.internal("fish_sheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(fishSheet, FISH_SIZE, FISH_SIZE);
        fishFrames = new TextureRegion[] { tmp[0][0], tmp[0][1] };
        fishAnimation = new Animation(0.27f, fishFrames);
        fishAnimation.setPlayMode(PlayMode.LOOP);

        // The player
        player = new Player(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);
        playerObj = new Rectangle(player.getX(), player.getY(), FISH_SIZE, FISH_SIZE);
        // Player input
        Gdx.input.setInputProcessor(player);

        scoreRects = new Array<Rectangle>();
        score = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.72f, 0.91f, 1); // A nice aqua blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Draw
        stateTime += delta;
        game.batch.begin();
        for (WallPiece wp : wallBuilder.getPieces()) {
            game.batch.draw(wp.getTexture(), wp.getX(), wp.getY());
        }
        game.batch.draw(fishAnimation.getKeyFrame(stateTime), player.getX(), player.getY());
        NumberDrawer.draw(game.batch, score, 5, Gdx.graphics.getHeight() - 40);
        game.batch.end();

        if (!player.isDead()) {
            // Update game world
            wallBuilder.update(delta);
            updateScoreColliders(delta);

            // Update player
            player.update(delta);
            playerObj.x = player.getX();
            playerObj.y = player.getY();

            // Check score collisions
            checkScoreCollisions();

            // Check wall collisions
            if (wallBuilder.checkCollision(playerObj)) {
                player.kill();
            }

            // Place new obstacles/walls
            timeSinceWall += delta;
            if (timeSinceWall >= WALL_TIME) {
                timeSinceWall = 0f;
                placeObstacle();
            }
        } else {
            // Run the "death" animation
            player.deathUpdate(delta);
        }

        // Check player death
        if (player.isDead() && !player.inDeathUpdate()) {
            game.setScreen(new GameOverScreen(game, score));
            dispose();
        }
    }

    private void placeObstacle() {
        // Build two walls with a space between them
        // Get bottom wall height
        int height = MathUtils.random(MIN_WALL_HEIGHT, MAX_WALL_HEIGHT - (MIN_WALL_HEIGHT * 2));

        // Place one-tile lower to hide the bottom
        wallBuilder.buildWall(WALL_STARTX, -16, 3, height + 1);

        // Place second wall above the first
        wallBuilder.buildWall(WALL_STARTX,
                             (height + MIN_WALL_HEIGHT) * 16,
                             3,
                             MAX_WALL_HEIGHT - height);
        addScoreCollider();
    }

    private void addScoreCollider() {
        scoreRects.add(new Rectangle(WALL_STARTX, 8, 32, Gdx.graphics.getHeight()));
    }

    private void updateScoreColliders(float delta) {
        for (Rectangle r : scoreRects) {
            r.x -= 45f * delta;
        }
    }

    private void checkScoreCollisions() {
        Iterator<Rectangle> iter = scoreRects.iterator();
        Rectangle r;
        while (iter.hasNext()) {
            r = iter.next();
            if (r.overlaps(playerObj)) {
                score += 1;
                scoreRects.removeValue(r, true);
            }
        }
    }

    @Override
    public void dispose() {
        fishSheet.dispose();
        wallBuilder.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
