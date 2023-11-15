package com.github.jingerjesus.gameenginethreedee.engine.interactables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Texture {

    Image image;

    public Texture(String texturePath) {
        try {
            image = new Image(new FileInputStream("src/main/java/com/github/jingerjesus/gameenginethreedee/game/models/textures/" + texturePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Color getPixel(int x, int y) {
        return image.getPixelReader().getColor(x,y);
    }
    public Color getNormalizedPixel(double x, double y) {
        System.out.println("X: " + x + ", Y: " + y);
        int u, v;
        u = (int)(x*image.getWidth());
        v = (int)(y*image.getHeight());

        System.out.println("U: " + u + ", V: " + v);

        if (u >= image.getWidth() || u < 0 || v >= image.getHeight() || v < 0) {
            return image.getPixelReader().getColor(u,v);
        }

        System.out.println("OOB");
        return Color.BLACK;

    }
}
