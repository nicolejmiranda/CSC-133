package com.mycompany.a2;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

/**
 * This class is the abstract superclass of all "GameObjects".
 * All GameObjects have size, location, and color.
 * 	Size cannot change after object is instantiated	 
 */

public abstract class GameObject {
	
	// Attributes
	private int size;				// size of game object
	private Point location;			// determines the position of object
	private int color; 				// rgb color of objects
	
	// Constructor
	public GameObject(int size, Point location, int color) {
		this.size = size;
		this.location = location;
		this.color = color;
	}
	
	// Getters
	public int getSize() {
		return size;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public int getColor() {
		return color;
	}
	
	// Setters
	public void setLocation(Point newLocation) {
        this.location = newLocation;
    }

    public void setColor(int newColor) {
        this.color = newColor;
    }
    
    /**
     * Overloads toString() method to display information for all GameObjects
     */
    public String toString() {
    	
    	// Location info
    	Point objLocation = getLocation();
		float pointX = (float) (Math.round(objLocation.getX() * 10.0)/10.0);
		float pointY = (float) (Math.round(objLocation.getY() * 10.0)/10.0);
		
		// Color info
		int objColor = getColor();
		int r = ColorUtil.red(objColor);
		int g = ColorUtil.green(objColor);
        int b = ColorUtil.blue(objColor);
		
		String objDesc = "loc=" + pointX + "," + pointY + 
						" color=[" + r + "," + g + "," + b + 
						"] size=" + getSize();
		
		return objDesc;
    }
}
