package com.github.jingerjesus.gameenginethreedee.game.rooms;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import com.github.jingerjesus.gameenginethreedee.engine.peripherals.KeyInput;
import com.github.jingerjesus.gameenginethreedee.game.objects.Block;

public class FirstRoom extends Room {


    public FirstRoom(int w, int h, String t) {
        super(w,h,t);
    }

    @Override
    public void onStart() {
        Block block = new Block();
        block.addTo(this);
    }

    @Override
    public void onLoop() {

    }
}
