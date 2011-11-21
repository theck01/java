
import java.lang.Math;
import java.awt.*;

//class that creates random THTile subclasses to populate the game board
public class THTileFactory{

	private THTileFactory(){}

	public static THTile createRandomTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
	
		double random_num;
		random_num = Math.random();

		int x = (int)array_pos.getX();
		int y = (int)array_pos.getY();
		
		if(random_num < 0.22){
			return new THLeftTile(array_pos, actual_pos, target_pos, tile_size);
		}
		else if(random_num < 0.44){
			return new THRightTile(array_pos, actual_pos, target_pos, tile_size);
		}
		else if(random_num < 0.66){
			return new THUpTile(array_pos, actual_pos, target_pos, tile_size);
		}
		else if(random_num < 0.88){
			return new THDownTile(array_pos, actual_pos, target_pos, tile_size);
		}
		else{
			return new THStopTile(array_pos, actual_pos, target_pos, tile_size);
		}
	}
}
