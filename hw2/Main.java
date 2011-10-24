
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class Main extends JFrame{

	public static void main(String[] args){
		new Main();
	}

	public Main(){
	
		setLocation(100,100);
		setSize(500,500);
		setMinimumSize(new Dimension(400,400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container content = getContentPane();
		content.setLayout(new BorderLayout());

		THStatePanel state_panel = new THStatePanel();
		THGameManager game = new THGameManager(state_panel);

		content.add(state_panel, BorderLayout.NORTH);
		content.add(game, BorderLayout.CENTER);

		setVisible(true);
	}
}

