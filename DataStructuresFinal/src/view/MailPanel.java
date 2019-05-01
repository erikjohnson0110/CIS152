package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import control.MailControl;
import domain_components.Block;

/**This class file defines and creates all of the JPanel information used by
 * the JFrame in my ViewDriver.Java file. This panel populates all of the GUI 
 * elements for my project, such as buttons, labels, and the simulation Component.
 * @author Erik E Johnson
 *
 */
@SuppressWarnings("serial")
public class MailPanel extends JPanel {

	private MailControl mc = new MailControl();    // Class to control mail simulation logic
	private MailDraw draw = new MailDraw();        // class with logic to draw neighborhood
	
	// Labels and buttons used in the GUI
	private JLabel headerTextLabel = new JLabel("Welcome to Mail Delivery Simulator");
	private JButton generateBtn = new JButton("Generate Mail");
	private JButton exitBtn = new JButton("Exit");
	private JLabel spacerLabelOne = new JLabel("                                               ");
	private JLabel spacerLabelTwo = new JLabel("                                               ");
	private JButton deliverBtn = new JButton("Deliver Mail");
	private JButton pauseBtn = new JButton("Pause/Continue Delivery");
	private JLabel statusLabel = new JLabel("No Neighborhood Loaded");
	private JLabel deliverCountLabel = new JLabel("Letters Delivered: 0");
	
	private Timer mailTimer = null;      // A timer object used by my simulation.  A listener will listen for events thrown by this timer 
	private final int TIMER_DELAY = 500; // The delay in milliseconds used by the timer.  An event is thrown ever 0.5 seconds
	
	private Block current;               // The current block to be drawn.  Received from the MailControl logic
	private int numDelivered = 0;        // An accumulator that stores the number of letters delivered during the simulation
	
	// constructor for the panel
	public MailPanel() {	                                                          
		add(headerTextLabel);                                      // add all of the labels, text fields, buttons, and art components to the panel
		add(generateBtn);
		add(exitBtn);
		add(spacerLabelOne);
		add(deliverBtn);
		add(pauseBtn);
		add(draw);
		add(statusLabel);
		add(spacerLabelTwo);
		add(deliverCountLabel);
		draw.setPreferredSize(new Dimension(501, 501));            //set the dimensions for the neighborhood drawing component
		
		
		                                                           // instantiate and add action listeners to buttons
		GenerateActionListener gl = new GenerateActionListener();  // listener for the "Generate Neighborhood" button
		generateBtn.addActionListener(gl);
		
		DeliverActionListener dl = new DeliverActionListener();    // listener for the "Deliver Mail" button
		deliverBtn.addActionListener(dl);
		
		ExitActionListener el = new ExitActionListener();          // listener for the "Exit" button
		exitBtn.addActionListener(el);
		
		PauseActionListener pl = new PauseActionListener();        // listener for the "Pause/Continue" button
		pauseBtn.addActionListener(pl);
		
		deliverBtn.setEnabled(false);                              // disable the deliver button initially, cannot be clicked until neighborhood is loaded
		pauseBtn.setEnabled(false);                                // disable pause initially, cannot be clicked until simulation begins
		current = null;                                            // initialize current block to null.  Program logic checks for null values call getBlock method.
	}
	
	// Action Listeners
	// Generate Button
	/**
	 * Event listener for "Generate Neighborhood" button
	 * this contains the logic that is executed when the button is pressed
	 * This button generates and loads a neighborhood into the program
	 *
	 */
	class GenerateActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			generateBtn.setEnabled(false);                      // disable generate button - reenabled once delivery is complete. 
			mc.generateNeighborhood();                          // call the generate method from the control object.
			statusLabel.setText("Neighborhood Loaded");         // set the status lable text so the user knows
			deliverCountLabel.setText("Letters Delivered: 0");  // set deliver count label to zero
			numDelivered = 0;                                   // set accumulator to zero
			deliverBtn.setEnabled(true);                        // now that we're ready, enable the deliver button, and allow user to begin sim
		}
	}
	
	// Deliver Button
	/**
	 * Event listener for the "Deliver Mail" button
	 * This contains the logic that is executed when the button is pressed
	 * This button begins the delivery simulation
	 */
	class DeliverActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			deliverBtn.setEnabled(false);                                // disable deliver button - may only be pressed once, reset when new neighborhood loaded
			pauseBtn.setEnabled(true);                                   // enable the pause button
			statusLabel.setText("Delivery In Progress");                 // set status label to "Delivery In Progress" to notify user
			if (mailTimer == null) {                                     // if the timer is null
				mailTimer = new Timer(TIMER_DELAY, new TimerAction());   // create a new timer object - prevents null pointer exceptions
			}
			mailTimer.start();                                           // start the timer
		}
		
	}
	
	// Pause button
	/**
	 * Event listener for the "Pause/Continue" button
	 * This contains the logic that is executed when the button is pressed
	 * This button pauses and unpauses the simulation
	 *
	 */
	class PauseActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (mailTimer != null) 
			{                                                             // ensure mail timer is not null - shouldn't be at this point, but a safeguard
				if (mailTimer.isRunning()) {                              // if the mail timer is running
					mailTimer.stop();                                     // stop it
					statusLabel.setText("Delivery Paused");               // set status label to display pause
				}
				else {                                                    // if the mail timer is NOT running
					mailTimer.start();                                    // start it
					statusLabel.setText("Delivery In Progress");          // set status label to display in progress
				}
			}
		}
		
	}
	
	// Exit Button
	/**
	 * Event listener for the "Exit" button
	 * This contains the logic that is executed when the button is pressed
	 * This button exits the program
	 *
	 */
	class ExitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);                                              // exit the program
		}
	}
	
	// Timer action
	/**
	 * Event listener that listens for timer events
	 * This contains the logic that is executed when the sim timer
	 * throws a timer event.
	 * The timer executes the delivery of one address and updates
	 * the draw component
	 *
	 */
	class TimerAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (current != null && !current.isDone) {                            // if current block is not null and not done delivering
				current.deliverNext();                                           // call block's deliverNext method
				numDelivered += current.getLastNumDelivered();                   // increment number of letters delivered counter by # of letters for current adr
				deliverCountLabel.setText("Letters Delivered: " + numDelivered); // update display label
				draw.setBlockGraphic(current);                                   // send current block data to draw component for rendering
			}
			else {                                                               // if current block is NULL, or done Delivering
				current = mc.getNextBlock();                                     // call controller's get next block method
				if (current != null) {                                           // if not null, begin delivering new block
					draw.setBlockGraphic(current);                               // send block data to the draw component
				}
				else                                                             // if still null, there are no more blocks in the controller.
				{
					statusLabel.setText("Delivery Complete.  Generate New Neighborhood to continue.");  // set status to done
					deliverBtn.setEnabled(false);                                // disable deliver button
					pauseBtn.setEnabled(false);                                  // disable pause button
					generateBtn.setEnabled(true);                                // enable generate new neighborhood button
					mailTimer.stop();                                            // stop the timer
				}
			}
			draw.repaint();                                                      // repaint component with new data.
		}
		
	}
}
