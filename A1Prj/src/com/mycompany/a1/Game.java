package com.mycompany.a1;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;

/** 
 * This class defines Game.
 * Acts as the controller.
 * Takes input or requested commands that manipulate data in the game model
 * For this first assignment, it's also responsible for displaying information
 */

public class Game extends Form{
	private GameWorld gw;
	
	public Game() {
		gw = new GameWorld();
		gw.init();
		play();
	}
	
	/**
	 * This method accepts and execute user commands
	 * that operate on the game world.
	 * 
	 * Refer to "Appendix - CN1 Notes" for accepting
	 * keyboard commands via a text field located on
	 * the form
	 */
	private void play() {
		Label myLabel=new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField=new TextField();
		this.addComponent(myTextField);
		this.show();
		
		myTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String sCommand=myTextField.getText().toString();
				myTextField.clear();
				if(sCommand.length() != 0) {
					switch (sCommand.charAt(0)) {
						case 'a':
							gw.accelerate();
							break;
						case 'b':
							gw.brake();
							break;
						case 'l':
							gw.headLeft();
							break;
						case 'r':
							gw.headRight();
							break;
						case 'c':
							gw.changeEatRate();
							break;
						case 'f':
							gw.touchFoodStation();
							break;
						case 'g':
							gw.touchSpider();
							break;
						case 't':
							gw.tickClock();
							break;
						case 'd':
							gw.display();
							break;
						case 'm':
							gw.map();
							break;
						case 'x':
							gw.exitGame();
							break;
						case 'y':
							gw.confirmExit();
							break;
						case 'n':
							gw.cancelExit();
							break;
						case '1':
				            gw.touchFlag(1);
				            break;
				        case '2':
				            gw.touchFlag(2);
				            break;
				        case '3':
				            gw.touchFlag(3);
				            break;
				        case '4':
				            gw.touchFlag(4);
				            break;
				        default:
				        	System.out.println("Invalid command. Please use one of the valid keys: "
				        	            + "a, b, l, r, c, f, g, t, d, m, x, 1-4.");
				        	break;
					} //switch
				}
			} //actionPerformed
		} //new ActionListener()
		); //addActionListener
		
	}

}
