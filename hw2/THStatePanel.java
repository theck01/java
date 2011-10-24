
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Class provides the score panel for the game
//A panel is used so that other elements could be added to the board
public class THStatePanel extends JPanel implements ActionListener{
	
	JLabel time_label;
	JLabel score_label;
	JButton new_game_btn;
	THGameManager game;

	public THStatePanel(){
		setLayout(new FlowLayout());
		time_label = new JLabel("Time Left -- 00:00");
		score_label = new JLabel("Score -- 0");
		new_game_btn = new JButton("New Game");
		add(time_label);
		add(score_label);
		add(new_game_btn);
		new_game_btn.addActionListener(this);
	}

	public void updateScore(int score){
		score_label.setText("Score: -- " + Integer.toString(score));
		repaint();
		return;
	}

	public void updateTime(int remaining_time){
		int minutes = remaining_time/60;
		int seconds = remaining_time%60;
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
	
	public void actionPerformed(ActionEvent e){
		game.startGame();
		System.out.println("Would be starting a new game... but sadly its not ready");
	}
}
