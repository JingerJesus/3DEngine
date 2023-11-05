package com.github.jingerjesus.gameenginethreedee.engine.geometry;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;

public class Vec {
    private double x,y,z,w = 1;

    public Vec() {
        x = 0.0; y = 0.0; z = 0.0;
    }

    public Vec(double i, double i1, double i2) {
        x = i; y = i1; z = i2;
    }

    public Vec(double[] in) {
        if (in.length == 3) new Vec(in[0], in[1], in[2]);
        else if (in.length == 4) {
            x = in[0]; y = in[1]; z = in[2]; w = in[3];
        }
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getZ() {return z;}

    public Vec project(Room r) {
        Matrix projmat = Matrix.getProjectionMatrix(r);

        Vec semi = Matrix.MultiplyVector(projmat, this);

        double[] almost = semi.getVector();

        if (almost[3] != 0) {
            for (int i = 0; i < 3; i ++) {
                almost[i] /= almost[3];
            }
        }

        return new Vec(almost[0], almost[1], almost[2]);

    }

    public double[] getVector() {
        return new double[]{x, y, z, w};
    }

}
