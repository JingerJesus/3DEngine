package com.github.jingerjesus.gameenginethreedee.engine;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Matrix;
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

    static int θ;
    static int θ0;
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

        θ++; θ0 +=2;

        for (GameObject object : objects) {
            for (Tri tri : object.getMesh().getTris()) {

                //OPTIONAL - ROTATE Z
                Vec[] preZRot = tri.getVecs();
                Vec[] zRot = new Vec[3];

                for (int i = 0; i < 3; i ++) {
                    zRot[i] = Matrix.projectVector(Matrix.getZRotationalMatrix(θ), preZRot[i]);
                }

                Vec[] xRot = new Vec[3];

                for (int i = 0; i < 3; i ++) {
                    xRot[i] = Matrix.projectVector(Matrix.getXRotationalMatrix(θ0), zRot[i]);
                }



                //OFFSET
                //Vec[] preTranslate = tri.getVecs();
                Vec[] preTranslate = xRot;
                Vec[] translated = new Vec[3];

                for (int i = 0; i < 3; i ++) {
                    translated[i] = new Vec(preTranslate[i].getX(), preTranslate[i].getY(), preTranslate[i].getZ() + 3);
                }


                //PROJECT
                Vec[] projVecs = new Vec[3];

                //System.out.println(preProjVecs[0].getX() + ": PREPROJ");

                for (int i = 0; i < 3; i ++) {
                    projVecs[i] = Matrix.projectVector(Matrix.getProjectionMatrix(currentRoom), translated[i]);
                }

                //System.out.println(projVecs[0].getX() + ": POSTPROJ");

                //SCALE
                Vec[] semiScaled = new Vec[3];
                Vec[] scaled = new Vec[3];


                semiScaled[0] = new Vec(projVecs[0].getX() + 1.0, projVecs[0].getY() + 1, projVecs[0].getZ());
                semiScaled[1] = new Vec(projVecs[1].getX() + 1.0, projVecs[1].getY() + 1, projVecs[1].getZ());
                semiScaled[2] = new Vec(projVecs[2].getX() + 1.0, projVecs[2].getY() + 1, projVecs[2].getZ());

                //System.out.println(semiScaled[0].getX() + ", SEMISCALED");

                scaled[0] = new Vec(semiScaled[0].getX() * 0.47 * currentRoom.getWidth(), semiScaled[0].getY() * 0.47 * currentRoom.getHeight(), semiScaled[0].getZ());
                scaled[1] = new Vec(semiScaled[1].getX() * 0.47 * currentRoom.getWidth(), semiScaled[1].getY() * 0.47 * currentRoom.getHeight(), semiScaled[1].getZ());
                scaled[2] = new Vec(semiScaled[2].getX() * 0.47 * currentRoom.getWidth(), semiScaled[2].getY() * 0.47 * currentRoom.getHeight(), semiScaled[2].getZ());

                //System.out.println("HEIGHT: " + currentRoom.getHeight() + ", WIDTH: " + currentRoom.getWidth());
                //System.out.println(scaled[0].getX() + ", SCALED");





                //DRAW
                screen.drawTri((int)scaled[0].getX(), (int)scaled[0].getY(),
                        (int)scaled[1].getX(), (int)scaled[1].getY(),
                        (int)scaled[2].getX(), (int)scaled[2].getY(),
                        Color.WHITE);

            }
        }

    }
}