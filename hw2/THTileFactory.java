
import java.lang.Math;

//class that creates random THTile subclasses to populate the game board
public class THTileFactory{

	private THTileFactory(){}

	public static THTile createRandomTile(Point array_pos){
	
		double random_num;
		random_num = Math.random();

		x = array_pos.getX();
		y = array_pos.getY();
		
		if(random_num < 0.2){
			return new THLeftTile(x,y);
		}
		else if(random_num < 0.4){
			return new THRightTile(x,y);
		}
		else if(random_num < 0.6){
			return new THUpTile(x,y);
		}
		else if(random_num < 0.8){
			return new THDownTile(x,y);
		}
		else{
			return new THStopTile(x,y);
		}
	}
}
