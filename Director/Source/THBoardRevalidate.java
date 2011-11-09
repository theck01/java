
public class THBoardRevalidate extends THProcess{
	
	public THBoardRevalidate(THBoard board){
		super(board);
	}
	
	public void run(){
		((THBoard)process_object).revalidate();
	}
}