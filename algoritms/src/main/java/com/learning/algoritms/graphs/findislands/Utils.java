package com.learning.algoritms.graphs.findislands;

public class Utils {

	/**
	 * Use the x, y (the square position inside the matrix) to generate a unique index
	 * The limit of nodes is x = 46340 and y = 46340. in this way we do not reach the limit size for "int" value
	 * @param matrixSize
	 * @param currentX
	 * @param currentY
	 * @return an id generated from x, y (selected index from matrix)
	 */
	public static int calculateId(int matrixSize, int currentX, int currentY) {
		int maxValue = 46340;
		
		if(currentX > maxValue) {
			throw new IndexOutOfBoundsException("Max allowed value for matriz size is: 46340"); 
		}

		int id = matrixSize * currentX + currentY;
		return id;
	}

	/**
	 * Calculate the x location for this node
	 * @param id
	 * @param matrixSize
	 * @return x
	 */
	public static int reverseXFromId(int id, int matrixSize) {
		return id / matrixSize;
	}

	/**
	 * Calculate the y location for this node 
	 * @param id
	 * @param x
	 * @param matrixSize
	 * @return
	 */
	public static int reverseYFromId(int id, int x, int matrixSize) {
		return id + (-(matrixSize * x));
	}
}
