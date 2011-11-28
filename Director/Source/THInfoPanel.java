
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class THInfoPanel extends JPanel implements ItemListener, ActionListener{
	
	THTile tile;
	JLabel	array_p, target_p, actual_p, size, actual_size;
	THBoard board;
	JCheckBox left, right, up, down, stop, splitv, splith;
	JButton deselect;
	
	public THInfoPanel(){
		
		board = null;
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
				
		//Label addition
		array_p = new JLabel();
		array_p.setHorizontalAlignment(JLabel.LEFT);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty=0;
		gbc.fill = GridBagConstraints.NONE;
		add(array_p, gbc);
		
		actual_p = new JLabel();
		actual_p.setHorizontalAlignment(JLabel.LEFT);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty=0;
		gbc.fill = GridBagConstraints.NONE;
		add(actual_p, gbc);
		
		target_p = new JLabel();
		target_p.setHorizontalAlignment(JLabel.LEFT);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty=0;
		gbc.fill = GridBagConstraints.NONE;
		add(target_p, gbc);
		
		size = new JLabel();
		size.setHorizontalAlignment(JLabel.LEFT);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty=0;
		gbc.fill = GridBagConstraints.NONE;
		add(size, gbc);
		
		actual_size = new JLabel();
		actual_size.setHorizontalAlignment(JLabel.LEFT);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty=0;
		gbc.fill = GridBagConstraints.NONE;
		add(actual_size,gbc);
		
		//Checkbox subpanel creation
		ButtonGroup g = new ButtonGroup();
		JPanel box_panel = new JPanel();
		box_panel.setLayout(new GridLayout(3,3));
		
		left = new JCheckBox("Left", false);
		g.add(left);
		left.addItemListener(this);
		box_panel.add(left);
		
		right = new JCheckBox("Right", false);
		g.add(right);
		right.addItemListener(this);
		box_panel.add(right);
		
		up = new JCheckBox("Up", false);
		g.add(up);
		up.addItemListener(this);
		box_panel.add(up);
		
		splith = new JCheckBox("Split Hor.", false);
		g.add(splith);
		splith.addItemListener(this);
		box_panel.add(splith);
		
		splitv = new JCheckBox("Split Vert.", false);
		g.add(splitv);
		splitv.addItemListener(this);
		box_panel.add(splitv);
		
		down = new JCheckBox("Down", false);
		g.add(down);
		down.addItemListener(this);
		box_panel.add(down);
		
		stop = new JCheckBox("Stop", false);
		g.add(stop);
		stop.addItemListener(this);
		box_panel.add(stop);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		add(box_panel,gbc);
		
		//Button creation
		deselect = new JButton("Deselect Tile");
		deselect.addActionListener(this);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		add(deselect,gbc);
		
		noneSelected();
	}
	
	public void setBoard(THBoard board){
		this.board = board;
	}
	
	public void noneSelected(){
		tile = null;
		array_p.setText("Array Position -- NA");
		actual_p.setText("Actual Position -- NA");
		target_p.setText("Target Position -- NA");
		size.setText("Tile Size -- NA");
		actual_size.setText("Actual Size -- NA");
		disableControls();
		repaint();
	}
	
	public void tileSelected(THTile tile){
	
		this.tile = tile;
		Point i = tile.getArrayPosition();
		Point a = tile.getActualPosition();
		Point t = tile.getTargetPosition();
		int s = tile.getTileSize();
		int as = tile.getActualSize();
	
		array_p.setText("Array Position -- " + Integer.toString((int)i.getX()) + "," + Integer.toString((int)i.getY()));
		actual_p.setText("Array Position -- " + Integer.toString((int)a.getX()) + "," + Integer.toString((int)a.getY()));
		target_p.setText("Array Position -- " + Integer.toString((int)t.getX()) + "," + Integer.toString((int)t.getY()));
		size.setText("Tile Size -- " + Integer.toString(s));
		actual_size.setText("Actual Size -- " + Integer.toString(as));
		
		switch(tile.getID()){
		
				case THConstants.left_id:
					left.setSelected(true);
					break;
				case THConstants.right_id:
					right.setSelected(true);
					break;
				case THConstants.up_id:
					up.setSelected(true);
					break;
				case THConstants.down_id:
					down.setSelected(true);
					break;
				case THConstants.splith_id:
					splith.setSelected(true);
					break;
				case THConstants.splitv_id:
					splitv.setSelected(true);
					break;
				default:
					stop.setSelected(true);
					break;
		}
		
		enableControls();
	}
	
	protected void disableControls(){
		
		left.setSelected(false);
		right.setSelected(false);
		up.setSelected(false);
		down.setSelected(false);
		stop.setSelected(false);
		splitv.setSelected(false);
		splith.setSelected(false);
		
		left.setEnabled(false);
		right.setEnabled(false);
		up.setEnabled(false);
		down.setEnabled(false);
		stop.setEnabled(false);
		splitv.setEnabled(false);
		splith.setEnabled(false);
		
		deselect.setEnabled(false);
	}
	
	protected void enableControls(){
		left.setEnabled(true);
		right.setEnabled(true);
		up.setEnabled(true);
		down.setEnabled(true);
		stop.setEnabled(true);
		splith.setEnabled(true);
		splitv.setEnabled(true);
		deselect.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(tile != null){
			board.deselect();
		}
	}
	
	public void itemStateChanged(ItemEvent e){
	
		if(e.getStateChange() == ItemEvent.SELECTED){
		
			System.out.println("Item selected");
			
			String sel = ((JCheckBox)e.getItem()).getText();
			Point i = tile.getArrayPosition();
			Point a = tile.getActualPosition();
			Point t = tile.getTargetPosition();
			int s = tile.getTileSize();
			int as = tile.getActualSize();
			
			if(sel.compareTo("Left") == 0){
				board.replaceTile(tile, THConstants.left_id);
			}
			else if(sel.compareTo("Right") == 0){
				board.replaceTile(tile, THConstants.right_id);
			}
			else if(sel.compareTo("Up") == 0){
				board.replaceTile(tile, THConstants.up_id);
			}
			else if(sel.compareTo("Down") == 0){
				board.replaceTile(tile, THConstants.down_id);
			}
			else if(sel.compareTo("Split Hor.") == 0){
				board.replaceTile(tile, THConstants.splith_id);
			}
			else if(sel.compareTo("Split Vert.") == 0){
				board.replaceTile(tile, THConstants.splitv_id);
			}
			else{
				board.replaceTile(tile, THConstants.stop_id);
			}
		}
	}
	
	public void setTile(THTile t){
		tile = t;
	}
}