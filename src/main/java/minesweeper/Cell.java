package minesweeper;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.BoardUtil;
import util.StyleUtil;

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
    
    private boolean flagged;
    
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
     * Display a single cell, according to its value
     * Display the digit with the correct color Or a mine/flag image
     * After that, set the click event to indicate the neighbors
     */
    public void displaySingleCell() {
        revealed = true;

        if (isMine()) {
            displayMine();
        } else if (isBlank()) {
            displayBlank();
        } else {
            displayDigit();
        }
        
        this.setSelected(true);
        //Hotfix
        this.setMinWidth(29);
        this.setMinHeight(31);

        //TODO Check if GAME WON

        this.setOnAction(event -> {
            //TODO revealed cell clicked Event
            this.setSelected(true);
        });
    }
    
    /**
     * Display this cell and try to display its neighbors
     */
    public void displayNeighborCells() {
        BoardUtil.revealNeighbors(this);
    }
        
    /**
     * Display the mine image
     */
    public void displayMine() {
        Image testMineImage = new Image(getClass().getResourceAsStream("/images/mine.png"));
        ImageView testMineImageView = new ImageView(testMineImage);
        testMineImageView.setFitHeight(21);
        testMineImageView.setFitWidth(21);

        this.setText("");
        this.setGraphic(testMineImageView);
        this.setPadding(new Insets(5,4,5,4));
    }
    
    /**
     * Display spaces for a blank cell
     */
    public void displayBlank() {
        this.setText("  ");
    }
    
    /**
     * Display a cell as a number with the corresponding color
     */
    public void displayDigit() {
        this.setText(value + "");
        this.setStyle("-fx-font-weight: bold");
        this.setTextFill(StyleUtil.getColorForValue(value));
    }
    
    /**
     * Display the flag image
     */
    public void displayFlag() {
        Image flagImage = new Image(getClass().getResourceAsStream("/images/flag.png"));
        ImageView flagImageView = new ImageView(flagImage);
        flagImageView.setFitHeight(21);
        flagImageView.setFitWidth(21);

        this.setText("");
        this.setGraphic(flagImageView);
        this.setPadding(new Insets(5,4,5,4));
        
        //TODO Check if GAME WON
    }
    
    /**
     * Display spaces for a blank cell
     */
    public void displayHidden() {
        ImageView noImage = new ImageView();
        noImage.setImage(null);        
        noImage.setFitHeight(21);
        noImage.setFitWidth(21);
        
        this.setGraphic(noImage);
        this.setText("");
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
    
    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
        
        if (flagged) {
            displayFlag();
        } else {
            displayHidden();
        }
    }
    
    /**
     * Quick check on the Cell value whether it's a mine
     * @return
     */
    public boolean isMine() {
        if (value == -1) {
            return true;
        } else {
            return false;
        }
    }    

    /**
     * Quick check on the Cell value whether it's a blank cell
     * @return
     */
    public boolean isBlank() {
        if (value == 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
