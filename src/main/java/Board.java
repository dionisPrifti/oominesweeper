import java.util.ArrayList;
import java.util.List;

import util.BoardUtil;

public class Board {

    int totalRows; //height
    
    int totalCols; //width
    
    Cell[][] cells;
    
    /**
     * Get a list of all the neighbor cells 
     * 
     * @param row
     * @param col
     * @return
     */
    List<Cell> getNeighbours(int row, int col) {
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

}
