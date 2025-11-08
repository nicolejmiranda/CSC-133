package com.mycompany.a1;
import com.codename1.charts.models.Point;

/**
 * This class defines the "Flags" in the game which acts as a waypoint on the path.
 *  Flags have a sequenceNumber that defines the order of which they must be walked on.
 *  Flags cannot change colors once instantiated.
 */
public class Flag extends Fixed {
	
	// Attributes
	private int sequenceNumber; 	// order of the flag on the path
	
	// Constructor
	public Flag(int size, Point location, int color, int sequenceNumber) {
		super(size, location, color);
		this.sequenceNumber = sequenceNumber;
	}
	
	// Getters
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	// Setters
	public void setSequenceNumber(int newSequenceNumber) {
		this.sequenceNumber = newSequenceNumber;
	}
	
	/**
	 * Overloads setColor since Flags cannot change color once created.
	 */
	public void setColor(int newColor) {}
	
	/**
     * Overloads toString() method to display information for all Flag objects
     */
    public String toString() {
    	String parentDesc = super.toString();
    	String flagDesc = " seqNum=" + getSequenceNumber();
		return "Flag: " + parentDesc + flagDesc;
    }

}
