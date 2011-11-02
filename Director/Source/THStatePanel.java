
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Class provides the score panel for the game
//A panel is used so that other elements could be added to the board
public class THStatePanel extends JPanel{
	
	protected JLabel time_label;
	protected JLabel score_label;
	protected THGameManager game;

	public THStatePanel(){
		setLayout(new FlowLayout());
		time_label = new JLabel("Time Left -- 00:00");
		score_label = new JLabel("Score -- 0");
		add(time_label);
		add(score_label);
	}

	public void updateScore(int score){
		score_label.setText("Score: -- " + Integer.toString(score));
		repaint();
		return;
	}

	public void updateTime(int remaining_time){
		int minutes = remaining_time/60000;
		int seconds = (remaining_time/1000)%60;
		if(seconds < 10){
			time_label.setText("Time Left -- 0" + minutes + ":0" + seconds);
		}
		else{
			time_label.setText("Time Left -- 0" + minutes + ":" + seconds);
		}
		repaint();
		return;
	}

	public void setGameManager(THGameManager game){
		this.game = game;
	}
}
