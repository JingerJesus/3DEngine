package com.github.jingerjesus.gameenginethreedee.engine.interactables;

import com.github.jingerjesus.gameenginethreedee.engine.peripherals.GraphicsScreen;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Room {
    private int width, height;
    private Scene scene;
    private Stage stage;
    private String windowTitle;

    private ArrayList<GameObject> objects;
    private Group group;



    public Room() {
        width = 1024;
        height = 768;
        objects = new ArrayList<GameObject>();
        group = new Group();
        windowTitle = "Default Room";
        setup(width,height);
    }

    public Room(int w, int h, String t) {
        width = w; height = h; windowTitle = t;
        objects = new ArrayList<GameObject>();
        group = new Group();
        setup(width,height);
    }

    private void setup(int w, int h) {
        scene = new Scene(group);
        stage = new Stage();
        stage.setWidth(w);
        stage.setHeight(h);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
    }

    public Stage setAsActive() {
        stage.show();
        return stage;
    }
    public Group getGroup() {
        return group;
    }
}
