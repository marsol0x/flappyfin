package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    private FlappyFinGame game;

    private final int FISH_COLS = 2;
    private Texture fishSheet;
    private TextureRegion[] fishFrames;
    private Animation fishAnimation;
    private float stateTime = 0.0f;
    
    private final WallBuilder wallBuilder = new WallBuilder();
    private Array<WallPiece> walls;

    public GameScreen(FlappyFinGame game) {
        this.game = game;

        fishSheet = new Texture(Gdx.files.internal("fish_sheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(fishSheet, fishSheet.getWidth() / FISH_COLS, fishSheet.getHeight());
        fishFrames = new TextureRegion[] { tmp[0][0], tmp[0][1] };
        fishAnimation = new Animation(0.27f, fishFrames);
        fishAnimation.setPlayMode(PlayMode.LOOP);
        
        walls = wallBuilder.buildWall(10, 100, 5, 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.72f, 0.91f, 1); // A nice aqua blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stateTime += delta;
        game.batch.begin();
            game.batch.draw(fishAnimation.getKeyFrame(stateTime), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            for (WallPiece wp : walls) {
            	game.batch.draw(wp.getTexture(), wp.getX(), wp.getY());
            }
        game.batch.end();
    }

    @Override
    public void dispose() {
        fishSheet.dispose();
        wallBuilder.dispose();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
