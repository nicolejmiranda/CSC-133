package com.mycompany.a2;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;

/**
 * This class defines GameWorld. 
 * It sets up GameObjects and manipulates game data.
 */
public class GameWorld extends Observable {
	private final GameObjectCollection worldVector = new GameObjectCollection();		// Dynamic storage for all game objects.
	
	private int playerLives;											// starts at 3
	private int clock;              									// ticks since start
	private Ant ant;                									// the single player-controlled ant
	private int maxFlagNum;												// maximum number of flags generated
	private boolean sound;												// sound on/off
	private boolean exitConfirmation = false;							// used to track exit confirmation
	private int mapWidth;												// map width
	private int mapHeight;												// map height
	
	/** 
	 * Initialize the world
	 */
	public void init() {
		
		// Initial states
		clock = 0;
		playerLives = 3;
		
		// Set up world objects
		setUpWorld();
	}
	
	/**
	 *  Increase the speed of the ant by a small amount.
	 *  Speed cannot be more than the maximum speed. 
	 */
	public void accelerate() {
		int acceleration = 2;
		int newSpeed = ant.getSpeed() + acceleration;
		int allowedSpeed = (ant.getHealthLevel() * ant.getMaximumSpeed()) / 10;
		if (newSpeed < allowedSpeed) {
			ant.setSpeed(newSpeed);
			System.out.println("Accelerate: speed = " + ant.getSpeed());
		} else System.out.println("You cannot go any faster.");
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Reduces the speed of the ant,
	 */
	public void brake() {
		int deacceleration = 2;
		int newSpeed = ant.getSpeed() - deacceleration;
		if (newSpeed > 0) {
			ant.setSpeed(newSpeed);
			System.out.println("Brake: speed = " + ant.getSpeed());
		} else System.out.println("You cannot go any slower.");
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Change heading of ant by 5 degrees to the left
	 */
	public void headLeft() {
		ant.setHeading((ant.getHeading() - 5 + 360) % 360);
	    System.out.println("Turn left: heading=" + ant.getHeading());
	    
	    setChanged();
	    notifyObservers();
	}
	
	/**
	 * Change heading of ant by 5 degrees to the right
	 */
	public void headRight() {
		ant.setHeading((ant.getHeading() + 5) % 360);
	    System.out.println("Turn right: heading=" + ant.getHeading());
	    
	    setChanged();
		notifyObservers();
	}
	
	/**
	 * Change food consumption rate randomly
	 */
	public void changeEatRate() {
		Random rand = new Random();
		int x = 5; 		// range of change
		int rate = -x + rand.nextInt(x);
		ant.setFoodConsumption(ant.getFoodConsumptionRate() + rate);
		System.out.println("Food consumption now " + ant.getFoodConsumptionRate());
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Collide with Flag number x
	 * Updates lastFlagReached if Flag number is one greater than x.
	 * If last flag in the sequence is reached, exits the game with player winning. 
	 */
	public void touchFlag() {
		// Create a text field for flag number input
		Command cOk = new Command("Ok");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[]{cOk, cCancel};
		TextField myTF = new TextField();
		

	    // Show dialog and wait for user response
		Command c = Dialog.show("Enter flag number:", myTF, cmds);
		if (c == cOk) {
			// Validate and parse user input
	    	int x;
		    try {
		        x = Integer.parseInt(myTF.getText().trim());
		    } catch (NumberFormatException e) {
		        Dialog.show("Invalid Input", "Please enter a valid flag number.", "OK", null);
		        return;
		    }
		    
			// touch flag
			if (x == ant.getLastFlagReached() + 1 && x <= maxFlagNum) {
	            ant.setLastFlagReached(x);
	            System.out.println("Reached flag " + x);
	            if (x == maxFlagNum) {
	            	System.out.println("Game over, you win! Total time: " + clock);
	                System.exit(0);
	            }
	        } else {
	        	System.out.println("Invalid Flag. Please go to Flag #" + (ant.getLastFlagReached() + 1) + " to proceed.");
	        } 
	    } else {
	    	System.out.println("Flag entry cancelled.");
	        return;
	    }
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Collide with food station chosen randomly.
	 * Increases food level by food station capacity.
	 */
	public void touchFoodStation() {
		FoodStation fs = findNonEmptyFs();
		if (fs == null) {
			System.out.println("All food stations are empty.");
			return;
		}
		
		int foodAmt = fs.getCapacity();									// amount of food in station
		ant.setFoodLevel(ant.getFoodLevel() + foodAmt);
		fs.setCapacity(0);												// set station capacity to empty
		fs.setColor(ColorUtil.rgb(209, 227, 209));						// change color to show empty
		
		worldVector.add(createFoodStation(ColorUtil.rgb(0, 255, 0)));	// spawns new food station
		
		System.out.println("Ate food: +" + foodAmt + " (foodLevel = " + ant.getFoodLevel() + ")");
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Collide with spider
	 * Handles speed limit rule
	 * Checks if player is dead after every hit
	 */
	public void touchSpider() {
		ant.reduceHealth();
		
		// Fade ant color by increasing current rgb values by 30%
		int currentColor = ant.getColor();
		float amt = 0.3f;						// percentage to increase
		int r = ColorUtil.red(currentColor);
		int g = ColorUtil.green(currentColor);
        int b = ColorUtil.blue(currentColor);
        r = (int)(r + (255 - r) * amt);
        g = (int)(g + (255 - g) * amt);
        b = (int)(b + (255 - b) * amt);
        int newColor = ColorUtil.rgb(r, g, b);
        ant.setColor(newColor);
        
        System.out.println("Ouch! health=" + ant.getHealthLevel() + ", speed=" + ant.getSpeed());
        checkIfGameOver();
        
        setChanged();
		notifyObservers();
	}
	
	/**
	 * Ticks clock
	 * All movable objects move based on their current heading and speed.
	 * Player reduces food level
	 * Checks if game over since food level goes down every tick.
	 */
	public void tickClock() {
		// All movable objects move
		IIterator it = worldVector.getIterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject) it.getNext();
			if (obj instanceof Movable) {
				Movable mObj = (Movable) obj;
				mObj.move();
				}
		}
		
		// Ant reduces food level
		ant.setFoodLevel(ant.getFoodLevel() - ant.getFoodConsumptionRate());
		
		// Clock ticks
		clock += 1;
		
		System.out.println("Tick: time=" + clock + ", food=" + ant.getFoodLevel() + ", health=" + ant.getHealthLevel());
		
		// Checks if game over since food level goes down every tick.
		checkIfGameOver();
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Outputs the following game state values
	 * - Number of lives left
	 * - Elapsed time
	 * - Last flag reached
	 * - Current food level
	 * - Current health level
	 */
	public void display() {
		System.out.println("LIVES: " + playerLives);
		System.out.println("TIME: " + clock);
		System.out.println("LAST FLAG: " + ant.getLastFlagReached());
		System.out.println("FOOD: " + ant.getFoodLevel());
		System.out.println("HEALTH: " + ant.getHealthLevel());
	}
	
	/**
	 * Outputs information of all GameObjects to the system console.
	 */
	public void map() {
		
		IIterator it = worldVector.getIterator();

		while (it.hasNext()) {
		    GameObject obj = (GameObject) it.getNext();

		    if (obj instanceof Flag) {
		        System.out.println(obj.toString());
		    } else if (obj instanceof Ant) {
		        System.out.println(obj.toString());
		    } else if (obj instanceof Spider) {
		        System.out.println(obj.toString());
		    } else if (obj instanceof FoodStation) {
		        System.out.println(obj.toString());
		    }
		}
	}
	
	/**
	 * Exit game
	 */
	public void exitGame() {
		if (!exitConfirmation) {
			exitConfirmation = true;
	        System.out.println("Are you sure you want to exit? (y/n)");
	    }
	}
	
	/**
	 * Confirms exit > "YES"
	 */
	public void confirmExit() {
		if (exitConfirmation) {
	        System.out.println("Goodbye!");
	        System.exit(0);
	    } else {
	        System.out.println("Invalid command. Press 'x' if you want to exit.");
	    }
	}
	
	/**
	 * Cancel exit > "NO"
	 */
	public void cancelExit() {
		if (exitConfirmation) {
			exitConfirmation = false;
	        System.out.println("OK! Continuing game..");
	    } else {
	        System.out.println("Invalid command. Press 'x' if you want to exit.");
	    }
	}
	
	/*
	 * Toggles sound on and off
	 */
	public void toggleSound() {
		if (sound == false)
			sound = true;
		else if (sound == true)
			sound = false;
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Returns the number of player lives
	 */
	public int getLives() {
		return playerLives;
	}
	
	/**
	 * Returns the current clock value
	 */
	public int getClock() {
		return clock;
	}
	
	/**
	 * Returns sound state value
	 */
	public boolean isSoundOn() {
		if (sound == false)
			return false;
		else return true;
	}
	
	/**
	 * Returns the collection of all game objects in the world.
	 * Used by MapView to iterate and draw objects.
	 */
	public Object getGameObjects() {
		return worldVector;
	}
	
	/**
	 * Set values for map width and height
	 */
	public void setMapSize(int width, int height) {
	    this.mapWidth = width;
	    this.mapHeight = height;
	}

	
	//----------------//
	// Helper methods //
	//----------------//
	
	/**
	 * Re-initializes world without resetting the clock and lives.
	 *  Clears worldVector and sets up again.
	 */
	private void reinit() {
		worldVector.clear();
		setUpWorld();
	}
	
	/**
	 * Sets up all objects existing in the world.
	 */
	private void setUpWorld() {
		// Object colors
		int flagColor = ColorUtil.rgb(0, 0, 255);
        int fsColor = ColorUtil.rgb(0, 255, 0);
        int spiderColor = ColorUtil.rgb(0, 0, 0);
        
        // Set Flags
        worldVector.add(new Flag(10, new Point(200f, 200f), flagColor, 1));
        worldVector.add(new Flag(10, new Point(200f, 800f), flagColor, 2));
        worldVector.add(new Flag(10, new Point(700f, 800f), flagColor, 3));
        worldVector.add(new Flag(10, new Point(900f, 400f), flagColor, 4));
        maxFlagNum = 4;
        
        // Generate Ant (Player) at Flag #1
        Point flag1 = (getFlagByNumber(1).getLocation());
        if (ant == null) {
	        ant = Ant.getAnt(flag1);
        } else ant.resetStats(flag1);
        worldVector.add(ant);
        
        // Generate spiders
        worldVector.add(createSpider(spiderColor));
        worldVector.add(createSpider(spiderColor));
        
        // Generate food stations
        worldVector.add(createFoodStation(fsColor));
        worldVector.add(createFoodStation(fsColor));
	}
	
	/**
	 * Handles spider creation with random position, size, heading & speed
	 */
	private Spider createSpider(int spiderColor) {
		Random rand = new Random();
		int spiderSize = 5 + rand.nextInt(10);
		Point spiderLocation = generateRandomLocation();
		int heading = 0 + rand.nextInt(359);
		int speed = 5 + rand.nextInt(10);
		int foodLevel = 1;
		
		return new Spider(spiderSize, spiderLocation, spiderColor, heading, speed, foodLevel);
	}
	
	/**
	 * Handles Food Station creation with random location & size
	 */
	private FoodStation createFoodStation(int fsColor) {
		Random rand = new Random();
		int fsSize = 10 + rand.nextInt(50);
		Point fsLocation = generateRandomLocation();
		return new FoodStation(fsSize, fsLocation, fsColor);
	}
	
	/**
	 * Random location generator for objects that spawns at random locations
	 */
	private Point generateRandomLocation() {
	    Random rand = new Random();
	    float x = rand.nextFloat() * mapWidth;
	    float y = rand.nextFloat() * mapHeight;
	    return new Point(x, y);
	}

	
	/**
	 * Picks a non-empty food station randomly.
	 */
	private FoodStation findNonEmptyFs() {
		Random rand = new Random();
	    Vector<FoodStation> fullFs = new Vector<>();
	    
	    IIterator it = worldVector.getIterator();
	    while(it.hasNext()) {
	    	GameObject obj = (GameObject) it.getNext();
	    	if (obj instanceof FoodStation) {             // check type first
	            FoodStation fs = (FoodStation) obj;
	            if (fs.getCapacity() > 0) {
	                fullFs.add(fs);
	            }
	        }
	    }
	    if (fullFs.isEmpty()) {
	        return null;
	    }
	    return fullFs.elementAt(rand.nextInt(fullFs.size()));
	}
	
	/**
	 * Checks healthLevel and how many lives are left.
	 * Decreases playerLives if health is zero.
	 * Exits or re-initializes the game depending on lives left.
	 */
	private void checkIfGameOver() {
		boolean dead = (ant.getFoodLevel() <= 0) || (ant.getHealthLevel() <= 0);
        if (!dead) return;

        playerLives -= 1;
        System.out.println("Oops! You're dead. Lives left: " + playerLives);

        if (playerLives > 0) {
            reinit();
        } else {
        	System.out.println("Game over, you failed!");
            System.exit(0);
        }
	}
	
	/*
	 * Returns the Flag in sequence number of the passed num.
	 */
	private Flag getFlagByNumber(int num) {
	    IIterator it = worldVector.getIterator();
	    while (it.hasNext()) {
	        GameObject obj = (GameObject) it.getNext();
	        if (obj instanceof Flag) {
	            Flag f = (Flag) obj;
	            if (f.getSequenceNumber() == num) {
	                return f;
	            }
	        }
	    }
	    return null; // not found
	}




	
	
}
