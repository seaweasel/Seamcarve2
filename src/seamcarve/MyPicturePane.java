package seamcarve;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import support.seamcarve.*;


/** 
 * This class is your seam carving picture pane.  It is a subclass of PicturePane,
 * an abstract class that takes care of all the drawing, displaying, carving, and
 * updating of seams and images for you.  Your job is to override the abstract
 * method of PicturePanel that actually finds the lowest cost seam through
 * the image.
 * 
 * See method comments and handouts for specifics on the steps of the seam carving algorithm.
 *
 * 
 * @version 01/07/2017
 */

public class MyPicturePane extends PicturePane {
	private int[][] _pixelArray;
	private String _picture;
	
	/**
	 * The constructor accepts an image filename as a String and passes
	 * it to the superclass for displaying and manipulation.
	 * 
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);
		String defaultFileName = "/Users/Akhil/Pictures/honey.jpg";
		defaultFileName = filename;
		filename = _picture;
		_pixelArray = new int[getPicWidth()][getPicWidth()];
		calculateColorImportance();
	}

	
	/**
	 * In this method, you'll implement the dynamic programming algorithm
	 * that you learned on the first day of class to find the lowest cost seam from the top
	 * of the image to the bottom. BEFORE YOU START make sure you fully understand how the algorithm works
	 * and what it's doing.
	 * See the handout for some helpful resources and use hours/piazza to clarify conceptual blocks
	 * before you attempt to write code.
	 * 
	 * This method returns an array of ints that represents a seam.  This size of this array
	 * is the height of the image.  Each entry of the seam array corresponds to one row of the 
	 * image.  The data in each entry should be the x coordinate of the seam in this row.  
	 * For example, given the below "image" where s is a seam pixel and - is a non-seam pixel
	 * 
	 * - s - -  
	 * s - - -  
	 * - s - -  
	 * - - s -  
	 * 
	 * 
	 * the following code will properly return a seam:
	 * 
	 * int[] currSeam = new int[4];
	 * currSeam[0] = 1;
	 * currSeam[1] = 0;
	 * currSeam[2] = 1;
	 * currSeam[3] = 2;
	 * return currSeam;
	 * 
	 *
	 * @return the lowest cost seam of the current image
 	 */
	protected int[] findLowestCostSeam() {
		// TODO: Your code here
		return null;
	}
	
	public void calculateColorImportance(){
		for (int i = 0; i < getPicWidth()-1; i++){
			for (int j = 0; j < getPicHeight()-1;j++){
			int	currPixel = _pixelArray[i][j];
			// use helper method to calculate the color value 
			Color currColor = getPixelColor(i,j);
			
			//int currColorValue = (int) (currColor.getRed() + currColor.getGreen() + currColor.getBlue());
			int currColorValue = 0;
			// does this method actually compute the color value?
			currColorValue = (int) (currColor.getRed() + currColor.getGreen() + currColor.getBlue());
			int right = _pixelArray[i+1][j];
			int left = _pixelArray[i-1][j];
			int up = _pixelArray[i][j-1];
			int down = _pixelArray[i][j+1];
			
			//getColorValue(currColorValue, currColor);
			
			}
		}
	}
	
	public int getColorValue(int sum, Color color){
		// why is the sum of the colors a double?
			 sum =  (int) (color.getRed() + color.getGreen() + color.getBlue());
				return sum;
			}
		
	public void getColorSum(int i, int j, int cur){
		for (i = 0; i < getPicWidth()-1; i++){
			for (j=0; j < getPicHeight()-1; j++){
				int curr = _pixelArray[i][j];
				// hey !
				 
			}
		}
	}
		
	

}
