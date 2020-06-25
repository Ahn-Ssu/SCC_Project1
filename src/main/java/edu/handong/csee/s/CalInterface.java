package edu.handong.csee.s;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalInterface extends JFrame {

	private ArrayList<String> buff = new ArrayList<>();
	private ArrayList<String> log = new ArrayList<>();
	private boolean com = false;
	private boolean fnOn = false;

	private JLabel fnMode = new JLabel("Mode Ignore : four-point operation", SwingConstants.RIGHT);
	private JLabel label = new JLabel("0", SwingConstants.RIGHT);
	private MyDialog md;
	
	private final String[] tag = {
			"AC", "+/-", "%", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-", 
			"1", "2", "3", "+", 
			"Fn","0", ".", "=" 
			};
	private JButton button[] = new JButton[tag.length];

	public CalInterface() {
		
		JFrame frame = new JFrame("main");
		frame.setLocation(500, 400);
		frame.setPreferredSize(new Dimension(400, 500));

		Container contentPane = frame.getContentPane();
		GridLayout layout = new GridLayout(5, 4);
		JPanel panel = new JPanel();
		JPanel header = new JPanel(new BorderLayout(2, 1));
		JPanel part = new JPanel(new BorderLayout(1,2));
		fnMode.setFont(new Font("", Font.ITALIC, 15));
		label.setFont(new Font("", Font.BOLD, 50));
		
		md = new MyDialog(this, "Info");
		JButton info = new JButton ("Info");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				md.setVisible(true);
			}
		});
		getContentPane().add(info);
		setSize(40,40);
		setVisible(true);
		part.add(info);
		part.add(fnMode);
		for (int i = 0; i < tag.length; i++) {
			button[i] = new JButton(tag[i]);
			panel.add(button[i]);
			ButtonInput push = new ButtonInput();
			ButtonPress key = new ButtonPress();
			
			button[i].addKeyListener(key);
			button[i].addActionListener(push);
		}
		contentPane.add(panel);
		panel.setLayout(layout);
		header.add(part, BorderLayout.NORTH);
		header.add(label, BorderLayout.CENTER);
//		contentPane.add(label, BorderLayout.NORTH);
//		contentPane.add(fnMode, BorderLayout.SOUTH);
		contentPane.add(header, BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private class MyDialog extends JDialog{
		private JTextArea jt = new JTextArea("<Calculator>\n",13,21);
		private JButton jb = new JButton("OK");
		
		public MyDialog(JFrame f, String t) {
			super(f,t);
			setLayout(new FlowLayout());
			jt.setLineWrap(true);
			jt.append("\n");
			jt.append("Two ways to calculate using the Fn key\n");
			jt.append("Keyboard available\n");
			jt.append("\n");
			jt.append("Key : Function\n");
			jt.append("=, Enter : equal operator\n");
			jt.append("+ : add operator\n");
			jt.append("- : sub operator\n");
			jt.append("* : multiply operator\n");
			jt.append("/ : divide operator\n");
			jt.append("! : pos / neg\n");
			jt.append("% : persent\n");
			jt.append("C : Clear, All Clear\n");
			jt.append("F : Fn key, Change mode\n");
			jt.append("Backspace : Clear\n");
			
			add(jt);
			add(jb);
			setSize(300,350);
			
			jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			});
		}
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

			try {
				if (eventText.equals("Fn") ) {
					if (fnOn) {
						fnMode.setText("Mode Ignore : four-point operation");
						fnOn = !fnOn;
						reset();
					} else {
						fnMode.setText("Mode Keep   : four-point operation");
						fnOn = !fnOn;
						reset();
					}
				}
				
				

				else if (eventText.equals("=")) {
					// if (buff.get(buff.size() - 1).equals("+")
					// || buff.get(buff.size() - 1).equals("-")
					// || buff.get(buff.size() - 1).equals("/")
					// || buff.get(buff.size() - 1).equals("X")) {

					if (com) {
						com = false;
						buff.remove(buff.size() - 1);
					} else {
						buff.add(value);
						log.add(value);
					}
					System.out.println(" in = // buff : " + buff);

					if (buff.size() == 1) {
						label.setText(Util.convertFormat(buff.get(0)));
					} else if (buff.size() == 2) {
						buff.remove(1);
						label.setText(Util.convertFormat(buff.get(0)));
					} else if (buff.size() == 3) {
						Util.ignore(buff);
						label.setText(Util.convertFormat(buff.get(0)));
					}

					else if (buff.size() > 3) {
						if (fnOn) {
							buff.add(eventText);
							Util.keep(buff);
							buff.remove(1);
						} else {
							Util.ignore(buff);
						}

						label.setText(Util.convertFormat(buff.get(0)));
					}
				
				}

				else if (eventText.equals("+") || eventText.equals("-") || eventText.equals("*") || eventText.equals("/")) {
					if (buff.size() == 0) {
						buff.add(value);
						buff.add(eventText);
						log.add(value);
						log.add(eventText);

					} else {
						if (buff.size() % 2 != 0) {
							buff.add(eventText);
						} else if (com) {
							buff.set(buff.size() - 1, eventText);
							log.set(log.size() - 1, eventText);
						} else {
							buff.add(value);
							buff.add(eventText);
							log.add(value);
							log.add(eventText);
							if (buff.size() >= 4) {
								if (fnOn) {
									Util.keep(buff);
									label.setText(Util.convertFormat(buff.get(buff.size() - 2)));
								} else {
									Util.ignore(buff);
									label.setText(Util.convertFormat(buff.get(0)));
								}
							}
						}
					}
					com = true;
					
					if(buff.size()>0)
						button[0].setText("C");
					
				} else {

					
					if (buff.size() > 0) {
						label.setText(Util.convertFormat(buff.get(0)));
						if (com) {
							value = "0";
							com = false;
						}
					}

					if (eventText.equals("C")||eventText.equals("AC")) {
						if(button[0].getText().equals("C")) {
							label.setText("0");
							button[0].setText("AC");
						}
						else if(button[0].getText().equals("AC")) {
							reset();
						}			
							
					}


					else if (eventText.equals("+/-"))
						label.setText(Util.convertFormat(String.valueOf(-1*Double.parseDouble(value))));

					else if (eventText.equals("%")) {
						if (value.equals("0"))
							label.setText("0");
						else
							label.setText(Util.convertFormat(String.valueOf(0.01 * Double.parseDouble(value))));

					} else {
						if (buff.size() % 2 != 0) {
							value = "0";
							reset();
						}

						if (eventText.equals(".")) {
							if (!value.contains(".")) {
								if (value.equals("0")) {
									label.setText("0" + ".");
								} else
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

				}
			}catch(Exception err) {
				
			}

		}// actionPerform

		private void reset() {
			label.setText("0");
			buff.clear();
			log.clear();
		}
	}
	
	private class ButtonPress implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
			String eventText = String.valueOf(e.getKeyChar());
			
			String temp = label.getText();
			String[] t = temp.split(",");
			String value = "";
			for (String part : t) {
				value += part;
			}
			

			
try {
	if (eventText.toLowerCase().equals("f") || eventText.equals("ㄹ")) {
		if (fnOn) {
			fnMode.setText("Mode Ignore : four-point operation");
			fnOn = !fnOn;
			reset();
		} else {
			fnMode.setText("Mode Keep   : four-point operation");
			fnOn = !fnOn;
			reset();
		}
	}
	
	else if(eventText.equals("\b")) {
		label.setText("0");
	}

	else if (eventText.equals("=") || eventText.equals("\n")) {
		// if (buff.get(buff.size() - 1).equals("+")
		// || buff.get(buff.size() - 1).equals("-")
		// || buff.get(buff.size() - 1).equals("/")
		// || buff.get(buff.size() - 1).equals("X")) {

		if (com) {
			com = false;
			buff.remove(buff.size() - 1);
		} else {
			buff.add(value);
			log.add(value);
		}

		if (buff.size() == 1) {
			label.setText(Util.convertFormat(buff.get(0)));
		} else if (buff.size() == 2) {
			buff.remove(1);
			label.setText(Util.convertFormat(buff.get(0)));
		} else if (buff.size() == 3) {
			Util.ignore(buff);
			label.setText(Util.convertFormat(buff.get(0)));
		}

		else if (buff.size() > 3) {
			if (fnOn) {
				buff.add(eventText);
				Util.keep(buff);

				System.out.println(buff);
				Util.ignore(buff);;
			} else {
				Util.ignore(buff);
			}
			System.out.println(buff);
			label.setText(Util.convertFormat(buff.get(0)));
		}

	}

	else if (eventText.equals("+") || eventText.equals("-") || eventText.equals("*") || eventText.equals("/")) {
		if (buff.size() == 0) {
			buff.add(value);
			buff.add(eventText);
			log.add(value);
			log.add(eventText);

		} else {
			if (buff.size() % 2 != 0) {
				buff.add(eventText);
			} else if (com) {
				buff.set(buff.size() - 1, eventText);
				log.set(log.size() - 1, eventText);
			} else {
				buff.add(value);
				buff.add(eventText);
				log.add(value);
				log.add(eventText);
				if (buff.size() >= 4) {
					if (fnOn) {
						Util.keep(buff);
						label.setText(Util.convertFormat(buff.get(buff.size() - 2)));
					} else {
						Util.ignore(buff);
						label.setText(Util.convertFormat(buff.get(0)));
					}
				}
			}
		}
		com = true;
		if(buff.size()>0)
			button[0].setText("C");


		
		
	} else {

		
		if (buff.size() > 0) {
			label.setText(Util.convertFormat(buff.get(0)));
			if (com) {
				value = "0";
				com = false;
			}
		}

		if (eventText.toLowerCase().equals("c") ||eventText.equals("ㅊ")) {
			if(button[0].getText().equals("C")) {
				label.setText("0");
				button[0].setText("AC");
			}
			else if(button[0].getText().equals("AC")) {
				reset();
			}				
		}

		else if (eventText.equals("!"))
			label.setText(Util.convertFormat(String.valueOf(-1 * Double.parseDouble(value))));

		else if (eventText.equals("%")) {
			if (value.equals("0"))
				label.setText("0");
			else
				label.setText(Util.convertFormat(String.valueOf(0.01 * Double.parseDouble(value))));

		} else {
			if (buff.size() % 2 != 0) {
				value = "0";
				reset();
			}

			if (eventText.equals(".")) {
				if (!value.contains(".")) {
					if (value.equals("0")) {
						label.setText("0" + ".");
					} else
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

	}
}catch(Exception err) {
	
}
			

			// }

		}// actionPerform

		private void reset() {
			label.setText("0");
			buff.clear();
			log.clear();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
		
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
