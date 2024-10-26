package io.github.dropper;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class StartScreen implements Screen {

    final Dropper game;
    Texture forest;
    Texture basket;
    Texture apple;
    Sprite basketSprite;
    Sprite appleSprite;
    Array<Sprite> appleSpriteArray;
    String text;
    GlyphLayout layout;
    float delayTime;

   StartScreen(Dropper game) {
       this.game = game;

       forest = new Texture("forest.png"); //creates forest background texture
       basket = new Texture("basket.png");
       apple = new Texture("apple.png");
       basketSprite = new Sprite(basket);
       basketSprite.setSize(1.5f, 1.5f);
       basketSprite.setX((game.viewport.getWorldWidth() / 2) - (basketSprite.getWidth() / 2));
       basketSprite.setY(0);

       appleSpriteArray = new Array<Sprite>();
       createApple();

       text = "Press 'Space' to begin collecting apples";
       game.font.getData().setScale(game.viewport.getWorldWidth() / Gdx.graphics.getWidth(), game.viewport.getWorldHeight() / Gdx.graphics.getHeight()); //increases scale of text on MenuScreen
       layout = new GlyphLayout();
       layout.setText(game.font, text);
   }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
       input();
       logic();
       draw();
    }

    public void input() {
       if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
           game.setScreen(new GameScreen(game));
           dispose();
       }
    }

    public void logic() {
       float delta = Gdx.graphics.getDeltaTime();
       float speed = 1f;

       for(int i = appleSpriteArray.size - 1; i > 0; i--) {
           appleSpriteArray.get(i).translateY( -speed * delta);

           if(appleSpriteArray.get(i).getY() < -appleSpriteArray.get(i).getHeight()) {
               appleSpriteArray.removeIndex(i);
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

       float worldWidth = game.viewport.getWorldWidth();
       float worldHeight = game.viewport.getWorldHeight();
       float textWidth = layout.width;

       game.batch.begin();

       game.batch.draw(forest, 0, 0, worldWidth, worldHeight);
       basketSprite.draw(game.batch);
       for(Sprite appleSprite : appleSpriteArray) {
           appleSprite.draw(game.batch);
       }
       game.font.draw(game.batch, text, (worldWidth / 2f) - (textWidth / 2), worldHeight /2f);

       game.batch.end();
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
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        forest.dispose();
        apple.dispose();
        basket.dispose();
    }
}
