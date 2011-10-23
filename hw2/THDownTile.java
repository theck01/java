
public class THDownTile extends THTile{

	public THDownTile(int x, int y){
		super(x,y);
	}

	public Point getNextTilePosition(){
		return new Point(array_position.getX(), array_position.getY()+1);
	}

	public void draw(Point location, int size, Graphics g){

		int x = location.getX();
		int y = location.getY();
		int o = size/6;
		int arrow_y[7] = {y+5*o, y+3*o, y+3*o, y+1*o, y+1*o, y+3*o, y+3*o};
		int arrow_x[7] = {x+3*o, x+o, x+2*o, x+2*o, x+4*o, x+4*o, x+5*o};
		
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
