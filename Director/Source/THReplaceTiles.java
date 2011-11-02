
import java.util.*;

public class THReplaceTiles extends THProcess{
	
	public THReplaceTiles(THBoard board){
		super(board);
	}
	
	void run(){
		((THBoard)process_object).replaceTiles();
	}
}