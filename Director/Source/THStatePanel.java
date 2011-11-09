
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
	protected JLabel move_label;

	public THStatePanel(){
	
		setLayout(new GridLayout(1,3));
		
		time_label = new JLabel("Time Left -- 0:00");
		time_label.setHorizontalAlignment(JLabel.RIGHT);
		
		score_label = new JLabel("Score -- 0");
		score_label.setHorizontalAlignment(JLabel.LEFT);
		
		JLabel title = new JLabel("DIRECTOR");
		title.setHorizontalAlignment(JLabel.CENTER);
		
		move_label = new JLabel("Valid Moves -- 0");
		move_label.setHorizontalAlignment(JLabel.LEFT);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));
		p.add(score_label);
		p.add(move_label);
	
		add(time_label);
		add(title); //Adds a blank space between Time and Score labels
		add(p);
	}

	public void updateScore(int score){
		score_label.setText("Score: -- " + score);
		repaint();
		return;
	}

	public void updateTime(int remaining_time){
		int minutes = remaining_time/60000;
		int seconds = (remaining_time/1000)%60;
		if(seconds < 10){
			time_label.setText("Time Left -- " + minutes + ":0" + seconds);
		}
		else{
			time_label.setText("Time Left -- " + minutes + ":" + seconds);
		}
		repaint();
		return;
	}
	
	public void updateMoves(int num_moves){
		move_label.setText("Valid Moves -- " + num_moves);
	}
}
