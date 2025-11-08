package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the SetFoodConsumptionCommand, it adjusts
 * the Ants food consumption rate by invoking the GameWorlds changeEatRate method
 * when executed.
 */


public class SetFoodConsumptionCommand extends Command {
	private GameWorld gw;

    public SetFoodConsumptionCommand(GameWorld gw) {
        super("Set Food Consumption");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.changeEatRate();
    }
}
