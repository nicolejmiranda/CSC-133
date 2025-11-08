 package com.mycompany.a2;
import com.codename1.ui.*;
import com.codename1.ui.layouts.FlowLayout;

import java.util.Observable;
import java.util.Observer;

/**
 * This class defines an Observer class that 
 * provides a graphical output of current game states.
 *
 */

public class ScoreView extends Container implements Observer {

    private Label livesValue, clockValue, lastFlagValue, foodValue, healthValue, soundValue;

    public ScoreView() {
        this.setLayout(new FlowLayout(Component.CENTER));
        
        this.add(new Label("  Time: "));
        clockValue = new Label("0");
        this.add(clockValue);
        
        this.add(new Label("Lives Left: "));
        livesValue = new Label("0");
        this.add(livesValue);

        this.add(new Label("  Last Flag Reached: "));
        lastFlagValue = new Label("0");
        this.add(lastFlagValue);

        this.add(new Label("  Food Level: "));
        foodValue = new Label("0");
        this.add(foodValue);

        this.add(new Label("  Health Level: "));
        healthValue = new Label("0");
        this.add(healthValue);

        this.add(new Label("  Sound: "));
        soundValue = new Label("OFF");
        this.add(soundValue);
    }

    @Override
    public void update(Observable o, Object arg) {
        GameWorld gw = (GameWorld) o;
        System.out.println("\n--- ScoreView Updated ---");
        Ant ant = Ant.getAnt(null);

        clockValue.setText(String.valueOf(gw.getClock()));
        livesValue.setText(String.valueOf(gw.getLives()));
        lastFlagValue.setText(String.valueOf(ant.getLastFlagReached()));
        foodValue.setText(String.valueOf(ant.getFoodLevel()));
        healthValue.setText(String.valueOf(ant.getHealthLevel()));
        soundValue.setText(gw.isSoundOn() ? "ON" : "OFF");
        this.revalidate();   // refresh UI
    }
}
