import javafx.scene.control.ToggleButton;

public class Cell extends ToggleButton {

    private int row;
    
    private int col;
    
    /**
     * 0 for a blank cell
     * -1 for a mine
     * 1-8 for a digit representing the surrounding mines
     */
    private int value;
    
    private boolean revealed;
    
    /**
     * Creates a new Cell with the text displayed on the ToggleButton
     * @param text the text to be displayed on the ToggleButton 
     */
    public Cell(int row, int col, String text) {
        super(text);

        this.row = row;
        this.col = col;
    }
    
    /**
     * Display the cell according to its value
     * Display the digit with the correct color Or a mine/flag image
     */
    public void displayCell() {
        System.out.println("The cell "+ row +", "+ col + " with value "+ value +" is displayed!");
    }
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
        
        displayCell();
    }
    
}
