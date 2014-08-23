package com.marsol0x.flappyfin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {
    // We use this so that we can share the SpriteBatch on game between screens
    private FlappyFinGame game;
    private OrthographicCamera camera;

    private Texture titleImg;
    private Rectangle titlePos;

    private Texture subTitleImg;
    private Rectangle subTitlePos;

    private Tweener titleTween;
    private Tweener subTitleTween;

    public MainMenuScreen(FlappyFinGame game) {
        this.game = game;

        titleImg = new Texture(Gdx.files.internal("title.png"));
        titlePos = new Rectangle(45,
                Gdx.graphics.getHeight() - 50 - titleImg.getHeight(),
                titleImg.getWidth(),
                titleImg.getHeight());
        subTitleImg = new Texture(Gdx.files.internal("subtitle.png"));
        subTitlePos = new Rectangle((Gdx.graphics.getWidth() / 2) - (subTitleImg.getWidth() / 2),
                (Gdx.graphics.getHeight() / 2) - subTitleImg.getHeight(),
                subTitleImg.getWidth(),
                subTitleImg.getHeight());

        titleTween = new Tweener(new Rectangle(titlePos.x, Gdx.graphics.getHeight(), 0, 0), titlePos, 1.5f);
        subTitleTween = new Tweener(new Rectangle(subTitlePos.x, -subTitlePos.height, 0, 0), subTitlePos, 1.5f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.72f, 0.91f, 1); // A nice aqua blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        titleTween.update(delta);
        subTitleTween.update(delta);

        game.batch.begin();
            game.font.setScale(1.25f);
            game.batch.draw(titleImg, titlePos.x, titlePos.y, titlePos.width, titlePos.height);
            game.batch.draw(subTitleImg, subTitlePos.x, subTitlePos.y, subTitlePos.width, subTitlePos.height);
        game.batch.end();

        // Wait for player to click left click, but only after the tween is done
        if (titleTween.isTweening() || subTitleTween.isTweening()) return;

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
    public void dispose() {
        titleImg.dispose();
        subTitleImg.dispose();
    }
}
