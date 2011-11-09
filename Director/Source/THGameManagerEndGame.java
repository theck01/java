
public class THGameManagerEndGame extends THProcess{
	
	public THGameManagerEndGame(THGameManager game){
		super(game);
	}
	
	void run(){
		((THGameManager)process_object).endGame();
	}
}