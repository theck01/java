
import java.awt.*;
import javax.swing.*;

public class THUpTile extends THTile{

	public THUpTile(Point array_pos, Point actual_pos, Point target_pos, int tile_size){
		super(array_pos, actual_pos, target_pos, tile_size);
	}

	public Point getNextTilePosition(){
		return new Point((int)array_position.getX(), (int)(array_position.getY()-1));
	}

	public void draw(Graphics g){

		int x = (int)actual_position.getX();
		int y = (int)actual_position.getY();
		int o = actual_size/6;
		int arrow_y[] = {y+1*o, y+3*o, y+3*o, y+5*o, y+5*o, y+3*o, y+3*o};
		int arrow_x[] = {x+3*o, x+o, x+2*o, x+2*o, x+4*o, x+4*o, x+5*o};
		
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
		return THConstants.up_id;
	}
}
