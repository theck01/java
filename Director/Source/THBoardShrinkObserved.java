
public class THBoardShrinkObserved	extends THProcess{
	
	public THBoardShrinkObserved(THBoard board){
		super(board);
	}
	
	public void run(){
		((THBoard)process_object).shrinkObserved();
	}
}
