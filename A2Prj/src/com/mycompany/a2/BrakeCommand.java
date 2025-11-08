package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the BrakeCommand, it decreases
 * the Ant speed in the game world by invoking the GameWorld brake method
 * when executed.
 */

public class BrakeCommand extends Command {
    private GameWorld gw;

    public BrakeCommand(GameWorld gw) {
        super("Brake");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.brake();
    }
}
