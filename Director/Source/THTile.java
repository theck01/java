
import java.awt.*;
import javax.swing.*;

//Contains all common methods for the THTile subclasses
abstract class THTile{

	protected Point array_position;

	//boolean used to determine if a tile has already been checked for game
	//should also be used in the draw function to change arrow color
	protected boolean observed;

	public THTile(int x, int y){
		array_position = new Point(x,y);
		observed = false;
		
	}

	public void setArrayPosition(int new_x, int new_y){
		array_position = new Point(new_x, new_y);
	}

	public void setArrayPosition(Point new_pt){
		array_position = new_pt;
	}
	

	public Point getArrayPosition(){
		return array_position;
	}

	public void setObserved(){
		observed = true;
	}

	public boolean isObserved(){
		return observed;
	}

	//get next tile method will return the point in the array where the next
	//tile in the chain is located or null
	abstract Point getNextTilePosition();

	//draw method informs the tile about the location and size at which drawing
	//should occur
	abstract void draw(Point location, int size, Graphics g);	
}
