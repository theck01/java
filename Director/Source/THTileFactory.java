
import java.lang.Math;
import java.awt.*;

//class that creates random THTile subclasses to populate the game board
public class THTileFactory{

	private THTileFactory(){}

	public static THTile createRandomTile(Point array_pos){
	
		double random_num;
		random_num = Math.random();

		int x = (int)array_pos.getX();
		int y = (int)array_pos.getY();
		
		if(random_num < 0.22){
			return new THLeftTile(x,y);
		}
		else if(random_num < 0.44){
			return new THRightTile(x,y);
		}
		else if(random_num < 0.66){
			return new THUpTile(x,y);
		}
		else if(random_num < 0.88){
			return new THDownTile(x,y);
		}
		else{
			return new THStopTile(x,y);
		}
	}
}
