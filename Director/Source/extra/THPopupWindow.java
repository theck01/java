
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class THPopupWindow extends JFrame implements ActionListener, ItemListener{

	protected THDimSpinners dim;
	protected JComboBox time;
	protected int board_width;
	protected int board_height;
	protected int game_duration;
	protected THGameManager game;
	protected boolean comp;
	
	//Constants
	protected static int comp_height = 8;
	protected static int comp_width = 8;
	protected static int comp_duration = 60000;
	
	public THPopupWindow(THGameManager game){
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		//creation of mode selector panel
		
		JPanel mode_panel = new JPanel();
		JLabel mode_label = new JLabel("Mode:");
		mode_label.setHorizontalAlignment(JLabel.CENTER);
		mode_panel.add(mode_label);
		
		ButtonGroup g = new ButtonGroup();
		
		JCheckBox mode = new JCheckBox("Competitive", true);
		g.add(mode);
		mode.addItemListener(this);
		mode_panel.add(mode);
		
		mode = new JCheckBox("Free Play", false);
		g.add(mode);
		mode.addItemListener(this);
		mode_panel.add(mode);
		
		content.add(mode_panel, BorderLayout.NORTH);
		
		
		//creation and addition of free play controls
		
		dim = new THDimSpinners(this);
		dim.setEnabled(false);
		content.add(dim, BorderLayout.CENTER);
		
		JPanel time_panel = new JPanel();
		time_panel.setLayout(new GridLayout(2,1));
		JLabel time_label = new JLabel("Game Time:");
		time_panel.add(time_label);
		
		time = new JComboBox();
		time.addItem(new String("1 min"));
		time.addItem(new String("2 min"));
		time.addItem(new String("5 min"));
		time.addItem(new String("Infinite"));
		time.addActionListener(this);
		time.setSelectedIndex(0);
		time.setEnabled(false);
		
		time_panel.add(time);
		
		content.add(time_panel, BorderLayout.CENTER);
		
		//creation and addition of start button
		
		JButton start = new JButton("Start Game");
		start.addActionListener(this);
		content.add(start);
		
		board_width = THConstants.board_width;
		board_height = THConstants.board_height;
		game_duration = THConstants.game_duration;
		comp = true;
	}
	
	public void updateBoardDimensions(int width, int height){
		width = board_width;
		height = board_height;
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() != time){
			if(comp){
				THConstants.setBoardHeight(comp_height);
				THConstants.setBoardWidth(comp_width);
				THConstants.setGameDuration(comp_duration);
			}
			else{
				THConstants.setBoardHeight(board_height);
				THConstants.setBoardWidth(board_width);
				THConstants.setGameDuration(game_duration);
			}
			
			game.startGame();
			setVisible(false);
		}
		else{
			String new_time = (String)((JComboBox)e.getSource()).getSelectedItem();
			
			if(new_time.compareTo("1 min") == 0){
				game_duration = 60000;
			}
			else if(new_time.compareTo("2 min") == 0){
				game_duration = 120000;
			}
			else if(new_time.compareTo("5 min") == 0){
				game_duration = 300000;
			}
			else{
				game_duration = -1;
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e){
		if(e.getStateChange() == ItemEvent.SELECTED){
			
			String selected = ((JCheckBox)e.getItem()).getText();
			
			if(selected.compareTo("Competitive") == 0){
				comp = true;
				dim.setEnabled(false);
				time.setEnabled(false);
			}
			else{
				comp = false;
				dim.setEnabled(true);
				dim.setEnabled(true);
			}
		}
	}
}







