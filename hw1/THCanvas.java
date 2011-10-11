
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class THCanvas extends JComponent implements MouseListener, MouseMotionListener{

	Color current_color;
	GeneralPath current_path;
	THMutableGeneralPathArray paths;

	//Boolean to determine if user is still drawing	
	int draw;

	public THCanvas(){
		current_color = Color.blue;
		current_path = new GeneralPath();
		paths = new THMutableGeneralPathArray();
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void setCurrentColor(Color selected_color){
		current_color = selected_color;
	}

	public void clearUserDrawings(){
		current_path = new GeneralPath();
		paths.clear();
		repaint();
	}

	protected void saveCurrentPath(){
		paths.insert(current_path, current_color);
		current_path = new GeneralPath();
	}

	public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D) g;

		//draw background
		g2.setColor(Color.white);
		g2.fillRect(0,0,getWidth(),getHeight());

		for(int i=0; i<paths.getArrayLength(); i++){
			g2.setColor(paths.getColor(i));
			g2.draw(paths.getPath(i));
		}

		g2.setColor(current_color);
		g2.draw(current_path);
	}
	
	
	public void mousePressed(MouseEvent e){

		int x_pos, y_pos;
		x_pos = e.getPoint().x;
		y_pos = e.getPoint().y;
		
		if(draw == 0){
			System.out.println("Starting new drawing at " + x_pos + ", " + x_pos);
			draw = 1;
			current_path.moveTo(x_pos, y_pos);
		}
	}
	
	public void mouseDragged(MouseEvent e){

		int x_pos, y_pos;
		x_pos = e.getPoint().x;
		y_pos = e.getPoint().y;

		if(draw == 1){
			if(x_pos >= 0 && x_pos <= getWidth() && y_pos >= 0 && y_pos <= getHeight()){
					System.out.println("Drawing line to " + x_pos + ", " + y_pos);
					current_path.lineTo(x_pos, y_pos);
			}
			//if currently drawing and out of bounds, stop drawing and save
			else{
				System.out.println("Mouse exited drawing area while drawing, drawing complete, saving drawing");
				draw = 0;
				saveCurrentPath();
			}
		}

		repaint();
	}
	
	public void mouseReleased(MouseEvent e){

		if(draw == 1){
			System.out.println("Drawing complete, saving drawing");
			draw = 0;
			saveCurrentPath();
			repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
}
