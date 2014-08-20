package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenu implements Screen {
    // We use this so that we can share the SpriteBatch on game between screens
    private FlappyFinGame game;
    private OrthographicCamera camera;

    public MainMenu(FlappyFinGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.72f, 0.91f, 1); // A nice aqua blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
            game.font.setScale(1.5f);
            game.font.draw(game.batch, "Flappy Fin",
                    10,
                    Gdx.graphics.getHeight() / 2);
            game.font.setScale(1.25f);
            game.font.draw(game.batch, "Click to Start",
                    10,
                    Gdx.graphics.getHeight() / 3);
        game.batch.end();

        // Wait for player to click left click
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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

    @Override
    public void dispose() {}
}
