package com.mycompany.a1;
import com.codename1.charts.models.Point;

/**
 * This class defines Ant, a movable and food consumer game object
 * with attributes maximumSpeed, foodConsumptionRate,
 * healthLevel, and lastFlagReached.
 */

public class Ant extends Movable implements IFoodie {
	
	// Attributes
	private int maximumSpeed;
    private int foodConsumptionRate;
    private int healthLevel;
    private int lastFlagReached;
    
    // Constructor
	public Ant(int size, Point location, int color, int heading, int speed, int foodLevel,
            int maximumSpeed, int foodConsumptionRate) {
	     super(size, location, color, heading, speed, foodLevel);
	
	     this.maximumSpeed = maximumSpeed;					// upper limit of speed attribute
	     this.foodConsumptionRate = foodConsumptionRate;	// how much food ant needs to consume every tick (??)
	     this.healthLevel = 10;								// Starting health is 10
	     this.lastFlagReached = 1;							// Last flag reached is initialized to 1
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
     * Overloads toString() method to display information for all Ant objects
     */
    public String toString() {
    	String parentDesc = super.toString();
		String antDesc = " maxSpeed=" + getMaximumSpeed() + " foodConsumptionRate=" + getFoodConsumptionRate();
		return "Ant: " + parentDesc + antDesc;
    }
}
