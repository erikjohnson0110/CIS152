package driver;

import javax.swing.JFrame;
import javax.swing.JPanel;

import control.MailControl;
import view.MailPanel;

public class ViewDriver {

	public static void main(String[] args) {
		/*
		MailControl mailControl = new MailControl();
		mailControl.generateNeighborhood();
		mailControl.deliverMail();
		*/
		
		// create a new JFrame object
		JFrame frame = new JFrame();
		
		// create a new TornadoPanel JPanel object
		JPanel panel = new MailPanel();
		// add the panel to the frame
		frame.add(panel);
		
		// set the frame size
		frame.setSize(275, 300);
		// set the frame default close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make the frame visible.
		frame.setVisible(true);
		
	}

}
