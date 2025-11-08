package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the CollideSpiderCommand, it simulates
 * the Ant colliding with a spider by invoking the GameWorlds touchSpider method
 * when executed.
 */


public class CollideSpiderCommand extends Command {
    private GameWorld gw;

    public CollideSpiderCommand(GameWorld gw) {
        super("Collide Spider");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.touchSpider();
    }
}
