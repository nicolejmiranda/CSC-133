package com.mycompany.a2;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.Toolbar;

/** 
 * This class defines Game.
 * Acts as the controller.
 * Takes input or requested commands that manipulate data in the game model
 * For this first assignment, it's also responsible for displaying information
 */
public class Game extends Form {
	private GameWorld gw;
    private MapView mv;
    private ScoreView sv;
    private Toolbar myToolbar;

    public Game() {
        gw = new GameWorld();
        mv = new MapView();
        sv = new ScoreView();

        // register observers
        gw.addObserver(mv);
        gw.addObserver(sv);
        
        // add key bindings
        addKeyBindings();
        
        // set up layout manager
        setLayout(new BorderLayout());
        
        // build center
        add(BorderLayout.CENTER, buildCenterRegion());

        // add Score view
        add(BorderLayout.NORTH, buildNorthRegion());
        
        // toolbar
        buildToolbar();
        
        // add title
        setTitle("Ant-Path Game");

        // build button panels
        add(BorderLayout.WEST, buildWestRegion());
        add(BorderLayout.EAST, buildEastRegion());
        add(BorderLayout.SOUTH, buildSouthRegion());
         
        // display the form
        this.show();
        
        // query MapView size
        int mapWidth  = mv.getWidth();
        int mapHeight = mv.getHeight();
        gw.setMapSize(mapWidth, mapHeight);		// assign to World
        
        gw.init();								// initialize world
    }
    
    //----------------//
  	// Helper methods //
  	//----------------//
    
    private Container buildCenterRegion() {
        Container centerBox = new Container(new BorderLayout());
        centerBox.getAllStyles().setBgColor(ColorUtil.WHITE);
        centerBox.getAllStyles().setBgTransparency(255);
        centerBox.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.rgb(255,0,0)));
        centerBox.add(BorderLayout.CENTER, mv);
        return centerBox;
    }
    
    private Container buildNorthRegion() {
    	Container north = new Container(new BorderLayout());
        north.add(BorderLayout.NORTH, sv);
        for (Component c : sv) {
            c.getAllStyles().setFgColor(ColorUtil.BLUE);
        }

        return north;
    }
        

    private Container buildWestRegion() {
        Container left = new Container(BoxLayout.y());
        left.getAllStyles().setPadding(Component.TOP, 100);
        left.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
        
        // Create buttons
        Button accelerateBtn = new Button("Accelerate");
        Button leftBtn = new Button("Left");
       
        // Set a common style for all buttons
        for (Button b : new Button[]{accelerateBtn, leftBtn}) {
            b.getAllStyles().setBgColor(ColorUtil.BLUE);
            b.getAllStyles().setFgColor(ColorUtil.WHITE);
            b.getAllStyles().setBgTransparency(255);
            b.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
            b.getAllStyles().setPadding(Component.TOP, 5);
            b.getAllStyles().setPadding(Component.BOTTOM, 5);
        }

        // assign commands
        accelerateBtn.setCommand(new AccelerateCommand(gw));
        leftBtn.setCommand(new LeftTurnCommand(gw));

        left.addAll(accelerateBtn, leftBtn);
        return left;
    }

    private Container buildEastRegion() {
        Container right = new Container(BoxLayout.y());
        right.getAllStyles().setPadding(Component.TOP, 100);
        right.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
        
        // Create buttons
        Button brakeBtn = new Button("Brake");
        Button rightBtn = new Button("Right");
        // Set a common style for all buttons
        for (Button b : new Button[]{brakeBtn, rightBtn}) {
            b.getAllStyles().setBgColor(ColorUtil.BLUE);
            b.getAllStyles().setFgColor(ColorUtil.WHITE);
            b.getAllStyles().setBgTransparency(255);
            b.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
            b.getAllStyles().setPadding(Component.TOP, 5);
            b.getAllStyles().setPadding(Component.BOTTOM, 5);
        }
        
        // assign commands
        brakeBtn.setCommand(new BrakeCommand(gw));
        rightBtn.setCommand(new RightTurnCommand(gw));

        right.addAll(brakeBtn, rightBtn);
        return right;
    }

    private Container buildSouthRegion() {
    	Container buttonRow = new Container(BoxLayout.x());
	    Button collideFlagBtn = new Button("Collide with Flag");
	    Button collideSpiderBtn = new Button("Collide Spider");
	    Button eatFoodBtn = new Button("Collide with Food Stations");
	    Button tickBtn = new Button("Tick");

	    for (Button b : new Button[]{collideFlagBtn, collideSpiderBtn, eatFoodBtn, tickBtn}) {
	        b.getAllStyles().setBgColor(ColorUtil.BLUE);
	        b.getAllStyles().setFgColor(ColorUtil.WHITE);
	        b.getAllStyles().setBgTransparency(255);
	        b.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
	        b.getAllStyles().setPadding(Component.TOP, 5);
            b.getAllStyles().setPadding(Component.BOTTOM, 5);
	    }
	    
	    // assign commands
        collideFlagBtn.setCommand(new CollideFlagCommand(gw));
        collideSpiderBtn.setCommand(new CollideSpiderCommand(gw));
        eatFoodBtn.setCommand(new CollideFsCommand(gw));
        tickBtn.setCommand(new TickCommand(gw));

	    buttonRow.addAll(collideFlagBtn, collideSpiderBtn, eatFoodBtn, tickBtn);

	    //wrap the row in a FlowLayout centered container
	    Container centered = new Container(new FlowLayout(Component.CENTER));
	    centered.add(buttonRow);

	    return centered;
    }
    
    private Toolbar buildToolbar() {
    	myToolbar = new Toolbar();
        setToolbar(myToolbar);
        myToolbar.addCommandToSideMenu(new AccelerateCommand(gw));
        myToolbar.addCommandToSideMenu(new AboutCommand());
        myToolbar.addCommandToSideMenu(new ExitCommand());
        myToolbar.addCommandToRightBar("Help", null, e -> Dialog.show("Help",
                "Use buttons or keys:\nA-Accelerate, B-Brake, L-Left, R-Right,"
                + " C-Change Eat Rate, F-Eat Food, G-Collide Spider, T-Tick.", "OK", null));
        
        // Create Checkbox for Sound toggle
    	CheckBox soundCheck = new CheckBox("Sound");
    	soundCheck.setSelected(gw.isSoundOn()); // initialize to current GameWorld state
    	soundCheck.getAllStyles().setBgColor(ColorUtil.LTGRAY);
    	soundCheck.getAllStyles().setBgTransparency(255);
    	soundCheck.getAllStyles().setFgColor(ColorUtil.WHITE);
        myToolbar.addComponentToSideMenu(soundCheck);
     
        // Link the checkbox to a new ToggleSoundCommand
        SoundToggleCommand soundCmd = new SoundToggleCommand(gw);
        soundCheck.setCommand(soundCmd);
		return myToolbar;
    }
    
    private void addKeyBindings() {
    	addKeyListener('a', new AccelerateCommand(gw));
        addKeyListener('b', new BrakeCommand(gw));
        addKeyListener('l', new LeftTurnCommand(gw));
        addKeyListener('r', new RightTurnCommand(gw));
        addKeyListener('c', new SetFoodConsumptionCommand(gw));
        addKeyListener('f', new CollideFsCommand(gw));
        addKeyListener('g', new CollideSpiderCommand(gw));
        addKeyListener('t', new TickCommand(gw));
    }

}