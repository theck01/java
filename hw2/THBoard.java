
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Model Controller, manages all game times and game logic
public class THBoard{

	//later implementations of the game may allow for variably sized game board
	protected int tile_array_width, tile_array_height;
	protected THTile[][] tile_array;
	protected THGameManager game;

	//constructs a new THBoard with default size 10X10 and fills with random
	//tiles
	public THBoard(THGameManager game_mngr){

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
		tile_array[pos1.getX()][pos1.getY()] = tile2;
		tile_array[pos2.getX()][pos2.getY()] = buffer;

		return;
	}

	protected void replaceColumn(THTile current_tile){

		Point position = current_tile.getArrayPosition();
		int x = position.getX();

		//if the current tile is not at the top of the board, get the tile
		//immediately above it
		if(position.getX()>0){
			THTile next = tile_array[x][position.getY()-1];
			Point next_position = next.getPosition();
		}
		//else if the tile is at the top and needs to be replaced, replace it
		else if(current_tile.isObserved()){
			int y = position.getY();
			tile_array[x][y] = THTileFactory.createRandomTile(new Point(x,y))
			return;
		}
		//else if the tile is at the top and does not need replacement, return
		else{
			return;
		}

		//if the current tile needs replacement, find the next tile to replace
		//it
		if(current_tile.isObserved()){

			//while loop searches for next unobserved tile within array bounds
			while(next_position.getX()>0 && next.isObserved == 1){
				next = tile_array[x][next_position.getY()-1];
				next_position = next.getArrayPosition();
			}

			//if the next tile is at the top boundry of the board
			if(next_position.getX() == 0){

				//then replace all intermediate tiles if the next tile is observed
				if(next.isObserved()){
				
					for(int i=position.getY(); i>=0; i--){
						tile_array[x][i] = THTileFactory.getRandomTile(new Point(x,i));
					}

					return;
				}
				//else swap the unobserved top tile with the current tile, and replace
				//all tiles above current
				else{
				
					tileSwap(current_tile, next);
					for(int i=position.getY()-1; i>=0; i--){
						tile_array[x][y] = THTileFactory.getRandomTile(new Point(x,i));
					}

					return;
				}
			}
			//else swap next with current and call replaceColumn on the tile above current_tile
			else{
				tileSwap(current_tile, next);
				replaceColumn(tile_array[x][position.getY()-1]);
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
	protected void replaceTiles(){

		for(int i=0; i<num_columns; i++){
			replaceColumn(tile_array[num_rows-1][i]);
		}

		return;
	}

	//function recursively iterates through all tiles in the current move chain
	//and tabulates the score	
	protected int markTileChain(THTile current_tile, int current_length){

		current_tile.setObserved();
		//repaint();

		game.timeDelay(THConstants.animation_duration);

		Point next_position = current_tile.getNextTilePosition();
		//if the current_tile represents a stop tile
		if(new_position == null){
			return THConstants.stop_points*current_length;
		}
		
		int x = next_position.getX();
		int y = next_position.getY();

		current_tile.setObserved();

		//if the current_tile points to another tile
		if(x<num_columns && x >=0 && y<num_rows && y>=0){
			//and if the tile is marked, return score
			if(tile_array[x][y].isObserved()){
				return THConstants.direction_points*current_length;
			}
			//else keep marking and return score
			return THConstants.direction_points*current_length + markTileChain(tile_array[x][y], current_length+1);
		}
		//if the current_tile points off the board return score
		else{
			return THConstants.direction_points*current_length;
		}	
	}

	//calls markTileChain() on the point specified by array_position, calls replaceTiles()
	//after markTileChain returns, and returns the score from that move.
	public int userMove(Point array_position){

		THTile clicked_tile = tile_array[array_position.getX()][array_position.getY()];

		int score = markTileChain(clicked_tile, 1);

		replaceTiles();

		return score;
	}

	//Function draws grid lines starting at point board_pos, and tells each tile
	//to draw itself with the corresponding point
	public void draw(Point board_pos, int tile_size, Graphics g){

		//boolean determines whether horizontal lines should be drawn
		int draw=1;
		
		g.setColor(Color.black);

		//for loops draw grid for tiles and instructs each THTile to draw itself
		for(int i=0; i<num_columns; i++){
			for(int j=0; j<num_rows; j++){
			
				if(draw){
					g.setColor(Color.black);
					g.drawLine(board_pos.getX(), board_pos.getY()+j*tile_size, board_pos.getX()+num_columns*tile_size, board_pos.getY()+j*tile_size);
				}
				
				tile_array[i][j].draw(new Point(board_pos.getX()+i*tile_size, board_pos.getY()+j*tile_size), tile_size, g);	
			}
			draw = 0;
			g.setColor(Color.black);

			g.drawLine(board_pos.getX()+i*tile_size, board_pos.getY(), board_pos.getX()+i*tile_size, board_pos.getY()+num_rows*tile_size);
		}

		//draws last two grid lines
		g.drawLine(board_pos.getX(), board_pos.getY()+num_rows*tile_size, board_pos.getX()+num_columns*tile_size, board_pos.getY()+num_rows*tile_size);
		g.drawLine(board_pos.getX()+num_columns*tile_size, board_pos.getY(), board_pos.getX()+num_columns*tile_size, board_pos.getY()+num_rows*tile_size);

		return;
	}

	public Point getArraySize(){
		return new Point(num_columns, num_rows);
	}
}
