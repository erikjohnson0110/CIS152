package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MailControl;
import domain_components.Block;

public class MailPanel extends JPanel {
	
	// Class to control mail logic
	private MailControl mc = new MailControl();
	private Block current;
	// class to draw component
	private MailDraw draw = new MailDraw();
	
	private JLabel headerTextLabel = new JLabel("Welcome to Mail Delivery Simulator");
	private JButton generateBtn = new JButton("Generate Mail");
	private JButton exitBtn = new JButton("Exit");
	private JLabel spacerLabel = new JLabel("                                               ");
	private JButton deliverBtn = new JButton("Deliver Mail");
	
	public MailPanel() {
		// add all of the labels, textfields, buttons, and art components
		add(headerTextLabel);
		add(generateBtn);
		add(exitBtn);
		add(spacerLabel);
		add(deliverBtn);
		add(draw);
		draw.setPreferredSize(new Dimension(501, 501));
		
		
		// create and add action listeners to buttons
		GenerateActionListener gl = new GenerateActionListener();
		generateBtn.addActionListener(gl);
		
		DeliverActionListener dl = new DeliverActionListener();
		deliverBtn.addActionListener(dl);
		
		ExitActionListener el = new ExitActionListener();
		exitBtn.addActionListener(el);
		
		deliverBtn.setEnabled(false);
		current = null;
	}
	
	// Action Listeners
	// Generate Button
	class GenerateActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			generateBtn.setEnabled(false);
			mc.generateNeighborhood();
			deliverBtn.setEnabled(true);
			
		}
		
	}
	
	// Deliver Button
	class DeliverActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//deliverBtn.setEnabled(false);
			//mc.deliverMail();
			//generateBtn.setEnabled(true);
			
			if (current != null && !current.isDone) {
				current.deliverNext();
				draw.setBlockGraphic(current);
			}
			else {
				current = mc.getNextBlock();
				if (current != null) {
					draw.setBlockGraphic(current);
				}
				else 
				{
					deliverBtn.setEnabled(false);
					generateBtn.setEnabled(true);
				}
			}
			draw.repaint();
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
