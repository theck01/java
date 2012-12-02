
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main extends JFrame{

	//subviews
	protected THFreePlayView fpv;
	protected THMenuView mv;
	protected THAboutView av;
	protected THContestView cv;
	protected THHighScoreView hsv;
	
	//content
	Container content = getContentPane();
	
	//view ids
	protected static final String MAIN_MENU	= "mainmenu";
	protected static final String CONTEST = "contest";
	protected static final String FREE_PLAY = "freeplay";
	protected static final String ABOUT = "about";
	protected static final String HIGHSCORE = "highscore";

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

		mv = new THMenuView(this);
		cv = new THContestView(this);
		fpv = new THFreePlayView(this);
		av = new THAboutView(this);
		hsv = new THHighScoreView(this);
		
		content.add(mv, MAIN_MENU);
		content.add(cv, CONTEST);
		content.add(fpv, FREE_PLAY);
		content.add(av, ABOUT);
		content.add(hsv, HIGHSCORE);
		
		setVisible(true);
	}
	
	public void goToMenu(){
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, MAIN_MENU);
	}
	
	public void goToFreePlay(){
		
		//Initialize freeplay constants
		THConstants.infiniteTime();
	
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, FREE_PLAY);
	}
	
	public void goToAbout(){
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, ABOUT);
	}
	
	public void goToContest(){
		
		//Initialize contest constants;
		THConstants.contestTime(); 
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, CONTEST);
	}
	
	public void goToHighScore(){
		goToHighScore(0);		
	}
	
	public void goToHighScore(int score){
		hsv.setScore(score);
		CardLayout cards = (CardLayout)content.getLayout();
		cards.show(content, HIGHSCORE);
	}
}
