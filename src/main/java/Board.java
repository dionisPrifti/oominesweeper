import java.util.ArrayList;
import java.util.List;

import util.BoardUtil;

public class Board {

    private int totalRows; //height
    
    private int totalCols; //width
    
    private Cell[][] cells;
    
    private int[][] board;
    
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
                    if (BoardUtil.isValid(row + rowOffset, col + colOffset, totalRows, totalCols)) {
                        neighbours.add(cells[row + rowOffset][col + colOffset]);
                    }
                }
            }
        }

        return neighbours;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int[][] getBoard() {
        return board;
    }
    
}
