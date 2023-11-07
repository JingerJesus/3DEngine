package com.github.jingerjesus.gameenginethreedee.engine;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Matrix;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Tri;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.Vec;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.GameObject;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.GraphicsScreen;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.KeyInput;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Driver extends Application {
    static Stage mainStage;
    static Scene currentScene;
    static Group currentGroup;
    static Room currentRoom;

    static int θ;
    static int θ0;
    public static GraphicsScreen screen;

    public static ArrayList<Tri> toDraw;

    static double yaw;

    public static Vec vCamera = new Vec(0, 0, 0), vLookDir = new Vec(0,0,1);

    @Override
    public void start(Stage stage) throws IOException {
        Controller.start();

        KeyInput.setCurrentRoom(currentRoom);


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

        //θ++; θ0 +=2;

        toDraw = new ArrayList<Tri>();


        //controller
        if (KeyInput.isPressed("R")) {
            vCamera = new Vec(vCamera.getX(), vCamera.getY() - 0.8, vCamera.getZ());
        } else if (KeyInput.isPressed("V")) {
            vCamera = new Vec(vCamera.getX(), vCamera.getY() + 0.8, vCamera.getZ());
        }

        if (KeyInput.isPressed("A")) {
            vCamera = new Vec(vCamera.getX() - 0.8, vCamera.getY(), vCamera.getZ());
        } else if (KeyInput.isPressed("D")) {
            vCamera = new Vec(vCamera.getX() + 0.8, vCamera.getY(), vCamera.getZ());
        }

        if (KeyInput.isPressed("Q")) {
            yaw += 2;
        } else if (KeyInput.isPressed("E")) {
            yaw -= 2;
        }

        Vec forward = vLookDir.normalize().mult(0.8);
        double[] f = forward.getVector();

        if (KeyInput.isPressed("W")) {
            vCamera = vCamera.add(forward);
        } else if (KeyInput.isPressed("S")) {
            vCamera = vCamera.sub(forward);
        }
        //end controller

        Matrix worldMat = Matrix.getIdentityMatrix();
        worldMat = Matrix.MatrixMultiplication(Matrix.getZRotationalMatrix(θ), Matrix.getXRotationalMatrix(θ0));
        worldMat = Matrix.MatrixMultiplication(worldMat, Matrix.getTranslationalMatrix(0, 0, 20));

        Matrix cameraRot = Matrix.getYRotationalMatrix(yaw);

        vLookDir = new Vec(0,0,1);
        Vec up = new Vec(0,1,0);
        Vec target = new Vec(0,0,1);


        vLookDir = Matrix.MultiplyVector(cameraRot, target);
        target = vCamera.add(vLookDir);

        Matrix mCamera = Matrix.getPointAtMatrix(vCamera, target, up);
        Matrix mView = Matrix.getQuickInverseOf(mCamera);




        for (GameObject object : objects) {
            for (Tri tri : object.getMesh().getTris()) {



                Vec[] transformed = new Vec[3];

                for (int i = 0; i < 3; i ++) {
                    transformed[i] = Matrix.MultiplyVector(worldMat, tri.getVecs()[i]);
                }


                Vec normal, line1, line2;
                line1 = Vec.getVecBetween(transformed[0], transformed[1]);
                line2 = Vec.getVecBetween(transformed[0], transformed[2]);
                normal = line1.cross(line2);
                normal = normal.normalize();

                //if (normal.getZ() < 0) {
                if (Vec.DotProductOf(normal, Vec.getVecBetween(transformed[0], vCamera)) > 0) {

                    //VIEW
                    Vec[] viewVecs = new Vec[3];
                    for (int i = 0; i < 3; i ++) {
                        viewVecs[i] = Matrix.MultiplyVector(mView, transformed[i]);
                    }

                    //CLIP

                    //clip against ZNear
                    Tri[] clippedTris = Tri.clipAgainstPlane(new Vec(0, 0, 1 + currentRoom.zNear), vLookDir, new Tri(viewVecs));

                    for (int n = 0; n < clippedTris.length; n ++) {

                        //PROJECT
                        Vec[] projVecs = new Vec[3];

                        //System.out.println(preProjVecs[0].getX() + ": PREPROJ");

                        for (int i = 0; i < 3; i ++) {
                            projVecs[i] = Matrix.projectVector(Matrix.getProjectionMatrix(currentRoom), clippedTris[n].getVecs()[i]);
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

                        Vec lighting = new Vec(0, 0, -1);

                        lighting = lighting.normalize();
                        double shading = Vec.DotProductOf(lighting, normal);

                        int shaded = (int)(255 * shading);
                        if (shaded < 0) {
                            shaded = 0;
                        }


                        Tri draw = new Tri(scaled);
                        draw.setShading(shaded);


                        toDraw.add(draw);
                    }

                }
            }
        }

        //sort tris by z midpoint
        Collections.sort(toDraw, new Comparator<Tri>() {
            @Override
            public int compare(Tri o1, Tri o2) {
                return (int) ((o2.getMidpointZ() - o1.getMidpointZ()) * 1000000);
            }
        });

        for (Tri tri : toDraw) {

            screen.fillTri((int)tri.getVecs()[0].getX(), (int)tri.getVecs()[0].getY(),
                    (int)tri.getVecs()[1].getX(), (int)tri.getVecs()[1].getY(),
                    (int)tri.getVecs()[2].getX(), (int)tri.getVecs()[2].getY(),
                    Color.rgb((int)tri.getShading(), (int)tri.getShading(), (int)tri.getShading()));


            //debug wireframe
            screen.drawTri((int)tri.getVecs()[0].getX(), (int)tri.getVecs()[0].getY(), (int)tri.getVecs()[1].getX(), (int)tri.getVecs()[1].getY(), (int)tri.getVecs()[2].getX(), (int)tri.getVecs() [2].getY(),
                    //Color.rgb((int)tri.getShading(), (int)tri.getShading(), (int)tri.getShading()));
                    Color.BLACK);
        }

    }
}