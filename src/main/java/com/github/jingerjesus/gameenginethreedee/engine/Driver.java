package com.github.jingerjesus.gameenginethreedee.engine;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Tri;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Vec;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.GameObject;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.GraphicsScreen;
import com.github.jingerjesus.gameenginethreedee.game.Controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;

public class Driver extends Application {
    static Stage mainStage;
    static Scene currentScene;
    static Group currentGroup;
    static Room currentRoom;
    public static GraphicsScreen screen;
    @Override
    public void start(Stage stage) throws IOException {
        Controller.start();


        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
        //closing out the game
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                gameLoop.stop();
                Platform.exit();
                System.exit(0);
            }
        });

    }

    KeyFrame animate = new KeyFrame(

            //Common FPS Values:
            //120 FPS: 0.0083 sec
            //60 FPS: 0.0167 sec
            //35 FPS: 0.029 sec
            //25 FPS: 0.04 sec
            //1 FPS: 1.0 sec
            Duration.seconds(0.029),
            actionEvent -> {

                screen.cls();
                currentRoom.onLoop();
                drawPresentObjects();

                mainStage.show();
            }
    );

    Timeline gameLoop = new Timeline(animate);


    public static void main(String[] args) {
        launch();
    }

    public static Group getMainGroup() {
        return currentGroup;
    }

    public static void changeRoomTo(Room r) {
        currentRoom = r;
        currentScene = r.getGroup().getScene();
        currentGroup = r.getGroup();
        mainStage = r.setAsActive();
        screen = new GraphicsScreen((int)mainStage.getWidth(), (int)mainStage.getHeight());
        r.onStart();
        mainStage.show();
    }

    private static void drawPresentObjects() {
        GameObject[] objects = currentRoom.getObjects();

        for (GameObject object : objects) {
            for (Tri tri : object.getMesh().getTris()) {


                //PROJECT
                Tri projected;
                Vec[] projVecs = new Vec[3];
                Vec[] preProjVecs = tri.getVecs();

                for (int i = 0; i < 3; i ++) {
                    projVecs[i] = preProjVecs[i].project(currentRoom);
                }
                projected = new Tri(projVecs);


                //DRAW
                screen.drawTri((int)projVecs[0].getX(), (int)projVecs[0].getY(),
                        (int)projVecs[1].getX(), (int)projVecs[1].getY(),
                        (int)projVecs[2].getX(), (int)projVecs[2].getY(),
                        Color.WHITE);

            }
        }

    }
}