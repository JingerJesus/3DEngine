package com.github.jingerjesus.gameenginethreedee.engine.geometry;

public class Mesh {
    private Tri[] tris;

    public Mesh() {}

    public Mesh(Tri[] tris) {
        this.tris = tris;
    }

    public Tri[] getTris() {
        return tris;
    }
}
