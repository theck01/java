
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//Class sets up the view for free play mode, where the tile info panel exists,
//board size can be manipulated, and the time of game play is variable
public class THFreePlayView extends JPanel{

	protected Main window;
	
	public THFreePlayView(Main window){
		
		this.window = window;
	
		setLayout(new BorderLayout());

		THStatePanel state_panel = new THStatePanel();
		THInfoPanel info_panel = new THInfoPanel();
		THGameManager game = new THGameManager(state_panel, info_panel, false);
		THControlPanel control_panel = new THControlPanel(game, window, false);
		game.setControlPanel(control_panel);

		add(state_panel, BorderLayout.NORTH);
		add(game, BorderLayout.CENTER);
		add(control_panel, BorderLayout.SOUTH);
		add(info_panel, BorderLayout.EAST);
	}
}
