public class THBoardAnimate	extends THProcess{
	
	public THBoardAnimate(THBoard board){
		super(board);
	}
	
	public void run(){
		((THBoard)process_object).animate();
	}
}