
import java.awt.*;
import java.util.*;

//Model Controller, manages all game times and game logic
public class THBoard{

	protected Point origin;
	protected int tile_size;
	
	protected boolean contest;
	
	//later implementations of the game will allow for variably sized game board
	protected int num_columns, num_rows;
	protected THTile[][] tile_array;
    
    protected THProcessScheduler ps;
	protected THGameManager game;
	protected THInfoPanel info;
    protected THTile active_tile;
	
	boolean invalid_move;

	//constructs a new THBoard with default size 10X10 and fills with random
	//tiles
	public THBoard(THGameManager game_mngr, THProcessScheduler ps, Point origin, int tile_size, THInfoPanel info, boolean contest){

		this.contest = contest;
		
		this.info = info;
		this.origin = origin;
		this.tile_size = tile_size;
		
		int x = (int)origin.getX();
		int y = (int)origin.getY();
		
		num_columns = THConstants.board_width;
		num_rows = THConstants.board_height;
		tile_array = new THTile[num_columns][num_rows];

		Point location = new Point(0,0);

		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
				location.setLocation(x+i*tile_size, y+j*tile_size);
				tile_array[i][j] = THTileFactory.createRandomTile(new Point(i,j), new Point(location), new Point(location), tile_size);
			}
		}
		
		this.ps = ps;
		game = game_mngr;
        deselect();
		
		invalid_move = false;
	}
	
	public void size(Point new_origin, int new_tile_size){
		
		if(new_tile_size == tile_size && new_origin.equals(origin)){
			return;
		}
		
		tile_size = new_tile_size;
		origin.setLocation(new_origin);
		
		int x = (int)origin.getX();
		int y = (int)origin.getY();
		
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
				tile_array[i][j].setTileSize(tile_size);
				tile_array[i][j].setActualSize(tile_size);
				tile_array[i][j].setActualPosition(new Point(x+i*tile_size, y+j*tile_size));
				tile_array[i][j].setTargetPosition(new Point(x+i*tile_size, y+j*tile_size));
			}
		}
		
	}
	
	public void deselect(){
		active_tile = null;
		if(info != null){
			info.noneSelected();
		}
		game.repaint();
	}
	
	public void select(THTile tile){
		active_tile = tile;
		if(info != null){	
			info.tileSelected(tile);
		}
		game.repaint();
	}
	
	public void replaceTile(THTile t, int tile_id){
		
		int x = (int)t.getArrayPosition().getX();
		int y = (int)t.getArrayPosition().getY();
		
		Point i = active_tile.getArrayPosition();
		Point a = active_tile.getActualPosition();
		Point tp = active_tile.getTargetPosition();
		int s = active_tile.getTileSize();
		int as = active_tile.getActualSize();
		
		switch(tile_id){
			case THConstants.left_id:
				tile_array[x][y] = new THLeftTile(i,a,tp,s);
				break;
			case THConstants.right_id:
				tile_array[x][y] = new THRightTile(i,a,tp,s);
				break;
			case THConstants.up_id:
				tile_array[x][y] = new THUpTile(i,a,tp,s);
				break;
			case THConstants.down_id:
				tile_array[x][y] = new THDownTile(i,a,tp,s);
				break;
			case THConstants.splitv_id:
				tile_array[x][y] = new THSplitVTile(i,a,tp,s);
				break;
			case THConstants.splith_id:
				tile_array[x][y] = new THSplitHTile(i,a,tp,s);
				break;
			default:
				tile_array[x][y] = new THStopTile(i,a,tp,s);
				break;
		}		
		tile_array[x][y].setActualSize(as);
		
		select(tile_array[x][y]);
		
		game.repaint();
	}

	//function switches the positions of tile1 and tile2 in the array and changes
	//the internal position of each tile
	protected void tileSwap(THTile tile1, THTile tile2){
	
		Point pos1 = tile1.getArrayPosition();
		int x1 = (int)pos1.getX();
		int y1 = (int)pos1.getY();
		
		Point target1 = tile1.getTargetPosition();
		Point actual1 = tile1.getActualPosition();
		
		Point pos2 = tile2.getArrayPosition();
		int x2 = (int)pos2.getX();
		int y2 = (int)pos2.getY();
		
		Point target2 = tile2.getTargetPosition();
		Point actual2 = tile2.getActualPosition();
		
		tile1.setArrayPosition(pos2);
		tile1.setTargetPosition(target2);
		tile1.setActualPosition(actual2);
		
		tile2.setArrayPosition(pos1);
		tile2.setTargetPosition(target1);
		tile2.setActualPosition(actual1);
		
		tile_array[x1][y1] = tile2;
		tile_array[x2][y2] = tile1;
		
        game.repaint();

		return;
	}

    //recursively replaces tiles, modifies target locations of affected tiles, and creates new tiles
	protected void replaceColumn(THTile current_tile, int new_tiles){

		Point target = new Point();
		Point actual = new Point();

		Point position = current_tile.getArrayPosition();
		int x = (int)position.getX(); //the column being replaced
		int y = (int)position.getY();

		if(y == 0){
			if(current_tile.isObserved()){
			
				target.setLocation((int)origin.getX()+x*tile_size, (int)origin.getY());
				actual.setLocation((int)origin.getX()+x*tile_size, (0-(new_tiles+1)*tile_size));
				new_tiles++;
				
				tile_array[x][0] = THTileFactory.createRandomTile(new Point(x,y), new Point(actual), new Point(target), tile_size);
			}
		}
		else{
		
			THTile next = tile_array[x][y-1];
			
			if(current_tile.isObserved()){
				//drop all tiles above down by 1
				for(int i=y; i>0; i--){
					tile_array[x][i] = tile_array[x][i-1];
					tile_array[x][i].dropDown();
				}
				
				//add new tile at the top
				target.setLocation((int)origin.getX()+x*tile_size, (int)origin.getY());
				actual.setLocation((int)origin.getX()+x*tile_size, (0-(new_tiles+1)*tile_size));
				new_tiles++;
				tile_array[x][0] = THTileFactory.createRandomTile(new Point(x,0), new Point(actual), new Point(target), tile_size);
			}
			
			replaceColumn(next, new_tiles);
		}
	}
	
	
	protected void shrinkObserved(){
		
		boolean stop = true;
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
				if(!tile_array[i][j].shrink()){
					stop = false;
				}
			}
		}
		
		if(stop){
			ps.add(new THBoardReplaceTiles(this),0);
		}
		else{
			ps.add(new THBoardShrinkObserved(this), THConstants.shrink_delay);
		}
		
		game.repaint();
	}
	
	protected void animate(){
	
		boolean stop = true;
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
				if(!tile_array[i][j].animate()){
					stop = false;
				}
			}
		}
		
		if(!stop){
			ps.add(new THBoardAnimate(this), THConstants.animation_delay);
		}
		
		game.repaint();
	}

	//function checks each column for tiles to replace, and does so if required
	//may feature more complex animations in the future
    //loads num_columns of replace columns processes to be executed 
	protected void replaceTiles(){

		for(int i=0; i<num_columns; i++){
			System.out.println("Replacing column " + i);
			replaceColumn(tile_array[i][num_rows-1], 0);
		}

		ps.add(new THBoardAnimate(this),THConstants.animation_delay);
		game.repaint();
		
		return;
	}

	//function recursively iterates through all tiles in the current move chain
	//and tabulates the score
	//THBoardMarkChain is a THProcess that executes the method
	protected void markChain(Vector<THTile> active_tiles, int current_length){

		Vector<THTile> next_vector = new Vector<THTile>();
        
        for(int i=0; i<active_tiles.size(); i++){
            
			boolean split=false;
			
            THTile current_tile = active_tiles.get(i);
            THTile next_tile = null;
            
            current_tile.setObserved(true);
            
            Vector<Point> next_positions = current_tile.getNextTilePosition();
			if(next_positions.size()>1){
				split = true;
			}
            
			for(int j=0; j<next_positions.size(); j++){
				
				next_tile = null;
				//if the current_tile represents a stop tile
				if(next_positions.get(j) == null){
					System.out.println("Stop tile encountered, ending tile branch");
					game.updateScore(THConstants.stop_points*current_length);
				}
				else{
				
					int x = (int)next_positions.get(j).getX();
					int y = (int)next_positions.get(j).getY();
					
					//if the current_tile points to another tile
					if(x<num_columns && x >=0 && y<num_rows && y>=0){
						//and if the tile is marked, return score
						if(tile_array[x][y].isObserved()){
							System.out.println("Hit an already observed tile in the chain, ending chain");
							
							//if the tile is a split tile, no points should be earned
							if(!split){
								game.updateScore(THConstants.direction_points*current_length);
							}
						}
						else{                        
							System.out.println("Continuing onto next tile in chain");
							
							//if the tile is a split tile then no points should be earned
							if(!split){
								game.updateScore(THConstants.direction_points*current_length);
							}
							
							next_tile = tile_array[x][y];
						}
					}
					//if the current_tile points off the board return score
					else{
						System.out.println("Next tile is out of board bounds, ending tile chain");
						
						//if the tile is a split tile then no points should be earned
						if(!split){
							game.updateScore(THConstants.direction_points*current_length);
						}
					}
				}
				
				if(next_tile != null){
					next_vector.add(next_tile);
				}
            }
            
        }
        
        if(next_vector.size() == 0){
			ps.add(new THBoardShrinkObserved(this), THConstants.shrink_delay);
        }
        else{
            ps.add(new THBoardMarkChain(this, next_vector, current_length+1), THConstants.move_delay);
        }
        
        game.repaint();
	}

	//sets the active tile, swaps or deselects if an active tile already set
	//and generates intial THMarkChain process if the move is valid.
	public void userMove(Point array_position){

		THTile clicked_tile = tile_array[(int)array_position.getX()][(int)array_position.getY()];
        
		//if no tile is active, set as active_tile
        if(active_tile == null){
            select(clicked_tile);
        }
        else if(active_tile == clicked_tile){
            deselect();
        }
        else{
            
            System.out.println("Processing move, checking the chain of tiles");
            
            tileSwap(active_tile, clicked_tile);
			
			game.incNumMoves();
			
            Vector<THTile> seed_tiles = new Vector<THTile>();
            seed_tiles.add(active_tile);
            
            ps.add(new THBoardMarkChain(this, seed_tiles, 1), THConstants.invalid_delay);
			
			deselect();
        }
		
		game.repaint();
	}

	//Function draws grid lines starting at point board_pos, and tells each tile
	//to draw itself with the corresponding point
	public void draw(Graphics g){

		//boolean determines whether horizontal lines should be drawn
		boolean draw=true;
		
		int x = (int)origin.getX();
		int y = (int)origin.getY();		

		//draws the background
		if(game.isActive()){
			g.setColor(Color.white);
		}
		else{
			g.setColor(Color.lightGray);
			deselect();
		}
		
		g.fillRect(x, y, num_columns*tile_size, num_rows*tile_size);
        
		//highlights the currently selected tile, if one is specified
		if(active_tile != null){
			int off_x = (int)active_tile.getActualPosition().getX();
			int off_y = (int)active_tile.getActualPosition().getY();
			
			if(invalid_move){
				g.setColor(Color.red);
			}
			else{
				g.setColor(Color.gray);				
			}
				
			g.fillRect(off_x, off_y, tile_size, tile_size);
		}

		g.setColor(Color.black);

		//for loops draw grid for tiles and if the game is active 
		//instructs each THTile to draw itself
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
			
				if(draw){
					g.setColor(Color.black);
					g.drawLine(x, y+j*tile_size, x+num_columns*tile_size, y+j*tile_size);
				}
				
				if(!game.hideBoard()){
					tile_array[i][j].draw(g);
				}
			}

			draw = false;

			g.setColor(Color.black);
			g.drawLine(x+i*tile_size, y, x+i*tile_size, y+num_rows*tile_size);
		}

		//draws last two grid lines
		g.drawLine(x, y+num_rows*tile_size, x+num_columns*tile_size, y+num_rows*tile_size);
		g.drawLine(x+num_columns*tile_size, y, x+num_columns*tile_size, y+num_rows*tile_size);

		return;
	}

	public Point getArraySize(){
		return new Point(num_columns, num_rows);
	}
}
