package com.github.jingerjesus.gameenginethreedee.engine.interactables;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Mesh;

public abstract class GameObject {
    protected Mesh mesh;
    private double x, y, z, w, h, l;

    public Mesh getMesh() {return mesh;}

    public void addTo(Room r){
        r.getObjectsList().add(this);
    }

    public abstract void onStart();
    public abstract void onLoop();

}
