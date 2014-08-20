package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

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

    public GameScreen(FlappyFinGame game) {
        this.game = game;

        fishSheet = new Texture(Gdx.files.internal("fish_sheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(fishSheet, FISH_SIZE, FISH_SIZE);
        fishFrames = new TextureRegion[] { tmp[0][0], tmp[0][1] };
        fishAnimation = new Animation(0.27f, fishFrames);
        fishAnimation.setPlayMode(PlayMode.LOOP);

        player = new Player(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);
        playerObj = new Rectangle(player.getX(), player.getY(), FISH_SIZE, FISH_SIZE);

        wallBuilder.buildWall(200, 100, 3, 10);
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
        game.batch.end();

        // Update game world
        wallBuilder.update(delta);
        player.update(delta);
        playerObj.x = player.getX();
        playerObj.y = player.getY();
        if (wallBuilder.checkCollision(playerObj)) {
            player.kill();
        }
        if (player.isDead()) {
            game.setScreen(new MainMenu(game));
            dispose();
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
