
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Controller. Reacts to mouse events, timer events, and modifies the views
//and models accordingly. Responsible for starting and ending the game
public class THGameManager extends JComponent implements MouseListener, ActionListener{

	THBoard game_board;
	THStatePanel state_panel;
	//THTimer game_timer;
	int game_active;
	int time_left;
	int game_score;

	//time_count stores the duration of the game in hundredths of a second
	int time_count;

	public THGameManager(THStatePanel state){
		game_board = new THBoard();
		state_panel = state;
		state.setGameManager(this);
		game_timer = new Timer(THConstants.timer_interval, this);
		game_active = 0;
		time_left = THConstants.game_duration;
		game_score = 0;
		addMouseListener(this);
	}

	//function determines the size of a board to draw using the size of the window
	//available
	protected THBoardSizer determineBoardAttributes(){

		int tile_size;
		int board_x;
		int board_y;

		int num_columns = board.getArraySize().getX();
		int num_rows = board.getArraySize().getY();
		int screen_width = getWidth();
		int screen_height = getHeight();
		
		//nested if statements determine dimensions of the board by dividing the
		//narrowest screen dimensions by the largest of the array dimension to find
		//tile size, and using modulus operations to determine the offset of the 
		//board top corner
		if(screen_width > screen_height){
			if(num_columns > num_rows){
				tile_size = screen_height/num_columns;
				board_x = (screen_height%num_columns)/2;
				board_y = (screen_height%num_rows)/2;
			}
			else{
				tile_size = screen_height/num_rows;
				board_x = (screen_height%num_columns)/2;
				board_y = (screen_height%num_rows)/2;
			}
		}
		else{
			if(num_columns > num_rows){
				tile_size = screen_width/num_columns;
				board_x = (screen_width%num_columns)/2;
				board_y = (screen_width%num_rows)/2;
			}
			else{
				tile_size = screen_width/num_rows;
				board_x = (screen_width%num_columns)/2;
				board_y = (screen_width%num_rows)/2;
			}
		}

		return new THBoardSizer(tile_size, new Point(board_x, board_y));
	}

	public void startGame(){
	
		board = new THBoard();

		game_score = 0;
		state_panel.updateScore(game_score);
		time_left = THConstants.game_duration;
		state_panel.updateTime(time_left);
		
		game_active = 1;
		time_count = 0;
		game_timer.start();
	}

	public void endGame(){
		game_timer.stop();
		game_active = 0;
	}

	public void timeDelay(int hundredths_seconds){
		current_time = time_count;
		while(current_time < time_count+hundredths_seconds){}
		return;
	}	
	
	public void paintComponent(Graphics g){	
		THBoardSizer attributes = determineBoardAttributes();
		game_board.draw(attributes.getPosition(), attributes.getTileSize(), Graphics g);
	}

	public void actionPerformed(ActionEvent e){
		time_count++;
		time_left = THConstants.game_duration - time_count/100;
		state_panel.updateTime(time_left);
		if(!time_left){
			endGame();	
		}
	}

	public void mouseClicked(MouseEvent e){

		//if the game has not been started, do not process user clicks
		if(!game_active){
			return;
		}

		THBoardSizer attributes = determineBoardAttributes();
		Point array_dim = board.getArraySize();

		int tile_size = attributes.getSize();
		int board_x = attributes.getPosition().getX();
		int board_y = attributes.getPosition().getY();
		int max_x = board_x + array_dim.getX()*tile_size;
		int max_y = board_y + array_dim.getY()*tile_size;
		
		Point relative = new Point(e.getX() - board_x, e.getY() - board_y);

		//if the click was out of array bounds, then do nothing
		if(relative.getX() < 0 || relative.getX() > max_x|| relative.getY() < 0 || relative.getY() > max_y){
			return;
		}

		Point tile_index = new Point(relative.getX()/tile_size, relative.getY()/tile_size);

		game_score += board.userMove(tile_index);
		state_panel.updateScore(game_score);

		return;
	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}

//small class is only used for data packaging to return all relevant data about
//how large a board is and where it is located
public class THBoardSizer{

	int tile_size;
	Point board_position;

	public THBoardSizer(int tile_size, Point board_position){
		this.tile_size = tile_size;
		this.board_position = board_position;
	}

	public int getTileSize(){
		return tile_size;
	}

	public Point getPosition(){
		return board_position
	}
}
