
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class THHighScoreView extends JPanel implements ActionListener{
	
	Main window;
	JTextArea text;
	JButton return_button;
	TextField input;
	String[] names;
	int[] scores;
	int current_score;
	int score_index;
	
	public THHighScoreView(Main window){
		
		this.window = window;
		
		names = new String[10];
		scores = new int[10];
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("highscores.txt")));		
		
		try{
			for(int i=0; i<10; i++){
				names[i] = in.readLine();
				String temp = in.readLine();
				scores[i] = Integer.valueOf(temp);
			}
		}catch(IOException e){
			System.out.println("Highscore file corrupted, exiting...");
			System.exit(1);
		}
		
		input = new TextField("", 40);
		input.addActionListener(this);
		input.setEnabled(false);
		
		text= new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setOpaque(false);
		text.setEditable(false);
		printScores();
		
		return_button = new JButton("Main Menu");
		return_button.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0.7;
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(input, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0.3;
		gbc.insets = new Insets(0,200,0,200);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(text, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(return_button, gbc);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == return_button){
			if(input.isEnabled()){
				for(int i=8; i>=score_index; i--){
					names[i+1] = names[i];
					scores[i+1] = scores[i];
				}
				
				names[score_index] = "Anonymous";
				scores[score_index] = current_score;
				input.setEnabled(false);
				printScores();
				saveScores();
				repaint();
			}
			window.goToMenu();
		}
		else{
			for(int i=8; i>=score_index; i--){
				names[i+1] = names[i];
				scores[i+1] = scores[i];
			}
			
			names[score_index] = input.getText();
			scores[score_index] = current_score;
			input.setEnabled(false);
			printScores();
			saveScores();
			repaint();
		}
	}
	
	public void setScore(int score){
		current_score = score;
		
		score_index = 0;
		while(score_index < 10 && current_score <= scores[score_index]){
			score_index++;
		}
		
		if(score_index < 10){
			input.setText("New Highscore " + Integer.toString(current_score) + ", enter name here");
			input.setEnabled(true);
		}
		else{
			input.setText("No new highscores");
		}
	}
	
	protected void printScores(){
		
		text.setText("");
		String eol = System.getProperty("line.separator");
		
		text.append("High Scores:" + eol);
		text.append("------------" + eol + eol);
		for(int i=0; i<10; i++){
			text.append(names[i] + " -- " + Integer.toString(scores[i]) + eol);
		}
	}
	
	protected void saveScores(){
	
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new FileWriter("highscores.txt"));
		} catch (FileNotFoundException e){
			System.out.println("highscores.txt corrupted, cannot save scores after program termination");
		} catch (IOException e){
			System.out.println("highscores.txt corrupted, cannot save scores after program termination");
		}
		
		try{
			for(int i=0; i<10; i++){
				out.write(names[i]);
				out.newLine();
				out.write(Integer.toString(scores[i]));
				out.newLine();
				System.out.println("Wrote entry...");
			}
		}catch(IOException e){
			System.out.println("highscores.txt corrupted, cannot save scores after program termination");
		}
		
		try{
			out.flush();
		} catch(IOException e){
			System.out.println("flush failed");
		}
		
		System.out.println("Saved data");
	}
}