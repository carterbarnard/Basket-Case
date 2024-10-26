package io.github.dropper;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Dropper extends Game {

    OrthographicCamera camera;
    Viewport viewport;
    ScreenData data;
    SpriteBatch batch; //declares SpriteBatch for draw calls
    BitmapFont font; //declares BitmapFont for text display
    Music music;

    public void create() {
        //
        Gdx.graphics.setResizable(false);
        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Graphics.DisplayMode display = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(1920 / 2, 1080 / 2);

        //
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        //
        viewport = new StretchViewport(16, 9, camera);
        viewport.apply();

        //
        batch = new SpriteBatch(); //initializes SpriteBatch instance for draw calls
        font = new BitmapFont(); //initializes BitmapFont instance for text display
        font.setUseIntegerPositions(false);

        //
        music = Gdx.audio.newMusic(Gdx.files.internal("scaryMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.03f);
        music.play();

        //
        data = new ScreenData(this);

        //
        this.setScreen(new StartScreen(this)); //sets visible screen to MenuScreen
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        //dispose of batch & font instances
        batch.dispose();
        font.dispose();
    }
}
