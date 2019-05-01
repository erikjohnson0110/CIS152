package driver;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.MailPanel;

/**This is the driver file for my Mail Carrier simulation.  It contains only
 * the main method and basic Java Swing frame loading functions.
 * 
 * Program Summary:  This program is a simulator that simulates carrying mail
 * for a neighborhood.  The simulation is used to demonstrate the use of 
 * data structures in a program in an interesting way.
 * 
 * This program is my own original work, and is my CIS152 - Data Structures
 * Final project for Spring semester 2019
 * @author Erik E Johnson
 *
 */
public class ViewDriver {

	public static void main(String[] args) {
		JFrame frame = new JFrame();                          // create a new JFrame object - this is the main window
		JPanel panel = new MailPanel();                       // create a new MailPanel object - this panel contains the buttons and labels
		frame.add(panel);                                     // add the panel to the frame
		frame.setSize(600, 700);                              // set the frame size
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the frame default close
		frame.setVisible(true);                               // make the frame visible.
		frame.setResizable(false);                            // disable resize
	}

}
