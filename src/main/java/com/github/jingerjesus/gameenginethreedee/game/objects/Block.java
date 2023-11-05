package com.github.jingerjesus.gameenginethreedee.game.objects;

import com.github.jingerjesus.gameenginethreedee.engine.geometry.Mesh;
import com.github.jingerjesus.gameenginethreedee.engine.geometry.UnitCube;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.GameObject;

public class Block extends GameObject {

    public Block() {
        super();
        mesh = new UnitCube().getMesh();
    }

}
