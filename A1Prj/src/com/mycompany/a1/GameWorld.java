package com.mycompany.a1;
import java.util.Random;
import java.util.Vector;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

/**
 * This class defines GameWorld. 
 * It sets up GameObjects and manipulates game data.
 */
public class GameWorld {
	
	private static final float WORLD_MIN = 0f;							// minimum world map size
    private static final float WORLD_MAX = 1000f;						// maximum world map size
	
	private final Vector<GameObject> worldVector = new Vector<>();		// Dynamic storage for all game objects.
	
	private int playerLives;											// starts at 3
	private int clock;              									// ticks since start
	private Ant ant;                									// the single player-controlled ant
	private int maxFlagNum;												// maximum number of flags generated
	
	private boolean exitConfirmation = false;							// used to track exit confirmation
	
	/** 
	 * Initialize the world
	 * > Clock starts at 0
	 * 
	 * > Player starts with 3 lives
	 * 	 Randomize n amount of Flags between 4 and 9
	 * 	 Player's initial position at Flag #1
	 * 	 Initial heading is 0
	 * 	 Initial speed is 5
	 * 	 Initial size is 10
	 * 
	 * > 2 Spiders are spawned
	 * 	 Random position, size, heading & speed
	 * 
	 * > 2 Food stations are spawned
	 * 	 Random location & size
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
	}
	
	/**
	 * Change heading of ant by 5 degrees to the left
	 */
	public void headLeft() {
		ant.setHeading((ant.getHeading() - 5 + 360) % 360);
	    System.out.println("Turn left: heading=" + ant.getHeading());
	}
	
	/**
	 * Change heading of ant by 5 degrees to the right
	 */
	public void headRight() {
		ant.setHeading((ant.getHeading() + 5) % 360);
	    System.out.println("Turn right: heading=" + ant.getHeading());
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
	}
	
	/**
	 * Collide with Flag number x
	 * Updates lastFlagReached if Flag number is one greater than x.
	 * If last flag in the sequence is reached, exits the game with player winning. 
	 */
	public void touchFlag(int x) {
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
	}
	
	/**
	 * Ticks clock
	 * All movable objects move based on their current heading and speed.
	 * Player reduces food level
	 * Checks if game over since food level goes down every tick.
	 */
	public void tickClock() {
		// All movable objects move
		for (int i=0; i < worldVector.size(); i++) {
			if (worldVector.elementAt(i) instanceof Movable) {
				Movable mObj = (Movable)worldVector.elementAt(i);
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
		
		// Display Flags
		for (int i = 0; i < worldVector.size(); i++) {
	        if (worldVector.elementAt(i) instanceof Flag) {             // check type first
	            System.out.println(worldVector.elementAt(i).toString());
	        }
	    }
		
		// Display Ant
		System.out.println(ant.toString());
		
		// Display Spiders
		for (int i = 0; i < worldVector.size(); i++) {
	        if (worldVector.elementAt(i) instanceof Spider) {             // check type first
	            System.out.println(worldVector.elementAt(i).toString());
	        }
	    }
		
		// Display FoodStations
				for (int i = 0; i < worldVector.size(); i++) {
			        if (worldVector.elementAt(i) instanceof FoodStation) {             // check type first
			            System.out.println(worldVector.elementAt(i).toString());
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
	
	//----------------//
	// Helper methods //
	//----------------//
	
	/**
	 * Re-initializes world without resetting the clock and lives.
	 *  Clears worldVector and sets up again.
	 */
	private void reinit() {
		worldVector.removeAllElements();
		setUpWorld();
	}
	
	/**
	 * Sets up all objects existing in the world.
	 */
	private void setUpWorld() {
		// Object colors
		int flagColor = ColorUtil.rgb(0, 0, 255);
        int antColor = ColorUtil.rgb(255, 100, 0);
        int fsColor = ColorUtil.rgb(0, 255, 0);
        int spiderColor = ColorUtil.rgb(0, 0, 0);
        
        // Set Flags
        worldVector.add(new Flag(10, new Point(200f, 200f), flagColor, 1));
        worldVector.add(new Flag(10, new Point(200f, 800f), flagColor, 2));
        worldVector.add(new Flag(10, new Point(700f, 800f), flagColor, 3));
        worldVector.add(new Flag(10, new Point(900f, 400f), flagColor, 4));
        maxFlagNum = 4;
        
        // Generate Ant (Player) at Flag #1
        Point flag1 = ((Flag) worldVector.elementAt(0)).getLocation();
        int antSize = 40;             
        int antHeading = 0;           
        int antSpeed = 5;             
        int antFoodLevel = 50;             
        int antMaxSpeed = 50;			// Initial max speed
        int antEatRate = 2;               
        ant = new Ant(antSize, flag1, antColor,
                antHeading, antSpeed, antFoodLevel, antMaxSpeed, antEatRate);
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
        float x = WORLD_MIN + rand.nextFloat() * (WORLD_MAX - WORLD_MIN);
        float y = WORLD_MIN + rand.nextFloat() * (WORLD_MAX - WORLD_MIN);
        return new Point(x, y);
    }
	
	/**
	 * Picks a non-empty food station randomly.
	 */
	private FoodStation findNonEmptyFs() {
		Random rand = new Random();
	    Vector<FoodStation> fullFs = new Vector<>();
	    
		for (int i = 0; i < worldVector.size(); i++) {
	        GameObject obj = worldVector.elementAt(i);
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

	
	
}
