package com.github.jingerjesus.gameenginethreedee.engine;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.GraphicsScreen;
import com.github.jingerjesus.gameenginethreedee.game.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {
    static Stage mainStage;
    static Scene currentScene;
    static Group currentGroup;
    public static GraphicsScreen screen;
    @Override
    public void start(Stage stage) throws IOException {
        Controller.start();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Group getMainGroup() {
        return currentGroup;
    }

    public static void changeRoomTo(Room r) {
        currentScene = r.getGroup().getScene();
        currentGroup = r.getGroup();
        mainStage = r.setAsActive();
        screen = new GraphicsScreen((int)mainStage.getWidth(), (int)mainStage.getHeight());
        mainStage.show();
    }
}