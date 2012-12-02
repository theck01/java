
public class THBoardReplaceTiles extends THProcess{
	
	public THBoardReplaceTiles(THBoard board){
		super(board);
	}
	
	void run(){
		((THBoard)process_object).replaceTiles();
	}
}