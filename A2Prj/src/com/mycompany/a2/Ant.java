package com.mycompany.a2;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

/**
 * This class defines Ant, a movable and food consumer game object
 * implemented as a Singleton so only one Ant exists in the game world.
 */

public class Ant extends Movable implements IFoodie {
	// Singleton instance
	private static Ant theAnt;
	
	// Default values

	// Attributes
	private int maximumSpeed;
    private int foodConsumptionRate;
    private int healthLevel;
    private int lastFlagReached;
    
    // Private Constructor
    private Ant(Point startLocation) {
        // Initialize through Movable constructor
        super(
            40,                          // initial size
            startLocation,               // starting position
            ColorUtil.rgb(255, 100, 0),  // orange-red color
            0,                           // heading
            5,                           // starting speed
            50                           // starting food level
        );

        this.maximumSpeed = 50;
        this.foodConsumptionRate = 2;
        this.healthLevel = 10;
        this.lastFlagReached = 1;
    }
	
	// Accessor
	 public static Ant getAnt(Point startLocation) {
		if (theAnt == null) {
			theAnt = new Ant(startLocation);
		}
		return theAnt;
	}
	
	// Getters
	public int getMaximumSpeed() {
		return maximumSpeed;
	}

	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}

	public int getHealthLevel() {
		return healthLevel;
	}

	public int getLastFlagReached() {
		return lastFlagReached;
	}
	
	// Setters
	
	/**
	 *  Prevents foodConsumptionRate to go below zero.
	 */
	public void setFoodConsumption(int amount) {
		if (amount <= 0) {
            this.foodConsumptionRate = this.foodConsumptionRate + 1;
        } else {
            this.foodConsumptionRate = amount;
        }
	}
	
	public void setLastFlagReached(int flagNum) {
        this.lastFlagReached = flagNum;
    }
	
	// Methods
	
	/**
	 * Reduces the health of the ant and adjusts the speed accordingly.
	 */
	public void reduceHealth() {
		if (healthLevel > 0) {
		    healthLevel--;
		}
		
		int allowedSpeed = maximumSpeed * healthLevel / 10;		// example: when health is at 5, allowed speed is 50% of its max speed
        if (getSpeed() > allowedSpeed) {
            setSpeed(allowedSpeed);
        }
	}
	
	/**
     * Resets the Ant stats when the world is reinitialized.
     */
	public void resetStats(Point startLocation) {
        setLocation(startLocation);
        setHeading(0);
        setSpeed(5);
        setFoodLevel(50);
        this.healthLevel = 10;
        this.lastFlagReached = 1;
        this.maximumSpeed = 50;
        this.foodConsumptionRate = 2;
    }
	
	/**
     * Overloads toString() method to display information for all Ant objects
     */
    public String toString() {
    	String parentDesc = super.toString();
		String antDesc = " maxSpeed=" + getMaximumSpeed() + " foodConsumptionRate=" + getFoodConsumptionRate();
		return "Ant: " + parentDesc + antDesc;
    }
}
