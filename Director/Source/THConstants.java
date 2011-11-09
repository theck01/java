
public class THConstants{

    //all time units are in milliseconds
    static public int move_delay = 250;
	static public int invalid_delay = 150;
	
	//Board size constants. May be altered in game
	static public int board_width = 7;	//array sizing
	static public int board_height = 7;
	
	//Board boundry sizes
	static public int max_width = 10;
	static public int min_width = 5;
	static public int max_height = 10;
	static public int min_height = 5; 
	 
	
	//Points and game constants
	static public int stop_points = 100;
	static public int direction_points = 10;
	static public int min_chain_length = 3;
	
	static public int game_duration = 60000; //1 min
	static public int timer_interval = 1; //1 ms

	private THConstants(){}
	
	static public void setBoardHeight(int new_height){
		if(new_height > max_height){
			board_height = max_height;
		}
		else if(new_height < min_height){
			board_height = min_height;
		}
		else{
			board_height = new_height;
		}
	}
	
	static public void setBoardWidth(int new_width){
		if(new_width > max_width){
			board_width = max_width;
		}
		else if(new_width < min_width){
			board_width = min_width;
		}
		else{
			board_width = new_width;
		}
	}
}
