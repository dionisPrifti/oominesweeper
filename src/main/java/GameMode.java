/**
 * An Enum holding the possible values and fields 
 * for the game mode / difficulty
 */
public enum GameMode {

    /**
     * WIDTH: 9
     * HEIGHT: 9
     * MINES: 10
     * WINDOW WIDTH: 277
     * WINDOW HEIGHT: 405
     * 
     * Total cells: 81
     * Mine's percentage: 12.3
     */
    EASY(9, 9, 10, 277, 405),

    /**
     * WIDTH: 16
     * HEIGHT: 16
     * MINES: 40
     * WINDOW WIDTH: 480
     * WINDOW HEIGHT: 622
     * 
     * Total cells: 256
     * Mine's percentage: 15.6
     */
    MEDIUM(16, 16, 40, 480, 622),

    /**
     * WIDTH: 30
     * HEIGHT: 16
     * MINES: 99
     * WINDOW WIDTH: 886
     * WINDOW HEIGHT: 622
     * 
     * Total cells: 480
     * Mine's percentage: 20.6
     */
    HARD(30, 16, 99, 886, 622);
    
    private final int width;
    private final int height;
    private final int totalMines;
    private final int windowWidth;
    private final int windowHeight;

    private GameMode(final int width, final int height, final int totalMines, final int windowWidth, final int windowHeight) {
        this.width = width;
        this.height = height;
        this.totalMines = totalMines;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
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

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }
    
}
