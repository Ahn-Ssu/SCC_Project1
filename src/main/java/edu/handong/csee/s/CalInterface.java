package edu.handong.csee.s;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CalInterface extends JFrame {

	
	public CalInterface() {
		JFrame frame = new JFrame("main");
		frame.setLocation(500, 400);
		frame.setPreferredSize(new Dimension(400,500));
		
		Container contentPane = frame.getContentPane();
		GridLayout layout = new GridLayout(5,4);
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("0", SwingConstants.RIGHT);
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
		}
		
		contentPane.add(panel);
		panel.setLayout(layout);
		contentPane.add(label, BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
