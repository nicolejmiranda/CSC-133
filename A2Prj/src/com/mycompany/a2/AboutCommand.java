package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * This class defines the AboutCommand, it displays
 * game information such as the title, assignment number, and author
 * in a dialog box when executed.
 */

public class AboutCommand extends Command {

    public AboutCommand() {
        super("About");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Dialog.show("About", "Ant Game\nAssignment 2\nCreated by Nicole Espinoza", "OK", null);
    }
}
