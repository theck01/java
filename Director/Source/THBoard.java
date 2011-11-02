
import java.awt.*;
import java.util.*;

//Model Controller, manages all game times and game logic
public class THBoard{

	//later implementations of the game will allow for variably sized game board
	protected int num_columns, num_rows;
	protected THTile[][] tile_array;
    
    protected THProcessScheduler ps;
	protected THGameManager game;
    protected THTile active_tile;

	//constructs a new THBoard with default size 10X10 and fills with random
	//tiles
	public THBoard(THGameManager game_mngr, THProcessScheduler ps){

		num_columns = THConstants.board_width;
		num_rows = THConstants.board_height;
		tile_array = new THTile[num_columns][num_rows];

		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
				tile_array[i][j] = THTileFactory.createRandomTile(new Point(i,j));
			}
		}
		
		this.ps = ps;
		game = game_mngr;
        active_tile = null;
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
        
        game.repaint();

		return;
	}

    //Will become a THProcess in future program designs
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
    //loads num_columns of replace columns processes to be executed 
	protected void replaceTiles(){

		for(int i=0; i<num_columns; i++){
			System.out.println("Replacing column " + i);
			replaceColumn(tile_array[i][num_rows-1]);
		}

		game.repaint();
		
		return;
	}

	//function recursively iterates through all tiles in the current move chain
	//and tabulates the score
	protected void markChain(Vector<THTile> active_tiles, int current_length){

		Vector<THTile> next_vector = new Vector<THTile>();
        
        for(int i=0; i<active_tiles.size(); i++){
            
            THTile current_tile = active_tiles.get(i);
            THTile next_tile = null;
            
            current_tile.setObserved();
            
            Point next_position = current_tile.getNextTilePosition();
            
            //if the current_tile represents a stop tile
            if(next_position == null){
                System.out.println("Stop tile encountered, ending tile branch");
                game.updateScore(THConstants.stop_points*current_length);
            }
            else{
			
                int x = (int)next_position.getX();
                int y = (int)next_position.getY();
                
                //if the current_tile points to another tile
                if(x<num_columns && x >=0 && y<num_rows && y>=0){
                    //and if the tile is marked, return score
                    if(tile_array[x][y].isObserved()){
                        System.out.println("Hit an already observed tile in the chain, ending chain");
                        game.updateScore(THConstants.direction_points*current_length);
                    }
                    else{                        
                        System.out.println("Continuing onto next tile in chain");
                        game.updateScore(THConstants.direction_points*current_length);
                        next_tile = tile_array[x][y];
                    }
                }
                //if the current_tile points off the board return score
                else{
                    System.out.println("Next tile is out of board bounds, ending tile chain");
                    game.updateScore(THConstants.direction_points*current_length);
                }
            }
            
            if(next_tile != null){
                next_vector.add(next_tile);
            }
        }
        
        if(next_vector.size() == 0){
			ps.add(new THReplaceTiles(this), 0);
        }
        else{
            ps.add(new THMarkChain(this, next_vector, current_length+1), THConstants.move_delay);
        }
        
        game.repaint();
	}

	//calls markTileChain() on the point specified by array_position, calls replaceTiles()
	//after markTileChain returns, and returns the score from that move.
    //!!!!!!!! loads initial markTile chain Process
	public void userMove(Point array_position){

		THTile clicked_tile = tile_array[(int)array_position.getX()][(int)array_position.getY()];
        
        if(active_tile == null){
            active_tile = clicked_tile;
        }
        else if(active_tile == clicked_tile){
            active_tile = null;
        }
        else{
            
            System.out.println("Processing move, beginning to mark the chain of tiles");
            
            tileSwap(active_tile, clicked_tile);
            
            Vector<THTile> seed_tiles = new Vector<THTile>();
            seed_tiles.add(active_tile);
            seed_tiles.add(clicked_tile);
            
            ps.add(new THMarkChain(this, seed_tiles, 1), THConstants.move_delay);
			
			active_tile = null;
        }
		
		game.repaint();
	}

	//Function draws grid lines starting at point board_pos, and tells each tile
	//to draw itself with the corresponding point
	public void draw(Point board_pos, int tile_size, Graphics g){

		//boolean determines whether horizontal lines should be drawn
		boolean draw=true;
		
		int x = (int)board_pos.getX();
		int y = (int)board_pos.getY();		

		//draws white board background
		g.setColor(Color.white);
		g.fillRect(x, y, num_columns*tile_size, num_rows*tile_size);
        
		//highlights the currently selected tile, if one is specified
		if(active_tile != null){
			int off_x = (int)active_tile.getArrayPosition().getX();
			int off_y = (int)active_tile.getArrayPosition().getY();
			g.setColor(Color.gray);
			g.fillRect(x + off_x*tile_size, y + off_y*tile_size, tile_size, tile_size);
		}

		g.setColor(Color.black);

		//for loops draw grid for tiles and instructs each THTile to draw itself
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
			
				if(draw){
					g.setColor(Color.black);
					g.drawLine(x, y+j*tile_size, x+num_columns*tile_size, y+j*tile_size);
				}
				
				tile_array[i][j].draw(new Point(x+i*tile_size, y+j*tile_size), tile_size, g);	
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
