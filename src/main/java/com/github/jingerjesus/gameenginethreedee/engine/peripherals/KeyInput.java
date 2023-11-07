package com.github.jingerjesus.gameenginethreedee.engine.peripherals;

import com.github.jingerjesus.gameenginethreedee.engine.interactables.Room;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;

public class KeyInput {
    private static Scene currentScene;
    private static HashSet<String> currentlyActiveKeys;

    public static void setCurrentRoom(Room room) {
        currentScene = room.getScene();
        prepareActionHandlers();
    }

    public static boolean isPressed(String key) {  // I would make a note of what the key names are. But i wouldnt. Trololololol.
        //if (currentlyActiveKeys.contains(key)) System.out.println("Key down :3");
        return currentlyActiveKeys.contains(key);
    }

    // I stole this code. Credit to https://stackoverflow.com/questions/37472273/detect-single-key-press-in-javafx
    private static void prepareActionHandlers()
    {
        currentlyActiveKeys = new HashSet<String>();
        currentScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());

            }
        });
        currentScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());

            }
        });
    }
    // I did, however. write the following code myself. :3
}