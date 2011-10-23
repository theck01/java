
public class THStopTile extends THTile{

	public THStopTile(int x, int y){
		super(x,y);
	}

	public Point getNextTilePosition(){
		return null;
	}

	public void draw(Point location, int size, Graphics g){

		int x = location.getX();
		int y = location.getY();
		int o = size/6;
		
		if(observed == 1){
			g.setColor(Color.yellow);
		}
		else{
			g.setColor(Color.blue);
		}

		g.fillRect(x+2*o, y+2*o, 2*o, 2*o);

		return;
	}
}
