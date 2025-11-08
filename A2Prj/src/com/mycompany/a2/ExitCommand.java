package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the ExitCommand, it displays a confirmation
 * dialog asking the user to confirm quitting the game, and exits
 * the application if the user selects 'Yes'.
 */


public class ExitCommand extends Command {

    public ExitCommand() {
        super("Exit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean exit = Dialog.show("Confirm Exit", "Do you really want to quit?", "Yes", "No");
        if (exit) {
            System.exit(0);
        }
    }
}
