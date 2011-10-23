
public class THRightTile extends THTile{

	public THRightTile(int x, int y){
		super(x,y);
	}

	public Point getNextTilePosition(){
		return new Point(array_position.getX()+1, array_position.getY());
	}

	public void draw(Point location, int size, Graphics g){

		int x = location.getX();
		int y = location.getY();
		int o = size/6;
		int arrow_x[7] = {x+5*o, x+3*o, x+3*o, x+1*o, x+1*o, x+3*o, x+3*o};
		int arrow_y[7] = {y+3*o, y+o, y+2*o, y+2*o, y+4*o, y+4*o, y+5*o};
		
		if(observed == 1){
			g.setColor(Color.yellow);
		}
		else{
			g.setColor(Color.blue);
		}

		g.fillPolygon(arrow_x, arrow_y, 7);

		return;
	}
}
