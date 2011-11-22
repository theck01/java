
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class THRightTile extends THTile{

	public THRightTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
		super(array_pos, actual_pos, target_pos, tile_size);
	}

	public Vector<Point> getNextTilePosition(){
		Vector<Point> return_v = new Vector<Point>(1);
		return_v.add(new Point((int)array_position.getX() +1, (int)array_position.getY()));
		return return_v;
	}

	public void draw(Graphics g){

		int x = (int)actual_position.getX();
		int y = (int)actual_position.getY();
		int o = actual_size/6;
		int arrow_x[] = {x+5*o, x+3*o, x+3*o, x+1*o, x+1*o, x+3*o, x+3*o};
		int arrow_y[] = {y+3*o, y+o, y+2*o, y+2*o, y+4*o, y+4*o, y+5*o};
		
		if(observed){
			g.setColor(Color.yellow);
		}
		else{
			g.setColor(Color.blue);
		}

		g.fillPolygon(arrow_x, arrow_y, 7);

		return;
	}
	
	public int getID(){
		return THConstants.right_id;
	}
}
