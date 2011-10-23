
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Class provides the score panel for the game
//A panel is used so that other elements could be added to the board
public class THScoreBoard extends JPanel{

	int score;
	JLabel title;
	JLabel score_label;

	public THScoreBoard(){
		setLayout(new FlowLayout());
		title = new Label("Score: ");
		score = 0;
		score_label = new Label("0");
		add(title);
		add(score_label);
	}

	public updateScore(int points){
		score += points;
		score_label.setText(Integer.toString(score));
	}

	
}
