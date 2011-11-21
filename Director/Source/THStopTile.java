
import java.awt.*;
import javax.swing.*;

public class THStopTile extends THTile{

	public THStopTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
		super(array_pos, actual_pos, target_pos, tile_size);
	}

	public Point getNextTilePosition(){
		return null;
	}

	public void draw(Graphics g){

		int x = (int)actual_position.getX();
		int y = (int)actual_position.getY();
		int o = actual_size/6;
		
		if(observed){
			g.setColor(Color.yellow);
		}
		else{
			g.setColor(Color.blue);
		}

		g.fillRect(x+2*o, y+2*o, 2*o, 2*o);

		return;
	}
	
	public int getID(){
		return THConstants.stop_id;
	}
}
