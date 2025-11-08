package com.mycompany.a2;
import com.codename1.charts.models.Point;

/** 
 * This class defines GameObjects that are "Movable".
 * Movable objects can change locations.
 * All movable objects have:
 *   heading: Heading is the direction the object is moving towards to defined by a "compass angle."
 *   speed: The rate of how much the object's position is moving.
 *   foodLevel: Indicates how hungry the object is.
 */
public abstract class Movable extends GameObject {
	
	// Attributes
	private int heading; 	// direction defined by degrees (0=north; 90=east)
	private int speed;		// movement speed
	private int foodLevel;	// hunger level
	
	// Constructor
	public Movable(int size, Point location, int color, int heading, int speed, int foodLevel) {
        super(size, location, color);
        this.heading = heading;
        this.speed = speed;
        this.foodLevel = foodLevel;
    }
	
	// Getters
	public int getHeading() {
		return heading;
	}

	public int getSpeed() {
		return speed;
	}

	public int getFoodLevel() {
		return foodLevel;
	}
	
	// Setters
	public void setHeading(int newHeading) {
        this.heading = newHeading;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public void setFoodLevel(int newFoodLevel) {
        this.foodLevel = newFoodLevel;
    }
    
    /** 
     * Updates the location of the object based on its current heading and speed. 
     */
    public void move() {

        // Current position
        float oldX = getLocation().getX();
        float oldY = getLocation().getY();

        // Convert heading into radians
        double theta = Math.toRadians(90 - heading);

        // deltaX and deltaY based on speed
        float deltaX = (float)(Math.cos(theta) * speed);
        float deltaY = (float)(Math.sin(theta) * speed);

        // Compute new position
        float newX = oldX + deltaX;
        float newY = oldY + deltaY;

        // Update location
        setLocation(new Point(newX, newY));
    }
    
    /**
     * Overloads toString() method to display information for all Movable objects
     */
    public String toString() {
    	String parentDesc = super.toString();
		String movDesc = " heading=" + getHeading() + " speed=" + getSpeed();
		
		return parentDesc + movDesc;
    }
}
