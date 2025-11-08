package com.mycompany.a2;
import java.util.Random;
import com.codename1.charts.models.Point;

/**
 * This class defines Spiders, movable objects with
 * random heading values. 
 *  If center hits world border, heading changes so 
 * 	 it does not go out of bounds.
 *  If location is equal to Ant's location, Ant's health decreases by 1
 *  Spiders cannot change colors once created
 *  Speed is initialized to a random value (5-10)
 *  Heading is initialized to a random value (0-359)
 */

public class Spider extends Movable {
	
	// Constructor
	public Spider(int size, Point location, int color, int heading, int speed, int foodLevel) {
		
		super(size, location, color, heading, speed, foodLevel);

    }
	
	// Setters
	
	/**
	 * Overloads setColor() since  Spiders can't change colors after initialization
	 */
	public void setColor(int newColor) {}
	
	/**
	 * Overloads move() to randomly adjust the heading
	 */
	public void move() {
		
		Random rand = new Random();	
		
		// Randomly adjust heading 5 degrees
	    int randomDir = -5 + rand.nextInt(5);
	    setHeading((getHeading() + randomDir + 360) % 360);

	    // Update position using Movable's math
	    super.move();

	    // Stay inside world bounds (0 to 1000)
	    float x = getLocation().getX();
	    float y = getLocation().getY();

	    if (x < 0 || x > 1000 || y < 0 || y > 1000) {	// If hitting boundary
	        setHeading((getHeading() + 180) % 360);		// Reverse direction
	        super.move();
	    }
	}
	
	/**
     * Overloads toString() method to display information for all Spider objects
     */
    public String toString() {
    	String parentDesc = super.toString();
		return "Spider: " + parentDesc;
    }
}
