
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
		time_label = new Label("Time Left -- 00:00");
		score_label = new Label("Score -- 0");
		new_game_btn = new JButton("New Game")
		add(title);
		add(score_label);
		new_game_btn.addActionListener(this)
	}

	public void updateScore(int score){
		score_label.setText("Score: -- " + Integer.toString(score));
		return;
	}

	public void updateTime(int remaining_time){
		time_label.setText("Time Left -- " + remaining_time/60 + ":" + remaining_time%60);
	}

	public void setGameManager(THGameManager game){
		this.game = game;
	}
	
	public void actionPerformed(ActionEvent e){
		//game.startGame();
		System.out.println("Would be starting a new game...");
	}
}
