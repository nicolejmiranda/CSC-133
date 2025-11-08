package com.mycompany.a2;
import com.codename1.ui.Container;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * This class defines an Observer class that
 * provides a view of the Game map.
 *
 */

public class MapView extends Container implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        GameWorld gw = (GameWorld) o;
        System.out.println("\n--- MapView Updated ---");
        gw.map();  // reuse map() logic from GameWorld
    }
}
