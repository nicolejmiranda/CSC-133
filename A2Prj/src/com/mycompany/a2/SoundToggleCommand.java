package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the SoundToggleCommand, it toggles
 * the game's sound on or off by invoking the GameWorlds toggleSound method
 * when executed.
 */

public class SoundToggleCommand extends Command {
    private GameWorld gw;

    public SoundToggleCommand(GameWorld gw) {
        super("Toggle Sound");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.toggleSound();
    }
}
