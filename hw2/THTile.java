
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Contains all common methods for the THTile subclasses
abstract class THTile{

	protected Point array_position;

	//boolean used to determine if a tile has already been checked for game
	//should also be used in the draw function to change arrow color
	int observed;

	public THTile(int x, int y){
		array_position = new Point(x,y);
		observed = 0;
		
	}

	public void setArrayPosition(int new_x, int new_y){
		this.x = new_x;
		this.y = new_y;
	}

	public Point getArrayPosition(){
		return array_position;
	}

	public void setObserved(){
		observed = 1;
	}

	public int isObserved(){
		return observed;
	}

	//get next tile method will return the point in the array where the next
	//tile in the chain is located or null
	abstract Point getNextTilePosition();

	//draw method informs the tile about the location and size at which drawing
	//should occur
	abstract void draw(Point location, int size, Graphics g);	
}
