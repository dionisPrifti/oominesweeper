package minesweeper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.BoardUtil;

public class Board {

    private int totalRows; //height
    
    private int totalCols; //width
    
    private int totalMines;
    
    private Cell[][] cells;
    
    private int[][] board;
    
    public Board(GameMode gameMode, Cell[][] cells) {
        this.totalRows = gameMode.getTotalRows();
        this.totalCols = gameMode.getTotalCols();
        this.totalMines = gameMode.getTotalMines();

        this.cells = cells;
        BoardUtil.board = this;
    }
    
    /**
     * Generate the Minesweeper Board, after the first cell is clicked.
     * 
     * @param cell the first cell clicked
     */
    public void generateBoard(Cell cell) {
        int startRow = cell.getRow();
        int startCol = cell.getCol();

        board = new int[totalRows][totalCols];
        
        placeMinesRandomly(startRow, startCol);
        
        addDigitsAroundMines();

        //Print the generated array for testing purposes
        BoardUtil.printBoard(board, totalRows, totalCols);
        
        //TODO remove this method call, for testing purposes only
        revealCells();
    }
    
    //TODO For testing purposes only
    public void revealCells() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                cells[i][j].setRevealed(true);
            }
        }
    }
    
    /**
     * Place the Mine Randomly, starting from the first clicked cell 
     * and the totalMines count
     * @param startRow
     * @param startCol
     */
    public void placeMinesRandomly(int startRow, int startCol) {
        Random random = new Random();

        for (int mine = 0; mine < totalMines; mine++) {
            boolean minePlaced = false;

            while (!minePlaced) {
                int row = random.nextInt(totalRows);
                int col = random.nextInt(totalCols);

                //make sure we can place this mine:
                //1. the mine is not already placed
                //2. the mine is not a neighbor with the start cell
                if (board[row][col] != -1 && !BoardUtil.areNeighbors(startRow, startCol, row, col)) {
                    board[row][col] = -1;
                    cells[row][col].setValue(-1);
                    minePlaced = true;
                }
            }
        }
    }
    
    /**
     * After placing all the mines, populate the board with numbers indicating mines
     */
    public void addDigitsAroundMines() {
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                //Do this for non-mines
                if (board[row][col] != -1) {
                    int numberOfAdjacentMines = getAdjacentMineCount(row, col);
                    board[row][col] = numberOfAdjacentMines;
                    cells[row][col].setValue(numberOfAdjacentMines);
                }
            }
        }
    }
    
    /**
     * Get a list of all the neighbor cells 
     * 
     * @param row
     * @param col
     * @return
     */
    public List<Cell> getNeighbours(int row, int col) {
        List<Cell> neighbours = new ArrayList<>();
        
        for (int rowOffset : BoardUtil.OFFSETS) {
            for (int colOffset : BoardUtil.OFFSETS) {
                //Do not include the cell itself
                if (! (rowOffset == 0 && colOffset == 0)) {
                    
                    //Add as a neighbor only if it is a valid cell 
                    //(within the range of the board)
                    if (BoardUtil.isValid(row + rowOffset, col + colOffset)) {
                        neighbours.add(cells[row + rowOffset][col + colOffset]);
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
    public int getAdjacentMineCount(int row, int col) {
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
    
    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int[][] getBoard() {
        return board;
    }
    
}
