import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    /**
     * Creates a new Cell with the text displayed on the ToggleButton
     * @param text the text to be displayed on the ToggleButton 
     */
    public Cell(int row, int col, String text) {
        super(text);
        
        this.row = row;
        this.col = col;
    }

    public void displayCell() {
        if (value == -1) {
            Image testMineImage = new Image(getClass().getResourceAsStream("images/mine.png"));
            ImageView testMineImageView = new ImageView(testMineImage);
            testMineImageView.setFitHeight(21);
            testMineImageView.setFitWidth(21);

            this.setText("");
            this.setGraphic(testMineImageView);
            this.setPadding(new Insets(5,4,5,4));
        } else if (value == 0) {
            this.setText("  ");
        } else {
            this.setText(value+"");
            this.setStyle("-fx-font-weight: bold");
            this.setTextFill(StyleUtil.getColorForValue(value));
        }
    }
    
    public int getRow() {
        return row;
    }


    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }


    public void setCol(int col) {
        this.col = col;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
        
        //FIXME
        if (revealed) {
            displayCell();
            this.setSelected(true);
            this.setMouseTransparent(false);
        }
    }
    
}
