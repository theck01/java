
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

//Class creates the main menu that is visible to the user at the beginning of the
//game
public class THMenuView extends JPanel implements ActionListener{

	protected Main window;
	protected JButton freeplay;
	protected JButton contest;
	protected JButton about;
	
	public THMenuView(Main window){
	
		this.window = window;
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		
		contest = new JButton("Contest");
		contest.addActionListener(this);
		freeplay = new JButton("Free Play");
		freeplay.addActionListener(this);
		about = new JButton("About");
		about.addActionListener(this);
		
		JLabel title = new JLabel("DIRECTOR");
		JLabel empty = new JLabel("");
		
		//addition to the layout
		gbc.gridx = 5;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		add(empty, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(title, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(contest, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(freeplay, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(about, gbc);		
	}
	
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == about){
			window.goToAbout();
		}
		else if(e.getSource() == freeplay){
			window.goToFreePlay();
		}
	}
}