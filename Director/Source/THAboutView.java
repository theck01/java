
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class THAboutView extends JPanel implements ActionListener{

	Main window;
	
	public THAboutView(Main window){
	
		this.window = window;
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setOpaque(false);
		text.setEditable(false);
		
		URL file = URLClassLoader.getSystemResource("about.txt");
		InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("about.txt"));

		try{
			text.read(in, file);
		}
		catch(IOException e){
			System.out.println("About file corrupted, exiting...");
			System.exit(1);
		}
		
		
		JButton return_button = new JButton("Return");
		return_button.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0.6;
		gbc.insets = new Insets(0,50,0,50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(text, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(return_button, gbc);
	}
	
	public void actionPerformed(ActionEvent e){
		window.goToMenu();
	}
}