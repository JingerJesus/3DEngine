package com.github.jingerjesus.gameenginethreedee.engine.geometry;

public class Tri {
    private Vec[] vecs;

    private double shading;

    public Tri() {
        vecs = new Vec[3];
    }

    public Tri(Vec[] vin) {
        vecs = new Vec[3];
        for (int i = 0; i < 3; i ++) {
            vecs[i] = vin[i];
        }
    }

    public Tri(Vec p0, Vec p1, Vec p2) {
        vecs = new Vec[]{p0, p1, p2};
    }

    public Vec[] getVecs() {return vecs;}

    public void setShading(double shaded) {
        shading = shaded;
    }

    public double getShading() {
        return shading;
    }

    public double getMidpointZ() {
        double sum = 0;

        for (int i = 0; i < 3; i ++) {
            sum += vecs[i].getZ();
        }

        sum /= 3;

        return sum;
    }
}
