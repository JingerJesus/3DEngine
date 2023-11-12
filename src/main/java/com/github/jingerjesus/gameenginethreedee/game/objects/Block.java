package com.github.jingerjesus.gameenginethreedee.game.objects;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Mesh;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.UnitCube;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.GameObject;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.ObjFileLoader;

public class Block extends GameObject {

    public Block() {
        super();
        mesh = new UnitCube().getMesh();
        //mesh = ObjFileLoader.getObj("axis.obj");
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {

    }
}
