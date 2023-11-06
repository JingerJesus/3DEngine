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

    public double getX() {return this.x;}
    public double getY() {return y;}
    public double getZ() {return z;}

    public double[] getVector() {
        return new double[]{x, y, z, w};
    }

}
