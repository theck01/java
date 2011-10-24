
import java.awt.*;
import javax.swing.*;

public class THLeftTile extends THTile{

	public THLeftTile(int x, int y){
		super(x,y);
	}

	public Point getNextTilePosition(){
		return new Point((int)(array_position.getX()-1), (int)array_position.getY());
	}

	public void draw(Point location, int size, Graphics g){

		int x = (int)location.getX();
		int y = (int)location.getY();
		int o = size/6;
		int arrow_x[] = {x+o, x+3*o, x+3*o, x+5*o, x+5*o, x+3*o, x+3*o};
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
}
