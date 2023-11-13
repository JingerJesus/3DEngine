package com.github.jingerjesus.gameenginethreedee.engine.interactables;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Mesh;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.UnitCube;

public abstract class GameObject {
    protected Mesh mesh;
    protected Texture texture;
    private double x, y, z, w, h, d;

    protected GameObject() {
        mesh = new UnitCube().getMesh();
        texture = new Texture("defaultTexture.png");
    }

    public Mesh getMesh() {return mesh;}

    public void addTo(Room r){
        r.getObjectsList().add(this);
    }

    public abstract void onStart();
    public abstract void onLoop();

    public Texture getTexture() {return texture;}

}
