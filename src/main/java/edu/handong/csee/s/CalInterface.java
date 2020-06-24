package edu.handong.csee.s;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CalInterface extends JFrame {

	ArrayList<String> buff = new ArrayList<>();
	ArrayList<String> log = new ArrayList<>();
	boolean com = false;
	boolean fnOn = false;

	JLabel fnMode = new JLabel("Mode Ignore : four-point operation", SwingConstants.RIGHT);
	JLabel label = new JLabel("0", SwingConstants.RIGHT);
	
	
	public CalInterface() {
		JFrame frame = new JFrame("main");
		frame.setLocation(500, 400);
		frame.setPreferredSize(new Dimension(400, 500));

		Container contentPane = frame.getContentPane();
		GridLayout layout = new GridLayout(5, 4);
		JPanel panel = new JPanel();
		JPanel header = new JPanel(new BorderLayout(2,1));

		fnMode.setFont(new Font("", Font.ITALIC, 15));
		label.setFont(new Font("", Font.BOLD, 50));
		final String[] tag = { "AC", "+/-", "%", "/", "7", "8", "9", "X", "4", "5", "6", "-", "1", "2", "3", "+", "Fn",
				"0", ".", "=" };
		JButton button[] = new JButton[tag.length];

		for (int i = 0; i < tag.length; i++) {
			button[i] = new JButton(tag[i]);
			panel.add(button[i]);
			ButtonInput push = new ButtonInput();

			button[i].addActionListener(push);
		}
		contentPane.add(panel);
		panel.setLayout(layout);
		header.add(fnMode, BorderLayout.NORTH);
		header.add(label, BorderLayout.CENTER);
//		contentPane.add(label, BorderLayout.NORTH);
//		contentPane.add(fnMode, BorderLayout.SOUTH);
		contentPane.add(header, BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private class ButtonInput implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String eventText = e.getActionCommand();

			String temp = label.getText();
			String[] t = temp.split(",");
			String value = "";
			for (String part : t) {
				value += part;
			}

			if(eventText.equals("Fn")) {
				if(fnOn) {
					fnMode.setText("Mode Ignore : four-point operation");
					fnOn = !fnOn;
					reset();
				}
				else {
					fnMode.setText("Mode Keep   : four-point operation");
					fnOn = !fnOn;
					reset();
				}
				
			}
				
			
			System.out.println(fnOn);
			
			
			if (eventText.equals("=")) {
			 if(Double.parseDouble(value) == Double.parseDouble(buff.get(0))) {
				 label.setText(buff.get(0));
			 }
				 else {
					 buff.add(value);
					 Util.ignore(buff);
					 label.setText(Util.convertFormat(buff.get(0)));
					 buff.remove(1);
				 }
			 }


			if (eventText.equals("+") || eventText.equals("-") || eventText.equals("X") || eventText.equals("/")) {
				// label.setText("0");
				if (buff.size() == 0) {
					buff.add(value);
					buff.add(eventText);
					log.add(value);
					log.add(eventText);
					
				}

				else {
					/*
					 * if(buff.get(buff.size()-1).equals("+") ||buff.get(buff.size()-1).equals("-")
					 * ||buff.get(buff.size()-1).equals("/") ||buff.get(buff.size()-1).equals("X"))
					 * buff.set(buff.size()-1, eventText); else {
					 */
					if(buff.size()%2 !=0) {
						buff.add(eventText);
					}
				else if (com) {
						buff.set(buff.size() - 1, eventText);
						log.set(log.size() - 1, eventText);
					}
					else {
						buff.add(value);
						buff.add(eventText);
						log.add(value);
						log.add(eventText);
						if (buff.size() >= 4) {
							// 사칙연산 무시
							if(fnOn){
								Util.keep(buff);
								label.setText(Util.convertFormat(buff.get(buff.size()-2)));
							}
							else {
								Util.ignore(buff);
								label.setText(Util.convertFormat(buff.get(0)));
							}
							
						}
					}

				}
				com = true;
				System.out.println(buff);
				System.out.println("log : " + log);
				
			} else {

				if (buff.size() > 0) {
					label.setText(Util.convertFormat(buff.get(0)));
					if (com) {
						value = "0";
						com = false;
					}
				}
				if(buff.size()%2 !=0) {
					value = "0";
					reset();	
				}

				if (eventText.equals("AC")) {
					reset();
				}

				else if (eventText.equals("+/-"))
					label.setText(Util.convertFormat(String.valueOf(-1 * Double.parseDouble(value))));

				else if (eventText.equals("%"))
					label.setText(Util.convertFormat(String.valueOf(0.01 * Double.parseDouble(value))));

				else if (eventText.equals(".")) {
					if (!value.contains(".")) {
						if(value.equals("0")) {
							label.setText(value  + ".");
						}
						else
						label.setText(Util.convertFormat((Util.convertOutputNumber(value, eventText))) + ".");
					}
						
				}

				else if (Double.parseDouble(eventText) == 0) {
					if (!value.contains("."))
						label.setText(Util.convertFormat((Util.convertOutputNumber(value, eventText))));
					else
						label.setText(((Util.convertOutputNumber(value, eventText))));
				}

				else if (Double.parseDouble(eventText) > 0 && Double.parseDouble(eventText) < 10) {
					label.setText(Util.convertFormat((Util.convertOutputNumber(value, eventText))));
				}
			}

			// }

		}// actionPerform
		
		private void reset() {
			label.setText("0");
			buff.removeAll(buff);
			log.removeAll(log);
		}
	}

}