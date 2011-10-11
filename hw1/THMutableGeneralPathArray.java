
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

//Data structure for a variable sized array
public class THMutableGeneralPathArray{

	//color codes:
	//0 = RED
	//1 = BLUE
	//2 = YELLOW

	GeneralPath[] paths;
	Color[] path_colors;
	int capacity;
	int num_items;

	public THMutableGeneralPathArray(){
		paths = new GeneralPath[4];
		path_colors = new Color[4];
		capacity = 4;
		num_items = 0;
	}

	public void insert(GeneralPath new_path, Color new_path_color){

		System.out.println("Inserting with num_items = " + num_items);

		//resizes array of GeneralPaths if required
		if(num_items == capacity){

			System.out.println("Resizing");
			int new_capacity = 2*capacity;
			GeneralPath[] new_array = new GeneralPath[new_capacity];
			Color[] new_colors = new Color[new_capacity];

			//loop copies all paths from paths to new_array
			for(int i=0; i<num_items; i++){
				new_array[i] = paths[i];
				new_colors[i] = path_colors[i];
			}

			paths = new_array;
			path_colors = new_colors;
			capacity = new_capacity;
			System.out.println("Resized");
		}

		paths[num_items] = new_path;
		path_colors[num_items] = new_path_color;
		num_items = num_items + 1;
	}

	public GeneralPath getPath(int i){
	
		if(i>=0 && i<num_items){
			return paths[i];
		}

		return null;
	}

	public Color getColor(int i){

		if(i>=0 && i<num_items){
			return path_colors[i];
		}

		//if code is out of range return default of blue
		return Color.blue;
	}

	public int getArrayLength(){
		return num_items;
	}

	public void clear(){
		paths = new GeneralPath[4];
		path_colors = new Color[4];
		capacity = 4;
		num_items = 0;
	}	
}
