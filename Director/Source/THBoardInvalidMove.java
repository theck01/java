
public class THBoardInvalidMove extends THProcess{

	public THBoardInvalidMove(THBoard board){
		super(board);
	}
	
	public void run(){
		((THBoard)process_object).invalidMove();
	}
}