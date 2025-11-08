package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the RightTurnCommand, it turns
 * the Ant to the right by invoking the GameWorlds headRight method
 * when executed.
 */

public class RightTurnCommand extends Command {
    private GameWorld gw;

    public RightTurnCommand(GameWorld gw) {
        super("Right");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.headRight();
    }
}
