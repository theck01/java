
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Controller. Reacts to mouse events, timer events, and modifies the views
//and models accordingly. Responsible for starting and ending the game
public class THThreadedGameManager extends JComponent implements MouseListener, ActionListener, Runnable{

	protected THBoard game_board;
	protected THStatePanel state_panel;
	protected Timer game_timer;
	protected boolean game_active;
	protected int time_left;
	protected Point selected_tile_index;

	//last mouse event stored for use in threading
	protected MouseEvent e;
	
	//time_count stores the duration of the game in hundredths of a second
	protected int time_count;
	
	//small class is only used for data packaging to return all relevant data about
	//how large a board is and where it is located
	protected class THBoardSizer{

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
			return board_position;
		}
	}

	public THThreadedGameManager(THStatePanel state){
		//the new board is just for display. Another will be created upon the
		//start of a new game
		game_board = new THBoard(this);
		state_panel = state;
		state.setGameManager(this);
		game_timer = new Timer(THConstants.timer_interval, this);
		game_active = false;
		time_left = THConstants.game_duration;
		game_score = 0;
		addMouseListener(this);

		repaint();
	}

	//function determines the size of a board to draw using the size of the window
	//available
	protected THBoardSizer determineBoardAttributes(){

		int tile_size;
		int board_x;
		int board_y;

		int num_columns = (int) game_board.getArraySize().getX();
		int num_rows = (int) game_board.getArraySize().getY();
		int screen_width = getWidth();
		int screen_height = getHeight();
		
		//nested if statements determine tile size by dividing the
		//narrowest screen dimensions by the largest of the array dimension
		if(screen_width > screen_height){
			if(num_columns > num_rows){
				tile_size = screen_height/num_columns;
			}
			else{
				tile_size = screen_height/num_rows;
			}
		}
		else{
			if(num_columns > num_rows){
				tile_size = screen_width/num_columns;
			}
			else{
				tile_size = screen_width/num_rows;
			}
		}

		board_x = (screen_width%(num_columns*tile_size))/2;
		board_y = (screen_height%(num_rows*tile_size))/2;

		return new THBoardSizer(tile_size, new Point(board_x, board_y));
	}

	public void startGame(){
	
		game_board = new THBoard(this);

		game_score = 0;
		state_panel.updateScore(game_score);
		time_left = THConstants.game_duration;
		state_panel.updateTime(time_left);

		game_active = true;
		time_count = 0;
		game_timer.start();

		repaint();
		
		return;
	}

	public void endGame(){
		game_timer.stop();
		game_active = false;
		repaint();
		return;
	}

	public void timeDelay(int hundredths_seconds){
		int current_time = time_count;
		while(current_time < time_count+hundredths_seconds){}
		return;
	}	
	
	public void paintComponent(Graphics g){	
		THBoardSizer attributes = determineBoardAttributes();
		game_board.draw(attributes.getPosition(), attributes.getTileSize(), g);
	}

	//action comes from timer, occurs every 0.01s
	public void actionPerformed(ActionEvent e){
	
		time_count++;
		time_left = THConstants.game_duration - time_count/100;
		state_panel.updateTime(time_left);
		if(time_left == 0){
			endGame();	
		}
		
		return;
	}

	public void mouseClicked(MouseEvent e){

		//if the game has not been started, do not process user clicks
		if(!game_active){
			return;
		}

		//sets game to inactive so that user cannot manipulate board
		//while processing occurs
		game_active = false;
		
		
		THBoardSizer attributes = determineBoardAttributes();
		Point array_dim = game_board.getArraySize();

		int tile_size = attributes.getTileSize();
		int board_x = (int)attributes.getPosition().getX();
		int board_y = (int)attributes.getPosition().getY();
		
		Point relative = new Point((int)e.getX() - board_x, (int)e.getY() - board_y);

		Point tile_index = new Point(((int)relative.getX())/tile_size, ((int)relative.getY())/tile_size);

		//if the click was out of array bounds, then do nothing
		if(tile_index.getX() < 0 || tile_index.getX() >= array_dim.getX()|| tile_index.getY() < 0 || tile_index.getY() >= array_dim.getY()){
			System.out.println("Click out of board boundary");
			game_active = true;
			return;
		}

		System.out.println("Click in tile at position: " + tile_index.getX() + " across and " + tile_index.getY() + " down");

		//starts new thread that takes care of all processesing that needs
		//to occur to process the move while hopefully allowing for Timer
		//pulses to continue to be recieved
		(new Thread(this)).start();
		
		System.out.println("Thread created, move is being processed...");

		repaint();

		return;
	}

	public void run(){
		game_score += game_board.userMove(selected_tile_index);
		state_panel.updateScore(game_score);
		game_active = true;
		System.out.println("Move processed, game is again active")
		return;
	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
}
