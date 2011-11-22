
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class THSplitVTile extends THTile{

	public THSplitVTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
		super(array_pos, actual_pos, target_pos, tile_size);
	}
	
	public Vector<Point> getNextTilePosition(){
		Vector<Point> return_v = new Vector<Point>(2);
		return_v.add(new Point((int)array_position.getX(), (int)array_position.getY()+1));
		return_v.add(new Point((int)array_position.getX(), (int)array_position.getY()-1));
		return return_v;
	}
	
	public void draw(Graphics g){
		
		int x = (int)actual_position.getX();
		int y = (int)actual_position.getY();
		int o = actual_size/12;
		int arrow_x[] = {x+2*o, x+6*o, x+10*o, x+8*o, x+8*o, x+10*o, x+6*o, x+2*o, x+4*o, x+4*o};
		int arrow_y[] = {y+5*o, y+1*o, y+5*o, y+5*o, y+7*o, y+7*o, y+11*o, y+7*o, y+7*o, y+5*o};
		
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
		return THConstants.splitv_id;
	}
}