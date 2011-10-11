
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

		//Window Configuration
		setLocation(100,100);
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Layout
		Container content = getContentPane();
		content.setLayout(new BorderLayout());

		//Add drawing object
		THCanvas canvas = new THCanvas();
		content.add(canvas, BorderLayout.CENTER);

		//Create control panel object
		JPanel control_panel = new JPanel();
		control_panel.setLayout(new FlowLayout());
		//Add buttons to control panel
		THColorSelector color_selector = new THColorSelector(canvas);
		control_panel.add(color_selector);
		THButton clear_button = new THButton("Clear Drawing", canvas);
		control_panel.add(clear_button);
		//Add control panel
		content.add(control_panel, BorderLayout.SOUTH);
		
		//Add state panel object
		JPanel state_panel = new JPanel();
		JLabel title = new JLabel("Drawing App");
		state_panel.add(title);
		content.add(state_panel, BorderLayout.NORTH);

		setVisible(true);
	}
}
