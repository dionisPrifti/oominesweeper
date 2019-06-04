package util;

import minesweeper.Board;

/**
 * A class containing utility methods related to the board and the cells
 * 
 * @author Dionis Prifti
 */
public class BoardUtil {

    /**
     * The array of offsets, to be used for row- or col- offset
     * Careful with the (0, 0) combination (itself, not a neighbor)
     *  
     *   All the 8 possible neighbors:
     *          
     *   Cell-->Current Cell (row, col)    , offsets: ( 0,  0)
     *   N -->  North        (row-1, col)  , offsets: (-1,  0)
     *   S -->  South        (row+1, col)  , offsets: (+1,  0)
     *   E -->  East         (row, col+1)  , offsets: ( 0, +1)
     *   W -->  West         (row, col-1)  , offsets: ( 0, -1)
     *   N.E--> North-East   (row-1, col+1), offsets: (-1, +1)
     *   N.W--> North-West   (row-1, col-1), offsets: (-1, -1)
     *   S.E--> South-East   (row+1, col+1), offsets: (+1, +1)
     *   S.W--> South-West   (row+1, col-1), offsets: (+1, -1)
     *   
     */
    public static final int[] OFFSETS = { -1, 0, 1 };
    
    public static Board board;
    
    /**
     * An utility method to check whether a given cell (row, col) is a valid cell or not
     * @param row
     * @param col
     * @param totalRows
     * @param totalCols
     * @return
     */
    public static boolean isValid(int row, int col, int totalRows, int totalCols) { 
        // Returns true if row number and column number is in range 
        return ((row >= 0) && (row < totalRows) && 
               (col >= 0) && (col < totalCols)); 
    }

    /**
     * An utility method to check whether two cells are neighbors or not
     *  
     * @param row
     * @param col
     * @param row2
     * @param col2
     * @return
     */
    public static boolean areNeighbors(int row, int col, int row2, int col2) { 
        if (Math.abs(row-row2) <= 1 && Math.abs(col-col2) <= 1) 
            return true; 
        else
            return false; 
    }
    
    /**
     * Print the Board in the console, for testing purposes
     * 
     * @param board
     * @param totalRows
     * @param totalCols
     */
    public static void printBoard(int[][] board, int totalRows, int totalCols) {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                if (board[i][j] == -1) {
                    System.out.print("M ");
                } else if (board[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(board[i][j]+ " ");
                }
            }
            System.out.println();
        }
    }
    
}
