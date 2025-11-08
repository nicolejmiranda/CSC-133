package com.mycompany.a2;
import com.codename1.charts.models.Point;

/**
 * This class defines the "FoodStations" in the game for refilling hunger level.
 *  All FoodStations have a "capacity" which defines the amount of food it contains.
 */

public class FoodStation extends Fixed {
	
	// Attributes
	private int capacity;		// amount of food
	
	// Constructor
	public FoodStation(int size, Point location, int color) {
        super(size, location, color);
        this.capacity = size; // initial capacity proportional to size
    }
	
	// Getters
	public int getCapacity() {
        return capacity;
    }
	
	// Setters
	public void setCapacity(int newCapacity) {
		this.capacity = newCapacity;
    }
	
	/**
     * Overloads toString() method to display information for all FoodStation objects
     */
    public String toString() {
    	String parentDesc = super.toString();
    	String fsDesc = " capacity=" + getCapacity();
		return "FoodStation: " + parentDesc + fsDesc;
    }
}
