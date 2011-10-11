
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


public class THColorSelector extends JPanel implements ItemListener{

	protected THCanvas drawing_area;

	protected void setDrawingArea(THCanvas canvas){
		drawing_area = canvas;
	}
	
	public THColorSelector(THCanvas canvas){

		setDrawingArea(canvas);

		//Creates a panel of radio buttons
		ButtonGroup group = new ButtonGroup();

		JCheckBox color_box = new JCheckBox("Blue", true);
		add(color_box);
		group.add(color_box);
		color_box.addItemListener(this);

		color_box = new JCheckBox("Red", false);
		add(color_box);
		group.add(color_box);
		color_box.addItemListener(this);

		color_box = new JCheckBox("Yellow", false);
		add(color_box);
		group.add(color_box);
		color_box.addItemListener(this);
	}

	public void itemStateChanged(ItemEvent e){
	
		if(e.getStateChange() == ItemEvent.SELECTED){
		
			String selected = ((JCheckBox)e.getItem()).getText();

			//changes the color that the user will draw in on the
			//interface
			if(selected.compareTo("Blue") == 0){
				System.out.println("Drawing color set to blue");
				drawing_area.setCurrentColor(Color.blue);
			}
			else if(selected.compareTo("Red") == 0){
				System.out.println("Drawing color set to red");
				drawing_area.setCurrentColor(Color.red);
			}
			else{
				System.out.println("Drawing color set to yellow");
				drawing_area.setCurrentColor(Color.yellow);
			}
		}
	}
}
