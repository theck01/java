
public class THConstants{

    //all time units are in milliseconds
    static final public int move_delay = 250;
	static final public int invalid_delay = 150;
	static final public int shrink_delay = 5;
	static final public int animation_delay = 10;
	
	//Board size constants. May be altered in game
	static public int board_width = 7;	//array sizing, changeable
	static public int board_height = 7;
	
	//Board boundry sizes
	static final public int max_width = 10;
	static final public int min_width = 5;
	static final public int max_height = 10;
	static final public int min_height = 5; 
	 
	
	//Points and game constants
	static final public int stop_points = 100;
	static final public int direction_points = 10;
	static final public int split_points = 0;
	static final public int contest_height = 7;
	static final public int contest_width = 7;
	static final public int contest_duration = 60000; //1 min
	static final public int freeplay_duration = -1; //Infinite
	static final public int timer_interval = 1; //1 ms
	static public int game_duration = 60000;

	//animation sizes
	static final public int min_tile_size = 6; //in pixels
	static final public int animation_distance = 3; //pixels to move per 0.01 seconds
	
	//tile id codes
	static final public int left_id = 0;
	static final public int right_id = 1;
	static final public int up_id = 2;
	static final public int down_id = 3;
	static final public int stop_id = 4;
	static final public int splitv_id = 5;
	static final public int splith_id = 6;

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
	
	static public void infiniteTime(){
		game_duration = freeplay_duration;
	}
	
	static public void contestTime(){
		game_duration = contest_duration;
	}
}
