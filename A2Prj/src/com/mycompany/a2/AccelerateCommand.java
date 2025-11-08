package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the AccelerateCommand, it increases
 * the Ants speed in the game world by invoking the GameWorlds accelerate method
 * when executed.
 */


public class AccelerateCommand extends Command {
	private GameWorld gw;

    public AccelerateCommand(GameWorld gw) {
        super("Accelerate");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.accelerate();
    }
}
