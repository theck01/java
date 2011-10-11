
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;


public class THButton extends JButton implements ActionListener{

	//button holds information about drawing area of the main window
	//for clear operation
	protected THCanvas drawing_area;

	protected void setDrawingArea(THCanvas canvas){
		drawing_area = canvas;
	}

	public THButton(String label, THCanvas canvas){
		setText(label);
		setDrawingArea(canvas);
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		System.out.println("Clearing drawing area...");
		drawing_area.clearUserDrawings();
	}
}
