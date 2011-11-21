
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

//Controller. Reacts to mouse events, timer events, and modifies the views
//and models accordingly. Responsible for starting and ending the game
public class THGameManager extends JComponent implements MouseListener, ActionListener{

	protected THProcessScheduler ps;
	protected THBoard game_board;
	protected THStatePanel state_panel;
	protected THControlPanel control_panel;
	protected THInfoPanel info;
	
	protected boolean game_active;
	protected boolean process_active;
	protected boolean game_paused;
	protected boolean hide_board;
	
	protected int time_left;
	protected int game_score;
	protected int num_moves;
	
	
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
	

	public THGameManager(THStatePanel state, THInfoPanel info){
		
		ps = new THProcessScheduler();
		ps.addTimerListener(this);
		
		state_panel = state;
		this.info = info;
		game_board = null;
		process_active = false;
		game_paused = true;
		game_active = false;
		hide_board = true;
		
		time_left = THConstants.game_duration;
		game_score = 0;
		num_moves = 0;
		
		addMouseListener(this);

		repaint();
	}
	
	public void setControlPanel(THControlPanel controls){
		control_panel = controls;
		if(isActive()){
			controls.gameActiveControls();
		}
		else{
			controls.gameInactiveControls();
		}
	}

	//function determines the size of a board to draw using the size of the window
	//available
	protected THBoardSizer determineBoardAttributes(){

		int tile_size;
		int board_x;
		int board_y;

		int num_columns = THConstants.board_width;
		int num_rows = THConstants.board_height;
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
	
	public void newBoard(){
		THBoardSizer sizes = determineBoardAttributes();
		game_board = new THBoard(this, ps, sizes.getPosition(), sizes.getTileSize(), info);
		info.setBoard(game_board);
		hide_board = true;
		repaint();
	}

	public void startGame(){
	
		newBoard();

		game_score = 0;
		num_moves = 0;
		time_left = THConstants.game_duration;
		state_panel.updateScore(game_score);
		state_panel.updateMoves(num_moves);
		state_panel.updateTime(time_left);

		game_paused = false;
		game_active = true;
		hide_board = false;

		control_panel.gameActiveControls();
		
		ps.start();

		repaint();
		
		return;
	}

	public void endGame(){
	
		//if the process scheduler has additional events (such as animation)
		//delay ending the game until the animation completes. Does not affect
		//playtime, as the user cannot manipulate board while the process
		//scheduler is active
		if(ps.processesQueued()){
			ps.add(new THGameManagerEndGame(this), 0);
			return;
		}
	
		ps.halt();
		
		control_panel.gameInactiveControls();
		
		game_paused = true;
		game_active = false;
		
		repaint();
		
		return;
	}

	public void pause(){
		ps.halt();
		game_paused = true;
		repaint();
		return;
	}

	public void resume(){
		ps.start();
		game_paused = false;
		repaint();
		return;
	}
	
	//returns true is the game is currently playable
	public boolean isActive(){
		return (game_active && !game_paused);
	}
	
	public boolean hideBoard(){
		return hide_board;	
	}
	
    public void updateScore(int score){
        game_score += score;
        state_panel.updateScore(game_score);
    }
	
	public void incNumMoves(){
		num_moves++;
		state_panel.updateMoves(num_moves);
	}
	
	public void paintComponent(Graphics g){
	
		//used to initialize board at the start of the game
		if(game_board == null){
			newBoard();
		}
		else{
			THBoardSizer attributes = determineBoardAttributes();
			game_board.size(attributes.getPosition(), attributes.getTileSize());
		}
		game_board.draw(g);
	}

	//action comes from ps, occurs every 0.001s
	public void actionPerformed(ActionEvent e){
	
		time_left--;
		state_panel.updateTime(time_left);
		
		process_active = ps.isActive();
		
		if(time_left == 0){
			ps.add(new THGameManagerEndGame(this), 0);
			
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







