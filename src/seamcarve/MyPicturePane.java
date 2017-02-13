package seamcarve;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import support.seamcarve.*;

/**
 * This class is your seam carving picture pane. It is a subclass of
 * PicturePane, an abstract class that takes care of all the drawing,
 * displaying, carving, and updating of seams and images for you. Your job is to
 * override the abstract method of PicturePanel that actually finds the lowest
 * cost seam through the image.
 * 
 * See method comments and handouts for specifics on the steps of the seam
 * carving algorithm.
 *
 * 
 * @version 01/07/2017
 */

public class MyPicturePane extends PicturePane {
	private int[][] _pixelArray;
	private int[][] _colorImportance;
	private String _picture;
	private int[][] _costsArray;
	private int[][] _dirsArray;

	/**
	 * The constructor accepts an image filename as a String and passes it to
	 * the superclass for displaying and manipulation.
	 * 
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);
		String defaultFileName = "/Users/Akhil/Pictures/honey.jpg";
		defaultFileName = filename;
		filename = _picture;
		
		_pixelArray = new int[getPicHeight()][getPicWidth()];
		_colorImportance = new int[getPicHeight()][getPicWidth()];
		_costsArray = new int[getPicHeight()][getPicWidth()];
		_dirsArray = new int[getPicHeight()-1][getPicWidth()];
		
//		calculateColorImportance();
//		calcCostsAndDirs();
		@SuppressWarnings("unused")
		int x = 0;
		
	}

	/**
	 * In this method, you'll implement the dynamic programming algorithm that
	 * you learned on the first day of class to find the lowest cost seam from
	 * the top of the image to the bottom. BEFORE YOU START make sure you fully
	 * understand how the algorithm works and what it's doing. See the handout
	 * for some helpful resources and use hours/piazza to clarify conceptual
	 * blocks before you attempt to write code.
	 * 
	 * This method returns an array of ints that represents a seam. This size of
	 * this array is the height of the image. Each entry of the seam array
	 * corresponds to one row of the image. The data in each entry should be the
	 * x coordinate of the seam in this row. For example, given the below
	 * "image" where s is a seam pixel and - is a non-seam pixel
	 * 
	 * - s - - s - - - - s - - - - s -
	 * 
	 * 
	 * the following code will properly return a seam:
	 * 
	 * int[] currSeam = new int[4]; currSeam[0] = 1; currSeam[1] = 0;
	 * currSeam[2] = 1; currSeam[3] = 2; return currSeam;
	 * 
	 *
	 * @return the lowest cost seam of the current image
	 */
	protected int[] findLowestCostSeam() {
		calculateColorImportance();
		calcCostsAndDirs();
		// Returns index of min in top row
		int min_col = findMin(_costsArray[0]);
		//lowest cost seam
		int[] seam = new int[getPicHeight()];
		seam[0] = _costsArray[0][min_col];
		
		for(int row = 0; row < getPicHeight()-1; row++){
			seam[row+1] = seam[row] + _dirsArray[row][seam[row]];
		}
		
		return seam;
	}
	
	/*
	 * return index of lowest element in int array
	 */
	public int findMin(int[] nums){
		int minValue = nums[0];
		int minDex = 0;
		
		for(int i = 0; i < nums.length; i++){
			if(nums[i] < minValue){
				minValue = nums[i];
				minDex = i;
			}
		}
		
		return minDex;
	}

	public void calculateColorImportance() {
		//replace i/j with col/row
		//swap getPicWidth() a& getPicHeight() below
		for (int row = 0; row < getPicHeight(); row++) {
			for (int col = 0; col < getPicWidth(); col++) {
				//int currPixel = _pixelArray[row][col];

				// get adjacent pixels:
				ArrayList<Point> neighbors = getAdjacentPixels(row, col);

				// iterate on neighbors to calculate color importance
				/*
				 * _colorImportance[i][j] = something
				 */
				int totalDiff = 0;
				for (Iterator<Point> n = neighbors.iterator(); n.hasNext();) {
					Point neighbor = n.next();
					// TODO -- write code here
					// calculate difference magnitude between pixel and neighbor
					// here

					Color currColor = getPixelColor(row, col);
					int colorValue = (int) (255* currColor.getRed() + 255 * currColor.getBlue() + 255 * currColor.getGreen());

					Color neighbColor = getPixelColor((int) neighbor.getY(), (int) neighbor.getX());
					int neighbColorValue = (int) (255 * neighbColor.getRed() + 255 * neighbColor.getBlue()
							+ 255 * neighbColor.getGreen());
					// absolute value of difference between two
					int diff = Math.abs(colorValue - neighbColorValue);

					// put in values array
					
					totalDiff  += diff;
					int x = 0;
				}
				_colorImportance[row][col] = totalDiff;
				int x = 1;
				
			}
		}
	}

	public ArrayList<Point> getAdjacentPixels(int row, int col) {
		/*
		 * return a list of the pixels adjacent to pixel(row, col)
		 * 
		 * can vary between 2-4
		 */
		// TODO -- do code here
		ArrayList<Point> neighbs = new ArrayList();
		// top
		try {
			int pixel = _pixelArray[row - 1][col];
			neighbs.add(new Point(col, row - 1));
		} catch (IndexOutOfBoundsException whatever) {
		}
		// right

		try {
			int pixel = _pixelArray[row][col + 1];
			neighbs.add(new Point(col + 1, row));
		} catch (IndexOutOfBoundsException whatever) {
		}
		// bottom

		try {
			int pixel = _pixelArray[row + 1][col];
			neighbs.add(new Point(col, row + 1));
		} catch (IndexOutOfBoundsException whatever) {
		}
		// left

		try {
			int pixel = _pixelArray[row][col - 1];
			neighbs.add(new Point(col - 1, row));
		} catch (IndexOutOfBoundsException whatever) {
		}
		return neighbs;
	}

	public void calcCostsAndDirs() {

		// fill in bottom row of costs
		for (int j = 0; j < getPicWidth(); j++) {
			_costsArray[getPicHeight()-1][j] = _colorImportance[getPicHeight() - 1][j];
			
		}

		// in case two seams have the same cost, pick the first seam from the
		// left
		for (int row = getPicHeight() - 2; row >= 0; row--) {
			for (int col = 0; col < getPicWidth(); col++) {
				// fill in costs array && dirs array
				// find bottom row neighbors

				Integer bottomLeft = null;
				Integer bottom = null;
				Integer bottomRight = null;

				// find column of lowest downstairs neighbor
				try {
					bottomLeft = _colorImportance[row + 1][col - 1];
				} catch (IndexOutOfBoundsException whatever) {
				}
				try {
					bottom = _colorImportance[row + 1][col];
				} catch (IndexOutOfBoundsException whatever) {
				}
				try {
					bottomRight = _colorImportance[row + 1][col + 1];
				} catch (IndexOutOfBoundsException whatever) {
				}

				// value of least cost seam below this row
				// also calculate dir value
				Integer smallest = bottomLeft;
				int dir = -1;
				if (bottomLeft == null || bottomLeft > bottom) {
					smallest = bottom;
					dir = 0;
				}
				if (bottomRight != null && bottom > bottomRight) {
					smallest = bottomRight;
					dir = 1;
				}

				int cost = _colorImportance[row][col] + smallest;
				_costsArray[row][col] = cost;
				_dirsArray[row][col] = dir;
				
			}

		}

	}

}
