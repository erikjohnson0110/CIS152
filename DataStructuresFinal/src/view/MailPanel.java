package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MailControl;

public class MailPanel extends JPanel {
	
	// Class to control mail logic
	private MailControl mc = new MailControl();
	// class to draw component
	
	private JLabel headerTextLabel = new JLabel("Welcome to Mail Delivery Simulator");
	private JButton generateBtn = new JButton("Generate Mail");
	private JButton deliverBtn = new JButton("Deliver Mail");
	private JButton exitBtn = new JButton("Exit");
	
	public MailPanel() {
		// add all of the labels, textfields, buttons, and art components
		add(headerTextLabel);
		add(generateBtn);
		add(deliverBtn);
		add(exitBtn);
		
		//tm.setPreferredSize(new Dimension(225, 110)); // set preferred size for component.
		//add(errorLabel);
		
		// create and add action listeners to buttons
		GenerateActionListener gl = new GenerateActionListener();
		generateBtn.addActionListener(gl);
		
		DeliverActionListener dl = new DeliverActionListener();
		deliverBtn.addActionListener(dl);
		
		ExitActionListener el = new ExitActionListener();
		exitBtn.addActionListener(el);
	}
	
	// Action Listeners
	// Generate Button
	class GenerateActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			generateBtn.setEnabled(false);
			mc.generateNeighborhood();
			generateBtn.setEnabled(true);
		}
		
	}
	
	// Deliver Button
	class DeliverActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			deliverBtn.setEnabled(false);
			mc.deliverMail();
			deliverBtn.setEnabled(true);
		}
		
	}
	
	// Exit Button
	class ExitActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		
	}
}
