
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main extends JFrame{

	//subviews
	protected THFreePlayView fpv;
	protected THMenuView mv;
	protected THAboutView av;
	
	//content
	Container content = getContentPane();
	
	//view ids
	protected static final String FREE_PLAY = "freeplay";
	protected static final String MAIN_MENU	= "mainmenu";
	protected static final String ABOUT = "about";

	public static void main(String[] args){
		new Main();
	}

	public Main(){
	
		setLocation(100,100);
		setSize(700,600);
		setMinimumSize(new Dimension(600,500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		content = getContentPane();
		content.setLayout(new CardLayout());

		fpv = new THFreePlayView(this);
		mv = new THMenuView(this);
		av = new THAboutView(this);
		
		content.add(mv, MAIN_MENU);
		content.add(fpv, FREE_PLAY);
		content.add(av, ABOUT);
		
		setVisible(true);
	}
	
	public void goToMenu(){
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, MAIN_MENU);
	}
	
	public void goToFreePlay(){
		
		//Initialize freeplay constants
		THConstants.game_duration = -1;
		THConstants.setBoardHeight(7);
		THConstants.setBoardWidth(7);
	
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, FREE_PLAY);
	}
	
	public void goToAbout(){
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, ABOUT);
	}
}
