
import java.util.*;

public class THBoardMarkChain extends THProcess{
    
    protected Vector<THTile> active_tiles;
    protected int chain_length;
    
    public THBoardMarkChain(THBoard board, Vector<THTile> active_tiles, int chain_length){
        super(board);
        this.active_tiles = active_tiles;
        this.chain_length = chain_length;
    }
    
    public void run(){
        ((THBoard)process_object).markChain(active_tiles, chain_length);
    }
}