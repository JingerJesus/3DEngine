package com.github.jingerjesus.gameenginethreedee.game.objects;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Mesh;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.UnitCube;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.GameObject;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.Texture;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.ObjFileLoader;

public class Block extends GameObject {

    public Block() {
        super();
        mesh = ObjFileLoader.getObj("untitled.obj");
    }

    public Block(Texture t) {
        super();
        texture = t;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {

    }

}
