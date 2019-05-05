import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    int totalRows; //height

    int totalCols; //width
    
    int totalMines;
    
    Cell[][] cells;
    
    int[][] board;
    
    public Board(GameMode gameMode, Cell[][] cells) {
        this.totalRows = gameMode.getTotalRows();
        this.totalCols = gameMode.getTotalCols();
        this.totalMines = gameMode.getTotalMines();
        
        this.cells = cells;
    }
    
    public void generateBoard(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        
        generateBoard(row, col);
    }
    
    public void generateBoard(int startRow, int startCol) {
        board = new int[totalRows][totalCols];
        Random random = new Random();
        
        for (int mine = 0; mine < totalMines; mine++) {
            boolean minePlaced = false;
            
            while (!minePlaced) {
                int row = random.nextInt(totalRows);
                int col = random.nextInt(totalCols);
                
                //make sure we can place this mine:
                //1. the mine is not already placed
                //2. the mine is not a neighbor with the start cell
                if (board[row][col]==0 && !areNeighbours(startRow, startCol, row, col)) {
                    board[row][col] = -1;
                    minePlaced = true;
                }
                
            }
        }
        
        //After placing all the mines, populate the board with numbers indicating mines
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                //Do this for non-mines
                if (board[row][col] != -1) {
                    int numberOfAdjecentMines = getAdjecentMineCount(row, col);
                    board[row][col] = numberOfAdjecentMines;
                }
            }
        }

        setCellsValues();

        //TODO remove this, for testing purposes
        printBoard();
    };
    
    public void printBoard() {
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
    
    public void setCellsValues() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                cells[i][j].setValue(board[i][j]);
                
                //TODO remove this, for testing purposes
                cells[i][j].setRevealed(true);
            }
        }
    }
    
    /**
     * A Utility Function to check whether given cell (row, col) is a valid cell or not
     * @param row
     * @param col
     * @return
     */
    boolean isValid(int row, int col) { 
        // Returns true if row number and column number 
        // is in range 
        return ((row >= 0) && (row < totalRows) && 
               (col >= 0) && (col < totalCols)); 
    }
      
    /**
     * A Utility Function to check whether given cell (row, col) has a mine or not.
     * @param row
     * @param col
     * @return
     */
    boolean isMine(int row, int col) { 
        if (cells[row][col].getValue() == -1) 
            return true; 
        else
            return false; 
    }

    boolean areNeighbours(int row, int col, int row2, int col2) { 
        if (Math.abs(row-row2) <= 1 && Math.abs(col-col2) <= 1) 
            return true; 
        else
            return false; 
    }
    
    List<Cell> getNeighbours(int row, int col) {
        List<Cell> neighbours = new ArrayList<>();
        /* 
            All the 8 adjacent 
            
            Cell-->Current Cell (row, col) 
            N -->  North        (row-1, col) 
            S -->  South        (row+1, col) 
            E -->  East         (row, col+1) 
            W -->  West         (row, col-1) 
            N.E--> North-East   (row-1, col+1) 
            N.W--> North-West   (row-1, col-1) 
            S.E--> South-East   (row+1, col+1) 
            S.W--> South-West   (row+1, col-1) 
        */

        //N -->  North        (row-1, col)
        if (isValid(row-1, col)) {
            neighbours.add(cells[row-1][col]);
        }
        //S -->  South        (row+1, col) 
        if (isValid(row+1, col)) {
            neighbours.add(cells[row+1][col]);
        }
        //E -->  East         (row, col+1)
        if (isValid(row, col+1)) {
            neighbours.add(cells[row][col+1]);
        }
        //W -->  West         (row, col-1) 
        if (isValid(row, col-1)) {
            neighbours.add(cells[row][col-1]);
        }

        //N.E--> North-East   (row-1, col+1)
        if (isValid(row-1, col+1)) {
            neighbours.add(cells[row-1][col+1]);
        }
        //N.W--> North-West   (row-1, col-1) 
        if (isValid(row-1, col-1)) {
            neighbours.add(cells[row-1][col-1]);
        }
        //S.E--> South-East   (row+1, col+1)
        if (isValid(row+1, col+1)) {
            neighbours.add(cells[row+1][col+1]);
        }
        //S.W--> South-West   (row+1, col-1) 
        if (isValid(row+1, col-1)) {
            neighbours.add(cells[row+1][col-1]);
        }
        
        return neighbours;
    }
    
    int getNeighboursCount(int row, int col) {
        List<Cell> neighbours = getNeighbours(row, col);
        
        return neighbours.size();
    }
    
    int getAdjecentMineCount(int row, int col) {
        List<Cell> neighbours = getNeighbours(row, col);
        
        int mineCount = 0;
        for (Cell neighbor : neighbours) {
            if (board[neighbor.getRow()][neighbor.getCol()] == -1) {
                mineCount++;
            }
        }
        return mineCount;
    }
}
