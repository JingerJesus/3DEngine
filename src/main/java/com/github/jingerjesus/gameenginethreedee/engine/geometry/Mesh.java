package com.github.jingerjesus.gameenginethreedee.engine.geometry;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Texture;

public class Mesh {
    private Tri[] tris;

    private Texture texture;

    public Mesh() {}

    public Mesh(Tri[] tris) {
        this.tris = tris;
    }

    public Mesh(Tri[] tris, Texture tex) {
        this.tris = tris; texture = tex;

    }

    public Tri[] getTris() {
        return tris;
    }

    public void setTexture(Texture t) {
        texture = t;
        for (int i = 0; i < tris.length; i ++) {
            tris[i].setTexture(t);
        }
    }

}
