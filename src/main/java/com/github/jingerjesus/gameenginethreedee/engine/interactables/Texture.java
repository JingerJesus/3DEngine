package com.github.jingerjesus.gameenginethreedee.engine.interactables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Texture {

    Image image;

    public Texture(String texturePath) {
        image = new Image("src/main/java/com/github/jingerjesus/gameenginethreedee/game/models/objects/" + texturePath);
    }
    public Color getPixel(int x, int y) {
        return image.getPixelReader().getColor(x,y);
    }
}
