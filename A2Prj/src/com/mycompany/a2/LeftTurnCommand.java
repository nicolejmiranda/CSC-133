package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the LeftTurnCommand, it turns
 * the Ant to the left by invoking the GameWorlds headLeft method
 * when executed.
 */


public class LeftTurnCommand extends Command {
    private GameWorld gw;

    public LeftTurnCommand(GameWorld gw) {
        super("Left");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.headLeft(); 
    }
}
