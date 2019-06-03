package util;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import minesweeper.Board;
import minesweeper.Cell;

/**
 * A class containing utility methods related to the board and the cells
 * 
 * @author Dionis Prifti
 */
public class BoardUtil {

    public static Board board;
    
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
    
    /**
     * An utility method to check whether a given cell (row, col) is a valid cell or not
     * @param row
     * @param col
     * @param totalRows
     * @param totalCols
     * @return
     */
    public static boolean isValid(int row, int col) { 
        // Returns true if row number and column number is in range 
        return ((row >= 0) && (row < board.getTotalRows()) && 
               (col >= 0) && (col < board.getTotalCols()));
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
    
    /**
     * Get a list of all the neighbor cells 
     * 
     * @param row
     * @param col
     * @return
     */
    public static List<Cell> getNeighbours(int row, int col) {
        List<Cell> neighbours = new ArrayList<>();
        
        for (int rowOffset : BoardUtil.OFFSETS) {
            for (int colOffset : BoardUtil.OFFSETS) {
                //Do not include the cell itself
                if (! (rowOffset == 0 && colOffset == 0)) {
                    
                    //Add as a neighbor only if it is a valid cell 
                    //(within the range of the board)
                    if (BoardUtil.isValid(row + rowOffset, col + colOffset)) {
                        neighbours.add(board.getCells()[row + rowOffset][col + colOffset]);
                    }
                }
            }
        }

        return neighbours;
    }
    
    /**
     * Get the number of the adjacent mines to a certain Cell
     * 
     * @param row
     * @param col
     * @return
     */
    public static int getAdjacentMineCount(int row, int col) {
        List<Cell> neighbours = getNeighbours(row, col);

        int mineCount = 0;
        for (Cell neighbor : neighbours) {
            //Using getters on the Cell class
            //neighbor.getValue() MUST BE EUQAL TO board[neighbor.getRow()][neighbor.getCol()]
            if (neighbor.getValue() == -1) {
                  mineCount++;
            }
        }
        return mineCount;
    }
    
    /**
     * Get the number of flagged neighbors of a cell
     * 
     * @param cell
     * @return
     */
    public static int numberOfFlaggedNeighbors(Cell cell) {
        int number = 0;
        
        List<Cell> neighbors = BoardUtil.getNeighbours(cell.getRow(), cell.getCol());
        for (Cell neighbor : neighbors) {
            if (neighbor.isFlagged()) {
                number++;
            }
        }
        
        return number;
    }
 
    /**
     * Method to reveal all the neighbors of a cell
     * Used when clicking on a revealed cell that has already a number of 
     * flagged neighbors equal or greater than its value
     * 
     * @param cell
     */
    public static void autoRevealNeighbors(Cell cell) {
        List<Cell> neighbors = BoardUtil.getNeighbours(cell.getRow(), cell.getCol());
        for (Cell neighbor : neighbors) {
                revealNeighbors(neighbor);
        }
    }
    
    /**
     * Indicate all hidden neighbors
     * 
     * @param cell
     */
    public static void indicateNeighbors(Cell cell) {
        List<Cell> neighbors = BoardUtil.getNeighbours(cell.getRow(), cell.getCol());
        
        for (Cell neighbor : neighbors) {
            if (neighbor.isRevealed() || neighbor.isFlagged()) {
                //do not do anything
            } else {
                String oldStyle = neighbor.getStyle();
                neighbor.setStyle("-fx-background-color: #FFEDFF; ");
                
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(event -> {
                    neighbor.setStyle(oldStyle);
                });
                
                new Thread(sleeper).start();
            }
        }
    }
    
    /**
     * Reveal all hidden neighbors recursively
     * 
     * @param cell
     */
    public static void revealNeighbors(Cell cell) {
        //A cell should be revealed, if it is not already revealed, and is not a flag
        if (!cell.isRevealed() && !cell.isFlagged()) {
            
            cell.displaySingleCell();
            
            if (cell.isBlank()) {
                List<Cell> neighbors = BoardUtil.getNeighbours(cell.getRow(), cell.getCol());
                neighbors.forEach(n -> revealNeighbors(n));
            }
        } else {
            return;
        }
    }
    
    /**
     * Check whether the game is won, on each move - reveal or flag a cell
     * The game is won when all the mines are flagged
     * @return
     */
    public static boolean checkIfGameWon() {
        int foundMines = 0;
        int foundFlags = 0;
        
        //The user may choose to flag a cell even before start playing
        if (board == null) {
            return false;
        }
        
        for (Cell[] cells : board.getCells()) {
            for (Cell cell : cells) {
                //FIXME maybe remove this: all cells must be opened and only mines should be flagged
                if (!cell.isRevealed() && !cell.isFlagged()) {
                    return false;
                }

                //avoiding that the user flags all cells
                if (cell.isFlagged()) {
                    foundFlags++;
                }
                
                if (cell.isMine() && cell.isFlagged()) {
                    foundMines++;
                }
            }
        }
        
        if (foundMines == board.getTotalMines() && foundMines == foundFlags) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Open all hidden cells when Game Over / Won
     */
    public static void openCells() {
        for(Cell[] cells : board.getCells()) {
            for (Cell cell : cells) {
                if (!cell.isRevealed() && !cell.isFlagged()) {
                    cell.displaySingleCell();
                }
            }
        }
    }
    
    /**
     * Display all mine cells when Game Over
     */
    public static void displayMines() {
        for(Cell[] cells : board.getCells()) {
            for (Cell cell : cells) {
                if (cell.isMine() && !cell.isFlagged()) {
                    System.out.println("Display mine");
                    cell.displaySingleCell();
                }
            }
        }
    }
    
    /**
     * Disable all cells when Game Over / Won
     */
    public static void disableCells() {
        for(Cell[] cells : board.getCells()) {
            for (Cell cell : cells) {
                cell.setDisable(true);
            }
        }
    }
}
