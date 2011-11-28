import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//Class sets up the view for free play mode, where the tile info panel exists,
//board size can be manipulated, and the time of game play is variable
public class THContestView extends JPanel{
	
	protected Main window;
	
	public THContestView(Main window){
		
		this.window = window;
		
		setLayout(new BorderLayout());
		
		THStatePanel state_panel = new THStatePanel();
		THGameManager game = new THGameManager(state_panel, null, true);
		THControlPanel control_panel = new THControlPanel(game, window, true);
		game.setControlPanel(control_panel);
		
		add(state_panel, BorderLayout.NORTH);
		add(game, BorderLayout.CENTER);
		add(control_panel, BorderLayout.SOUTH);
	}
}