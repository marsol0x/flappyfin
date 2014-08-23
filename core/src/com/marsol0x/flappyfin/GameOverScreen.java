package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameOverScreen implements Screen {
    private FlappyFinGame game;
    private int score;

    private Texture playAgainImg;
    private Rectangle playAgainPos;

    public GameOverScreen(FlappyFinGame game, int score) {
        this.game = game;
        this.score = score;

        playAgainImg = new Texture(Gdx.files.internal("playagain.png"));
        playAgainPos = new Rectangle(playAgainImg.getWidth() / 2,
                Gdx.graphics.getHeight() / 3,
                playAgainImg.getWidth(),
                playAgainImg.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.72f, 0.91f, 1); // A nice aqua blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Draw score and ask if the player wants to replay
        game.batch.begin();
        game.font.setScale(2f);
        game.font.draw(game.batch,
                        String.valueOf(score),
                        Gdx.graphics.getWidth() / 2,
                        (Gdx.graphics.getHeight() / 3) * 2);
        game.batch.draw(playAgainImg,
                playAgainPos.x,
                playAgainPos.y,
                playAgainPos.width,
                playAgainPos.height);
        game.batch.end();

        // Wait for player input
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    @Override
    public void dispose() {
        playAgainImg.dispose();
    }

}
