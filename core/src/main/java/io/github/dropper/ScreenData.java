package io.github.dropper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class ScreenData {

    Dropper game;
    private Texture basket;
    private Sprite basketSprite;
    private Array<Sprite> appleSpriteArray;
    private int appleCount;

    ScreenData(Dropper game) {
        //
        basket = new Texture("basket.png");
        basketSprite = new Sprite(basket);
        basketSprite.setSize(1.5f, 1.5f);
        basketSprite.setX((game.viewport.getWorldWidth() / 2) - (basketSprite.getWidth() / 2));
        appleSpriteArray = new Array<Sprite>();
        appleCount = 0;
    }

    public void setBasket(Sprite basketSprite) {
        this.basketSprite.setPosition(basketSprite.getX(), basketSprite.getY());
        this.basketSprite.setSize(basketSprite.getWidth(), basketSprite.getHeight());
    }

    public void setApples(Array<Sprite> appleSpriteArray) {
        this.appleSpriteArray = new Array<>(appleSpriteArray);
    }

    public void setApplesCollected(int appleCount) {
        this.appleCount = appleCount;
    }

    public Sprite getBasket() {
        return this.basketSprite;
    }

    public Array<Sprite> getApples() {
        return this.appleSpriteArray;
    }

    public int getApplesCollected() {
        return this.appleCount;
    }
}
