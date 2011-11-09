
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//add board size
public class THControlPanel extends JPanel implements ActionListener{

	protected JButton new_game_btn;
	protected JButton pause_btn;
	protected THDimSpinners dim;
	protected boolean is_paused;
	protected THGameManager game_mngr;

	public THControlPanel(THGameManager game_mngr){
	
		this.game_mngr = game_mngr;
		
		new_game_btn = new JButton("New Game");
		pause_btn = new JButton("Pause");
		dim = new THDimSpinners(game_mngr);
		
		add(new_game_btn);
		add(pause_btn);
		add(dim);
		
		new_game_btn.addActionListener(this);
		pause_btn.addActionListener(this);
		
		is_paused = false;
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == new_game_btn){
			if(!is_paused){
				game_mngr.startGame();
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
		new_game_btn.setEnabled(false);
		dim.setEnabled(false);
		pause_btn.setEnabled(true);
	}
	
	public void gameInactiveControls(){
		new_game_btn.setEnabled(true);
		dim.setEnabled(true);
		pause_btn.setEnabled(false);		
	}
}
