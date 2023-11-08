package com.github.jingerjesus.gameenginethreedee.game;

import com.github.jingerjesus.gameenginethreedee.engine.Driver;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import com.github.jingerjesus.gameenginethreedee.game.rooms.FirstRoom;

public class Controller {
    /*
    Declare static game-wide variables here.
    */
    static Room firstRoom = new FirstRoom(1920, 1080, "graphics engine take 5");

    public static void start() {
        Driver.changeRoomTo(firstRoom);
        Driver.screen.cls();
    }

}
