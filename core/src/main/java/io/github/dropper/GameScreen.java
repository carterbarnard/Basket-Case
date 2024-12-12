package io.github.dropper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** Second screen of the application. Displayed after the user interacts on MenuScreen. */
public class GameScreen implements Screen {

    final Dropper game;
    Viewport uiViewport;
    Stage stage;
    Skin skin;
    Label scoreMessage;
    Sound ding;
    Texture forest;
    Texture basket;
    Texture apple;
    Sprite basketSprite;
    Sprite appleSprite;
    Array<Sprite> appleSpriteArray;
    Rectangle basketRectangle;
    Rectangle appleRectangle;
    float delayTime;
    int applesCollected;
    float delta;

    GameScreen(Dropper game) {
        this.game = game;

        //
        uiViewport = new ScreenViewport();
        stage = new Stage(uiViewport);
        skin = new Skin(Gdx.files.internal("earthboundui/terra-mother-ui.json"));
        scoreMessage = new Label("Score: " + applesCollected, skin);
        scoreMessage.setPosition(0, uiViewport.getWorldHeight() - scoreMessage.getHeight());
        stage.addActor(scoreMessage);

        delta = Gdx.graphics.getDeltaTime();

        ding = Gdx.audio.newSound(Gdx.files.internal("ding.mp3"));
        forest = new Texture("forest.png"); //creates forest background texture
        basket = new Texture("basket.png");
        apple = new Texture("apple.png");
        basketSprite = new Sprite(basket);
        basketSprite.setSize(1.5f, 1.5f);
        basketSprite.setX((game.viewport.getWorldWidth() / 2) - (basketSprite.getWidth() / 2));
        basketSprite.setY(0);

        appleSpriteArray = new Array<Sprite>();

        basketRectangle = new Rectangle();
        appleRectangle = new Rectangle();

        applesCollected = 0;

        resume();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    public void input() {
        float speed = 2f;

        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            basketSprite.translateX(-speed * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            basketSprite.translateX(speed * delta);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause();
        }
    }

    public void logic() {
        float worldWidth = game.viewport.getWorldWidth();
        float speed = 1f;

        if(basketSprite.getX() > worldWidth - basketSprite.getWidth()) {
            basketSprite.setX(worldWidth - basketSprite.getWidth());
        } else if(basketSprite.getX() < 0) {
            basketSprite.setX(0);
        }
        basketRectangle.set(basketSprite.getX(), basketSprite.getY(), basketSprite.getWidth(), basketSprite.getHeight());

        for(int i = appleSpriteArray.size - 1; i > 0; i--) {
            appleSprite = appleSpriteArray.get(i);
            appleSprite.translateY( -speed * delta);

            appleRectangle.set(appleSprite.getX(), appleSprite.getY(), appleSprite.getWidth(), appleSprite.getHeight());

            if(appleSpriteArray.get(i).getY() < -appleSpriteArray.get(i).getHeight()) {
                appleSpriteArray.removeIndex(i);
            } else if(basketRectangle.overlaps(appleRectangle)) {
                appleSpriteArray.removeIndex(i);
                ding.play(0.08f);
                applesCollected++;
            }
        }

        delayTime += delta;
        if(delayTime > 2f) {
            createApple();
            delayTime = 0;
        }
    }

    public void draw() {
        ScreenUtils.clear(Color.CLEAR);
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.viewport.apply();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.begin();

        game.batch.draw(forest, 0, 0, worldWidth, worldHeight);
        basketSprite.draw(game.batch);
        for(Sprite appleSprite : appleSpriteArray) {
            appleSprite.draw(game.batch);
        }

        game.batch.end();

        //
        uiViewport.apply();
        stage.act();
        scoreMessage.setText("Score: " + applesCollected);
        stage.draw();
    }

    public void createApple() {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        appleSprite = new Sprite(apple);
        appleSprite.setSize(0.75f, 0.75f);
        appleSprite.setX(MathUtils.random(0, worldWidth - appleSprite.getWidth()));
        appleSprite.setY(worldHeight);
        appleSpriteArray.add(appleSprite);
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        game.viewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {
        delta = 0;
        saveData();
        dispose();
        game.music.pause();
        game.setScreen(new PauseScreen(game));
    }

    @Override
    public void resume() {
        basketSprite.setPosition(game.data.getBasket().getX(), game.data.getBasket().getY());
        appleSpriteArray = new Array<>(game.data.getApples());
        applesCollected = game.data.getApplesCollected();
        game.music.play();
    }

    @Override
    public void hide() {

    }

    public void saveData() {
        game.data.setBasket(basketSprite);
        game.data.setApples(appleSpriteArray);
        game.data.setApplesCollected(applesCollected);
    }

    @Override
    public void dispose() {
        forest.dispose();
        basket.dispose();
    }
}
