
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

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
		
		File file = new File("about.txt");
				
		FileReader in = null;
		try{
			in = new FileReader(file);
		}catch(FileNotFoundException e){
			System.out.println("About file not found, exiting...");
			System.exit(1);
		}
		
		try{
			text.read(in, file.toString());
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
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(text, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		add(return_button, gbc);
	}
	
	public void actionPerformed(ActionEvent e){
		window.goToMenu();
	}
}