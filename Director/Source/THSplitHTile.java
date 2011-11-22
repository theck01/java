
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class THSplitHTile extends THTile{
	
	public THSplitHTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
		super(array_pos, actual_pos, target_pos, tile_size);
	}
	
	public Vector<Point> getNextTilePosition(){
		Vector<Point> return_v = new Vector<Point>(2);
		return_v.add(new Point((int)array_position.getX()+1, (int)array_position.getY()));
		return_v.add(new Point((int)array_position.getX()-1, (int)array_position.getY()));
		return return_v;
	}
	
	public void draw(Graphics g){
		
		int x = (int)actual_position.getX();
		int y = (int)actual_position.getY();
		int o = actual_size/12;
		int arrow_x[] = {x+5*o, x+1*o, x+5*o, x+5*o, x+7*o, x+7*o, x+11*o, x+7*o, x+7*o, x+5*o};
		int arrow_y[] = {y+2*o, y+6*o, y+10*o, y+8*o, y+8*o, y+10*o, y+6*o, y+2*o, y+4*o, y+4*o};
		
		if(observed){
			g.setColor(Color.yellow);
		}
		else{
			g.setColor(Color.blue);
		}
		
		g.fillPolygon(arrow_x, arrow_y, 10);
		
		return;
	}
	
	public int getID(){
		return THConstants.splith_id;
	}
}