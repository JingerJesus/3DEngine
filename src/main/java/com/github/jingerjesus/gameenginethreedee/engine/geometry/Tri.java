package com.github.jingerjesus.gameenginethreedee.engine.geometry;

public class Tri {
    private Vec[] vecs;

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
}
