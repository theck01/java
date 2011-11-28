
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

//Changes the dimensions of the board when the game is inactive
//Controls enabled when the game is not active
public class THDimSpinners extends JPanel implements ChangeListener{
	
	JSpinner rows;
	JSpinner columns;
	THGameManager game;
		
	public THDimSpinners(THGameManager game){
		
		this.game = game;
		
		setLayout(new GridLayout(2,2));
		
		JLabel r_label = new JLabel("Rows:");
		r_label.setHorizontalAlignment(JLabel.RIGHT);
		JLabel c_label = new JLabel("Columns:");
		c_label.setHorizontalAlignment(JLabel.RIGHT);
		
		rows = new JSpinner(new SpinnerNumberModel(THConstants.board_height, THConstants.min_height, THConstants.max_height, 1));
		rows.addChangeListener(this);
		columns = new JSpinner(new SpinnerNumberModel(THConstants.board_width, THConstants.min_width, THConstants.max_width, 1));
		columns.addChangeListener(this);
		
		add(r_label);
		add(rows);
		add(c_label);
		add(columns);
	}
	
	public void stateChanged(ChangeEvent e){
		THConstants.setBoardHeight(((Integer)rows.getValue()).intValue());
		THConstants.setBoardWidth(((Integer)columns.getValue()).intValue());
		game.newBoard();
	}
	
	public void setValues(int width, int height){
		rows.setValue(new Integer(height));
		columns.setValue(new Integer(width));
	}
	
	public void setEnabled(boolean enabled){
		rows.setEnabled(enabled);
		columns.setEnabled(enabled);
	}
}
