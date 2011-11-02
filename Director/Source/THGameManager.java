
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

//Controller. Reacts to mouse events, timer events, and modifies the views
//and models accordingly. Responsible for starting and ending the game

//!!!!!!!!!!Pause logic, game inactive logic
public class THGameManager extends JComponent implements MouseListener, ActionListener{

	protected THProcessScheduler ps;
	protected THBoard game_board;
	protected THStatePanel state_panel;
	protected boolean process_active;
	protected boolean game_paused;
	protected int time_left;
	protected int game_score;
	
	
	//small class is only used for data packaging to return all relevant data about
	//how large a board is and where it is located
	protected class THBoardSizer{

		private int tile_size;
		private Point board_position;

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
	

	public THGameManager(THStatePanel state){
		
		ps = new THProcessScheduler();
		ps.addTimerListener(this);
		
		//the new board is just for display. Another will be created upon the
		//start of a new game
		game_board = new THBoard(this, ps);
		
		state_panel = state;
		
		process_active = false;
		game_paused = true;
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
	
		game_board = new THBoard(this, ps);

		game_score = 0;
		state_panel.updateScore(game_score);
		
		time_left = THConstants.game_duration;
		state_panel.updateTime(time_left);

		game_paused = false;
		ps.start();

		repaint();
		
		return;
	}

	public void endGame(){
		ps.halt();
		game_paused = true;
		repaint();
		return;
	}

	public void pause(){
		ps.halt();
		game_paused = true;
		return;
	}

	public void resume(){
		ps.start();
		game_paused = false;
		return;
	}
	
    public void updateScore(int score){
        game_score += score;
        state_panel.updateScore(game_score);
    }
	
	public void paintComponent(Graphics g){	
		THBoardSizer attributes = determineBoardAttributes();
		game_board.draw(attributes.getPosition(), attributes.getTileSize(), g);
	}

	//action comes from ps, occurs every 0.001s
	public void actionPerformed(ActionEvent e){
	
		time_left--;
		state_panel.updateTime(time_left);
		
		process_active = ps.isActive();
		
		if(time_left == 0){
			endGame();	
		}
		
		return;
	}

	public void mouseClicked(MouseEvent e){

		//if the game is paused or processing, do not allow
		//user clicks
		if(game_paused || process_active){
			return;
		}		
		
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
			return;
		}

		System.out.println("Click in tile at position: " + tile_index.getX() + " across and " + tile_index.getY() + " down");

		game_board.userMove(tile_index);

		repaint();

		return;
	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
}







