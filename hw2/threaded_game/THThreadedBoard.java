
import java.awt.*;

//Model, manages all game tiles and game logic
public class THThreadedBoard{

	//later implementations of the game may allow for variably sized game board
	protected int num_columns, num_rows;
	protected THTile[][] tile_array;
	protected THThreadedGameManager game;

	//constructs a new THThreadedBoard with default size 10X10 and fills with random
	//tiles
	public THThreadedBoard(THThreadedGameManager game_mngr){

		num_columns = THConstants.board_width;
		num_rows = THConstants.board_height;
		tile_array = new THTile[num_columns][num_rows];

		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
				tile_array[i][j] = THTileFactory.createRandomTile(new Point(i,j));
			}
		}

		game = game_mngr;
	}

	//function switches the positions of tile1 and tile2 in the array and changes
	//the internal position of each tile
	protected void tileSwap(THTile tile1, THTile tile2){
	
		Point pos1 = tile1.getArrayPosition();
		Point pos2 = tile2.getArrayPosition();

		tile1.setArrayPosition(pos2);
		tile2.setArrayPosition(pos1);

		//buffer variable prevents data loss as tiles are switched in specific
		//cases, such as when values are passed directly from array
		THTile buffer = tile1;
		tile_array[(int)pos1.getX()][(int)pos1.getY()] = tile2;
		tile_array[(int)pos2.getX()][(int)pos2.getY()] = buffer;

		return;
	}

	protected void replaceColumn(THTile current_tile){

		THTile next;
		Point next_position;

		Point position = current_tile.getArrayPosition();
		int x = (int)position.getX(); //the column being replaced

		//if the current tile is not at the top of the board, get the tile
		//immediately above it
		if(position.getY()>0){
			next = tile_array[x][(int)position.getY()-1];
			next_position = next.getArrayPosition();
		}
		//else if the tile is at the top and needs to be replaced, replace it
		else if(current_tile.isObserved()){
			int y = (int)position.getY();
			tile_array[x][y] = THTileFactory.createRandomTile(new Point(x,y));
			return;
		}
		//else if the tile is at the top and does not need replacement, return
		else{
			return;
		}

		//if the current tile needs replacement, find the next tile to replace
		//it. current tile is not at the top of the board
		if(current_tile.isObserved()){

			//while loop searches for next unobserved tile within array bounds
			//that is located above the current tile			
			while(next_position.getY()>0 && next.isObserved()){
				next = tile_array[x][(int)next_position.getY()-1];
				next_position = next.getArrayPosition();
			}

			//if the next tile is at the top boundry of the board
			if(next_position.getY() == 0){

				//then replace all intermediate tiles if the next tile is observed
				if(next.isObserved()){
				
					for(int i=(int)position.getY(); i>=0; i--){
						tile_array[x][i] = THTileFactory.createRandomTile(new Point(x,i));
					}

					return;
				}
				//else swap the unobserved top tile with the current tile, and replace
				//all tiles above current
				else{
				
					tileSwap(current_tile, next);
					for(int i=(int)position.getY()-1; i>=0; i--){
						tile_array[x][i] = THTileFactory.createRandomTile(new Point(x,i));
					}

					return;
				}
			}
			//else swap next with current and call replaceColumn on the tile above current_tile
			else{
				tileSwap(current_tile, next);
				replaceColumn(tile_array[x][(int)position.getY()-1]);
				return;
			}
		}
		//if the current_tile does not need replacement, execute funtion on
		//the tile above
		else{
			replaceColumn(next);
			return;
		}
	}

	//function checks each column for tiles to replace, and does so if required
	//may feature more complex animations in the future
	protected void replaceTiles(){

		for(int i=0; i<num_columns; i++){
			System.out.println("Replacing column " + i);
			replaceColumn(tile_array[i][num_rows-1]);
		}

		game.repaint();
		
		return;
	}

	//function recursively iterates through all tiles in the current move chain
	//and tabulates the score.
	protected int markTileChain(THTile current_tile, int current_length){

		current_tile.setObserved();

		game.repaint();

		System.out.println("Animation delay");
		
		try{
			Thread.sleep(THConstants.animation_duration);
		}
		catch(InterruptedException e){
			System.out.println("Animation delay interupted, returning");
			return 0;
		}
		
		System.out.println("Animation delay ended");
		
		Point next_position = current_tile.getNextTilePosition();
		System.out.println("Next tile position fetched");

		//if the current_tile represents a stop tile
		if(next_position == null){
			System.out.println("Stop tile encountered, ending tile chain");
			return THConstants.stop_points*current_length;
		}
		
		int x = (int)next_position.getX();
		int y = (int)next_position.getY();

		//if the current_tile points to another tile
		if(x<num_columns && x >=0 && y<num_rows && y>=0){
			//and if the tile is marked, return score
			if(tile_array[x][y].isObserved()){
				System.out.println("Hit an already observed tile in the chain, ending chain");
				return THConstants.direction_points*current_length;
			}
			//else keep marking and return score

			System.out.println("Continuing onto next tile in chain");
			return THConstants.direction_points*current_length + markTileChain(tile_array[x][y], current_length+1);
		}
		//if the current_tile points off the board return score
		else{
			System.out.println("Next tile is out of board bounds, ending tile chain");
			return THConstants.direction_points*current_length;
		}	
	}

	//calls markTileChain() on the point specified by array_position, calls replaceTiles()
	//after markTileChain returns, and returns the score from that move. This function is only
	//called within a special thread created and reserved for user move processing.
	public int userMove(Point array_position){
		
		THTile clicked_tile = tile_array[(int)array_position.getX()][(int)array_position.getY()];

		System.out.println("Processing move, beginning to mark the chain of tiles");
		int score = markTileChain(clicked_tile, 1);

		System.out.println("Move processed, replacing used tiles");
		replaceTiles();
		
		return score;
	}

	//Function draws grid lines starting at point board_pos, and tells each tile
	//to draw itself with the corresponding point
	public void draw(Point board_pos, int tile_size, Graphics g){

		//boolean determines whether horizontal lines should be drawn
		boolean draw=true;

		//draws white board background
		g.setColor(Color.white);
		g.fillRect((int)board_pos.getX(), (int)board_pos.getY(), num_columns*tile_size, num_rows*tile_size);

		g.setColor(Color.black);

		//for loops draw grid for tiles and instructs each THTile to draw itself
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
			
				if(draw){
					g.setColor(Color.black);
					g.drawLine((int)board_pos.getX(), (int)(board_pos.getY()+j*tile_size), (int)(board_pos.getX()+num_columns*tile_size), (int)(board_pos.getY()+j*tile_size));
				}
				
				tile_array[i][j].draw(new Point((int)(board_pos.getX()+i*tile_size), (int)(board_pos.getY()+j*tile_size)), tile_size, g);	
			}

			draw = false;

			g.setColor(Color.black);
			g.drawLine((int)(board_pos.getX()+i*tile_size), (int)board_pos.getY(), (int)(board_pos.getX()+i*tile_size), (int)(board_pos.getY()+num_rows*tile_size));
		}

		//draws last two grid lines
		g.drawLine((int)board_pos.getX(), (int)(board_pos.getY()+num_rows*tile_size), (int)(board_pos.getX()+num_columns*tile_size), (int)(board_pos.getY()+num_rows*tile_size));
		g.drawLine((int)(board_pos.getX()+num_columns*tile_size), (int)board_pos.getY(), (int)(board_pos.getX()+num_columns*tile_size), (int)(board_pos.getY()+num_rows*tile_size));

		return;
	}

	public Point getArraySize(){
		return new Point(num_columns, num_rows);
	}
}
