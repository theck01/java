
import java.awt.*;
import javax.swing.*;

//Contains all common methods for the THTile subclasses
abstract class THTile{

	protected Point array_position;
	protected Point actual_position;
	protected Point target_position;
	protected int tile_size;
	protected int actual_size;

	//boolean used to determine if a tile has already been checked for game
	//should also be used in the draw function to change arrow color
	protected boolean observed;

	public THTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
	
		array_position = array_pos;
		actual_position = actual_pos;
		target_position = target_pos;
		this.tile_size = tile_size;
		actual_size = tile_size;
		
		observed = false;
	}

	protected boolean isPositioned(){
		
		//if the tile is being shrunk then it must be in position
		//designed so tiles do not "move" while shrinking and do not shrink
		//before being located in the right position
		if(actual_size != tile_size){
			return true;
		}
		
		return ((target_position.getX() == actual_position.getX()) && (target_position.getY() == actual_position.getY()));	
	}

	//tells the tile to drop down, 
	//updating its target position and array position
	public void dropDown(){
		target_position.translate(0, tile_size);
		array_position.translate(0,1);
	}
	
	//shinks tile until some minimum size is reached, at which point the
	//funtion returns true, the tile has reached the minimum size
	public boolean shrink(){
	
		if(!isObserved()){
			return true;
		}
		
		actual_size--;
		//position is only shifted half the time, so that the tile appears
		//to be located in the middle of the allocated space at all times
		if(actual_size%2 == 0){
			actual_position.translate(1,1);
		}
		
		if(actual_size <= THConstants.min_tile_size){
			return true;
		}
		
		return false;
	}
	
	//animates tile movement, returns true if animation is complete, false
	//if not
	public boolean animate(){
		actual_position.translate(0,THConstants.animation_distance);
		if(actual_position.getY() > target_position.getY()){
			actual_position.setLocation(target_position);
		}
		
		return isPositioned();
	}
	
	public void setArrayPosition(Point p){
		array_position.setLocation(p);
	}

	public Point getArrayPosition(){
		return new Point(array_position);
	}
	
	public void setTargetPosition(Point p){
		target_position.setLocation(p);
	}
	
	public Point getTargetPosition(){
		return new Point(target_position);
	}

	public void setActualPosition(Point p){
		actual_position.setLocation(p);
	}
	
	public Point getActualPosition(){
		return new Point(actual_position);
	}
	
	public void setTileSize(int size){
		tile_size = size;
	}
	
	public int getTileSize(){
		return tile_size;
	}
	
	public void setActualSize(int size){
		actual_size = size;
	}
	public int getActualSize(){
		return actual_size;
	}
	
	public void setObserved(boolean state){
		observed = state;
	}

	public boolean isObserved(){
		return observed;
	}
	
	//for debugging
	public void print(){
		System.out.println("Array Position: " + array_position.toString());
		System.out.println("Target Screen Position: " + target_position.toString());
		System.out.println("Actual Screen Position: " + actual_position.toString());
		System.out.println("Tile Size: " + tile_size);
		System.out.println("Actual Tile Size: " + actual_size);
	}

	//get next tile method will return the point in the array where the next
	//tile in the chain is located or null
	abstract Point getNextTilePosition();

	//draw method informs the tile about the location and size at which drawing
	//should occur
	abstract void draw(Graphics g);
	
	//returns tile type, used for easy switch statements rather than processing
	//"is member of class" type methods
	abstract int getID();
}
