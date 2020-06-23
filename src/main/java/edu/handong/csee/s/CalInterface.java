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
	JLabel label = new JLabel("0", SwingConstants.RIGHT);
	Double temp ;
	boolean add=false;
	boolean sub=false;
	boolean mul=false;
	boolean dim=false;
	boolean next=false;
	
	public CalInterface() {
		JFrame frame = new JFrame("main");
		frame.setLocation(500, 400);
		frame.setPreferredSize(new Dimension(400,500));
		
		Container contentPane = frame.getContentPane();
		GridLayout layout = new GridLayout(5,4);
		JPanel panel = new JPanel();
		
		
		label.setFont(new Font("",Font.BOLD, 50));
		final String[] tag = {
				"C", "+/-", "%", "/",
				"7", "8", "9", "X",
				"4", "5", "6", "-",
				"1", "2", "3", "+",
				"Fn","0", ".", "="
		};
		JButton button[] = new JButton[tag.length]; 
		
		for(int i=0; i<tag.length;i++) {
			button[i] = new JButton(tag[i]);
			panel.add(button[i]);
			ButtonInput push = new ButtonInput();
			button[i].addActionListener(push);
		}
		
		contentPane.add(panel);
		panel.setLayout(layout);
		contentPane.add(label, BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	private class ButtonInput implements ActionListener {

		
		
		@Override
		public void actionPerformed(ActionEvent e) {
	
			String eventText =e.getActionCommand();
		
			
			
			String temp = label.getText();
			String[] t = temp.split(",");
			String value = "" ;
			for(String part:t) {
				value += part;
			}
			

			if(eventText.equals("+")){ 
				buff.add(value);
				buff.add(eventText);
			}
			if(eventText.equals("-")){ 
	
			}
			if(eventText.equals("/")){ 

			}
			if(eventText.equals("X")){ 

			}
			
			
			
			if(eventText.equals("C"))
				label.setText("0");
	
			else if(eventText.equals("+/-"))
				label.setText(Util.convertFormat(String.valueOf(-1*Double.parseDouble(value))));
			
			else if(eventText.equals("%"))
				label.setText(Util.convertFormat(String.valueOf(0.01*Double.parseDouble(value))));
			
			else if(eventText.equals(".")) {
				if(!value.contains("."))
				label.setText(label.getText()+".");
			}
			
			
			else if(Double.parseDouble(eventText)==0) {
				if(!value.contains("."))
					label.setText(Util.convertFormat((Util.convertOutputNumber(value,eventText))));
				else
					label.setText(label.getText()+"0");
			}
			
			else if(Double.parseDouble(eventText)>0 && Double.parseDouble(eventText)<10)
				label.setText(Util.convertFormat((Util.convertOutputNumber(value,eventText))));
			


		
			
			
	
			
		}//actionPerform

	}


}

