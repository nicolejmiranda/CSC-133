package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the EatFoodCommand, it simulates
 * the Ant consuming food by invoking the GameWorlds touchFoodStation method
 * when executed.
 */


public class CollideFsCommand extends Command {
    private GameWorld gw;

    public CollideFsCommand(GameWorld gw) {
        super("Collide with Food Stations");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.touchFoodStation();
    }
}
