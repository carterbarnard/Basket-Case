package io.github.dropper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseScreen implements Screen {

    Dropper game;
    Viewport viewport;
    private Skin skin;
    private Stage stage;
    private Table root;
    private TextButton unPauseButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private Sound click;
    int buttonFlag;

    PauseScreen(Dropper game) {
        //
        this.game = game;
        viewport = new ScreenViewport();

        //
        skin = new Skin(Gdx.files.internal("earthboundui/terra-mother-ui.json"));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        unPauseButton = new TextButton("Resume", skin);
        settingsButton = new TextButton("Settings", skin);
        exitButton = new TextButton("Exit Game", skin);

        //
        unPauseButton.getStyle().overFontColor = Color.YELLOW;

        //
        root.add(unPauseButton).pad(10f);
        root.row();
        root.add(settingsButton).pad(10f);
        root.row();
        root.add(exitButton).pad(10f);
        //stage.setDebugAll(true);

        //
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        buttonFlag = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    public void input() {
        //
        if(unPauseButton.isOver() || settingsButton.isOver() || exitButton.isOver()) {
            if(buttonFlag < 1) {
                click.play(0.01f);
                buttonFlag++;
            }
        } else {
            buttonFlag = 0;
        }

        //
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.P) || unPauseButton.isPressed()) {
            game.setScreen(new GameScreen(game));
        }

        //
        if(settingsButton.isPressed()) {
            //game.setScreen(new SettingsScreen(game));
        }

        //
        if(exitButton.isPressed()) {
            System.exit(0);
        }
    }

    public void logic() {

    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
