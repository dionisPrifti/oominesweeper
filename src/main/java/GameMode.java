/**
 * An Enum holding the possible values and fields 
 * for the game mode / difficulty
 */
public enum GameMode {

    /**
     * HEIGHT / TOTAL ROWS: 9
     * WIDTH / TOTAL COLS: 9
     * MINES: 10
     * WINDOW WIDTH: 277
     * WINDOW HEIGHT: 405
     * 
     * Total cells: 81
     * Mine's percentage: 12.3
     */
    EASY(9, 9, 10, 277, 405),

    /**
     * HEIGHT / TOTAL ROWS: 16
     * WIDTH / TOTAL COLS: 16
     * MINES: 40
     * WINDOW WIDTH: 480
     * WINDOW HEIGHT: 622
     * 
     * Total cells: 256
     * Mine's percentage: 15.6
     */
    MEDIUM(16, 16, 40, 480, 622),

    /**
     * HEIGHT / TOTAL ROWS: 16
     * WIDTH / TOTAL COLS: 30
     * MINES: 99
     * WINDOW WIDTH: 886
     * WINDOW HEIGHT: 622
     * 
     * Total cells: 480
     * Mine's percentage: 20.6
     */
    HARD(16, 30, 99, 886, 622);

    private final int totalRows;
    private final int totalCols;
    private final int totalMines;
    private final int windowWidth;
    private final int windowHeight;

    private GameMode(final int totalRows, final int totalCols, final int totalMines, final int windowWidth, final int windowHeight) {
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        this.totalMines = totalMines;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
    
    public int getHeight() {
        return totalRows;
    }

    public int getWidth() {
        return totalCols;
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

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }
    
}
