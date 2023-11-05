package com.github.jingerjesus.gameenginethreedee.game;

import com.github.jingerjesus.gameenginethreedee.engine.Driver;
import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;

public class Controller {
    /*
    Declare static game-wide variables here.
    */
    static Room firstRoom = new Room(1024, 768, "what do you mean im blocked");

    public static void start() {
        Driver.changeRoomTo(firstRoom);
        Driver.screen.cls();
    }

}
