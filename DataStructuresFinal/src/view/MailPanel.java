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

@SuppressWarnings("serial")
public class MailPanel extends JPanel {
	
	// Class to control mail logic
	private MailControl mc = new MailControl();
	private Block current;
	// class to draw component
	private MailDraw draw = new MailDraw();
	
	private JLabel headerTextLabel = new JLabel("Welcome to Mail Delivery Simulator");
	private JButton generateBtn = new JButton("Generate Mail");
	private JButton exitBtn = new JButton("Exit");
	private JLabel spacerLabelOne = new JLabel("                                               ");
	private JLabel spacerLabelTwo = new JLabel("                                               ");
	private JButton deliverBtn = new JButton("Deliver Mail");
	private JButton pauseBtn = new JButton("Pause/Continue Delivery");
	private JLabel statusLabel = new JLabel("No Neighborhood Loaded");
	private JLabel deliverCountLabel = new JLabel("Letters Delivered: 0");
	
	private Timer mailTimer = null;
	private final int TIMER_DELAY = 500;
	
	private int numDelivered = 0;
	
	public MailPanel() {
		// add all of the labels, textfields, buttons, and art components
		add(headerTextLabel);
		add(generateBtn);
		add(exitBtn);
		add(spacerLabelOne);
		add(deliverBtn);
		add(pauseBtn);
		add(draw);
		add(statusLabel);
		add(spacerLabelTwo);
		add(deliverCountLabel);
		draw.setPreferredSize(new Dimension(501, 501));
		
		
		// create and add action listeners to buttons
		GenerateActionListener gl = new GenerateActionListener();
		generateBtn.addActionListener(gl);
		
		DeliverActionListener dl = new DeliverActionListener();
		deliverBtn.addActionListener(dl);
		
		ExitActionListener el = new ExitActionListener();
		exitBtn.addActionListener(el);
		
		PauseActionListener pl = new PauseActionListener();
		pauseBtn.addActionListener(pl);
		
		deliverBtn.setEnabled(false);
		pauseBtn.setEnabled(false);
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
			statusLabel.setText("Neighborhood Loaded");
			deliverCountLabel.setText("Letters Delivered: 0");
			numDelivered = 0;
			deliverBtn.setEnabled(true);
			
		}
		
	}
	
	// Deliver Button
	class DeliverActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			deliverBtn.setEnabled(false);
			pauseBtn.setEnabled(true);
			statusLabel.setText("Delivery In Progress");
			if (mailTimer == null) {
				mailTimer = new Timer(TIMER_DELAY, new TimerAction());
			}
			mailTimer.start();
		}
		
	}
	
	class PauseActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (mailTimer.isRunning()) {
				mailTimer.stop();
				statusLabel.setText("Delivery Paused");
			}
			else {
				mailTimer.start();
				statusLabel.setText("Delivery In Progress");
			}
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
	
	class TimerAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (current != null && !current.isDone) {
				current.deliverNext();
				numDelivered += current.getLastNumDelivered();
				deliverCountLabel.setText("Letters Delivered: " + numDelivered);
				draw.setBlockGraphic(current);
			}
			else {
				current = mc.getNextBlock();
				if (current != null) {
					draw.setBlockGraphic(current);
				}
				else 
				{
					statusLabel.setText("Delivery Complete.  Generate New Neighborhood to continue.");
					deliverBtn.setEnabled(false);
					pauseBtn.setEnabled(false);
					generateBtn.setEnabled(true);
					mailTimer.stop();
				}
			}
			draw.repaint();
		}
		
	}
}
