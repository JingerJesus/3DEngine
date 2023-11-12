package com.github.jingerjesus.gameenginethreedee.engine.interactables;

public class Camera extends GameObject {

    private static String forward,backward,strafeleft,straferight,rise,fall,yawLeft,yawRight,pitchUp,pitchDown,rollLeft,rollRight;
    private static double linearSpeed = 0.4, rotationSpeed = 1;

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {

    }

    public static double getLinearSpeed() {return linearSpeed;}

    public static double getRotationSpeed() {return rotationSpeed;}


}
