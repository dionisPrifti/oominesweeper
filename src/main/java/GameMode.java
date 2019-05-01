/**
 * An Enum holding the possible values and fields 
 * for the game mode / difficulty
 */
public enum GameMode {

    /**
     * WIDTH: 9
     * HEIGHT: 9
     * MINES: 10
     * 
     * Total cells: 81
     * Mine's percentage: 12.3
     */
    EASY(9, 9, 10),

    /**
     * WIDTH: 16
     * HEIGHT: 16
     * MINES: 40
     * 
     * Total cells: 256
     * Mine's percentage: 15.6
     */
    MEDIUM(16, 16, 40),

    /**
     * WIDTH: 30
     * HEIGHT: 16
     * MINES: 99
     * 
     * Total cells: 480
     * Mine's percentage: 20.6
     */
    HARD(30, 16, 99);
    
    private final int width;
    private final int height;
    private final int totalMines;

    private GameMode(final int width, final int height, final int totalMines) {
        this.width = width;
        this.height = height;
        this.totalMines = totalMines;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTotalMines() {
        return totalMines;
    }
    
}
