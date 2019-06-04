package minesweeper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Info;

public class Main extends Application {
    
    TilePane tile = new TilePane();
    
    Label flagsNumberLabel;
    Label totalMinesLabel;
    
    boolean playing = false;
    
    @Override
    public void start(Stage stage) {
        VBox mainLayout = new VBox();
        
        HBox controlLayout = new HBox();
        controlLayout.setPadding(new Insets(5,5,5,5));
        HBox statusLayout = new HBox();
        statusLayout.setPadding(new Insets(5,5,5,5));

        ObservableList<GameMode> options = 
            FXCollections.observableArrayList(
                GameMode.EASY,
                GameMode.MEDIUM,
                GameMode.HARD
            );
        ComboBox<GameMode> gameModeCombo = new ComboBox<GameMode>(options);
        gameModeCombo.setMinWidth(120);
        gameModeCombo.getSelectionModel().selectFirst();
        
        Button newGameBtn = new Button("New Game");
       
        Image smilingImage = new Image(getClass().getResourceAsStream("/images/smiling.png"));
        ImageView smilingImageView = new ImageView(smilingImage);
        smilingImageView.setFitHeight(30);
        smilingImageView.setFitWidth(30);
        
        Image flagImage = new Image(getClass().getResourceAsStream("/images/flag.png"));
        ImageView flagImageView = new ImageView(flagImage);
        flagImageView.setFitHeight(30);
        flagImageView.setFitWidth(30);
        
        flagsNumberLabel = new Label("00");
        flagsNumberLabel.setFont(new Font(20));

        Image mineImage = new Image(getClass().getResourceAsStream("/images/mine.png"));
        ImageView mineImageView = new ImageView(mineImage);
        mineImageView.setFitHeight(30);
        mineImageView.setFitWidth(30);
        
        totalMinesLabel = new Label(GameMode.EASY.getTotalMines() + "");
        totalMinesLabel.setFont(new Font(20));

        controlLayout.getChildren().addAll(gameModeCombo, newGameBtn);
        HBox.setMargin(gameModeCombo, new Insets(0,5,0,0));
        
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        statusLayout.getChildren().addAll(flagImageView, flagsNumberLabel, region1, 
                                          smilingImageView, region2,
                                          mineImageView, totalMinesLabel);
        
        //Building the initial grid
        populateTilePane(GameMode.EASY);
        tile.setPadding(new Insets(5, 5, 5, 5));

        mainLayout.getChildren().addAll(controlLayout, statusLayout, tile);

        newGameBtn.setOnAction(event -> {
            boolean canStartNewGame = true;
            
            //If we are already playing, show the confirm dialog
            //Otherwise start new game directly, without asking
            if (playing) {
                canStartNewGame = Info.showConfirmationDialogStartNewGame();
            }
            
            if (canStartNewGame) {
                Task<GameMode> task = new Task<GameMode>() {
                    @Override 
                    public GameMode call() throws Exception {
                        GameMode gameMode = gameModeCombo.getSelectionModel().getSelectedItem();
                        updateValue(gameMode);
                        return gameMode;
                    }
                };
                
                task.valueProperty().addListener((obs, oldValue, newValue) -> {
                    stage.setWidth(newValue.getWindowWidth());
                    stage.setHeight(newValue.getWindowHeight());
                    
                    flagsNumberLabel.setText("00");
                    totalMinesLabel.setText(newValue.getTotalMines()+"");
                    
                    playing = false;

                    populateTilePane(newValue);
                });
                
                new Thread(task).start();
            }
        });
        
        Scene scene = new Scene(mainLayout);
        stage.getIcons().add(new Image("/images/mine.png"));
        stage.setTitle("OO Minesweeper");
        stage.setScene(scene);

        stage.setWidth(GameMode.EASY.getWindowWidth());
        stage.setHeight(GameMode.EASY.getWindowHeight());
        stage.setResizable(false);
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void populateTilePane(GameMode gameMode) {
        int totalRows = gameMode.getTotalRows();
        int totalCols = gameMode.getTotalCols();
        
        tile.getChildren().clear();

        Cell cells[][] = new Cell[totalRows][totalCols];
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                cells[i][j] = new Cell(i, j, "  ");
                tile.getChildren().add(cells[i][j]);
                
                cells[i][j].setOnAction(event -> {
                    if (!((Cell)event.getSource()).isFlagged()) {
                        if (!playing) {
                            playing = true;

                            //Generate Board
                            Board board = new Board(gameMode, cells);
                            board.generateBoard((Cell)event.getSource());
                        }

                        ((Cell)event.getSource()).displayNeighborCells();
                    } else {
                        ((Cell)event.getSource()).setSelected(false);
                    }
                });
                
                cells[i][j].setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        if (!((Cell)event.getSource()).isRevealed()) {
                            //Set the flag ON if OFF and vice-versa
                            boolean flagged = !((Cell)event.getSource()).isFlagged();
                            
                            ((Cell)event.getSource()).setFlagged(flagged);

                            addOrRemoveFlag(flagged);
                        }
                    }
                });
            }
        }
    }
    
    /**
     * Add or Remove a flag, by updating the Labels
     * @param add
     */
    public void addOrRemoveFlag(boolean add) {
        int offset = 1;
        
        if (!add) {
            offset = -1;
        }
        
        int flagNumber = Integer.parseInt(flagsNumberLabel.getText()) + offset;
        String flagNumberAsString = getStringRepresentationOfNumber(flagNumber);
        flagsNumberLabel.setText(flagNumberAsString);
    
        int minesNumber = Integer.parseInt(totalMinesLabel.getText()) - offset;
        String minesNumberAsString = getStringRepresentationOfNumber(minesNumber);
        totalMinesLabel.setText(minesNumberAsString);
    }
    
    /**
     * String Representation of a number to be displayed in the labels
     * Display a 0 before a single digit, except if it's negative
     * 
     * @param number
     * @return
     */
    public String getStringRepresentationOfNumber(int number) {
        String numberAsString;
        
        if (number < 10 && number >= 0) {
            numberAsString = "0" + number;
        } else {
            numberAsString = "" + number;
        }
        
        return numberAsString;
    }
}