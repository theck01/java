
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//add board size
public class THControlPanel extends JPanel implements ActionListener{

	protected JButton new_game_btn;
	protected JButton pause_btn;
	protected THDimSpinners dim;
	
	protected boolean is_paused;
	protected boolean contest;
	
	protected THGameManager game_mngr;
	protected Main window;

	public THControlPanel(THGameManager game_mngr, Main window, boolean contest){
	
		this.contest = contest;
		this.game_mngr = game_mngr;
		this.window = window;
		
		new_game_btn = new JButton("Start");	
		new_game_btn.addActionListener(this);
		
		if(contest){
			pause_btn = new JButton("Pause");
			pause_btn.addActionListener(this);
		}
		
		if(!contest){
			dim = new THDimSpinners(game_mngr);
		}
		
		add(new_game_btn);
		
		if(contest){
			add(pause_btn);
		}
		
		if(!contest){
			add(dim);
		}
		
	
		
		is_paused = false;
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == new_game_btn){
			if(new_game_btn.getText().compareTo("Start") == 0){
				game_mngr.startGame();
				new_game_btn.setText("Main Menu");
			}
			else{
			
				new_game_btn.setText("Start");
				THConstants.setBoardHeight(THConstants.contest_height);
				THConstants.setBoardWidth(THConstants.contest_width);
				if(!contest){
					dim.setValues(THConstants.board_width, THConstants.board_height);
				}
				
				int score = 0;
				if(game_mngr.gameRunning()){
					game_mngr.endGame();
				}
				else{
					score = game_mngr.getScore();
				}
				
				game_mngr.clearGame();
				
				if(contest){
					window.goToHighScore(score);
				}
				else{
					window.goToMenu();
				}
			}
		}
		else{
			if(is_paused){
				pause_btn.setText("Pause");
				is_paused = false;
				game_mngr.resume();
			}
			else{
				pause_btn.setText("Resume");
				is_paused = true;
				game_mngr.pause();
			}
		}
	}
	
	public void gameActiveControls(){
		if(contest){
			pause_btn.setEnabled(true);
		}
	}
	
	public void gameInactiveControls(){
		if(contest){
			pause_btn.setEnabled(false);
		}
	}
}
