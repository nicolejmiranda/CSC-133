package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the CollideFlagCommand, it simulates
 * the Ant colliding with a flag by invoking the GameWorlds touchFlag method
 * when executed.
 */

public class CollideFlagCommand extends Command {
    private GameWorld gw;

    public CollideFlagCommand(GameWorld gw) {
        super("Collide with Flag ");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.touchFlag();
    }
}
