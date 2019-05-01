import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    
    TilePane tile = new TilePane();
    
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

        Image smilingImage = new Image(getClass().getResourceAsStream("images/smiling.png"));
        ImageView smilingImageView = new ImageView(smilingImage);
        smilingImageView.setFitHeight(30);
        smilingImageView.setFitWidth(30);
        
        Image flagImage = new Image(getClass().getResourceAsStream("images/flag.png"));
        ImageView flagImageView = new ImageView(flagImage);
        flagImageView.setFitHeight(30);
        flagImageView.setFitWidth(30);
        
        Label flagsNumberLabel = new Label("04");
        flagsNumberLabel.setFont(new Font(20));

        Image mineImage = new Image(getClass().getResourceAsStream("images/mine.png"));
        ImageView mineImageView = new ImageView(mineImage);
        mineImageView.setFitHeight(30);
        mineImageView.setFitWidth(30);
        
        Label totalMinesLabel = new Label("99");
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
            Task<GameMode> task = new Task<GameMode>() {
                @Override 
                public GameMode call() throws Exception {
                    GameMode gameMode = gameModeCombo.getSelectionModel().getSelectedItem();
                    //updateMessage(gameMode.toString());
                    updateValue(gameMode);
                    return gameMode;
                }
            };
            
            task.valueProperty().addListener((obs, oldValue, newValue) -> {
                stage.setWidth(newValue.getWindowWidth());
                stage.setHeight(newValue.getWindowHeight());
                
                flagsNumberLabel.setText("00");
                totalMinesLabel.setText(newValue.getTotalMines()+"");

                populateTilePane(newValue);
            });
            
            new Thread(task).start();
        });
        
        Scene scene = new Scene(mainLayout);
        stage.getIcons().add(new Image("images/mine.png"));
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
        int width = gameMode.getWidth();
        int height = gameMode.getHeight();
        
        tile.getChildren().clear();

        ToggleButton cells[][] = new ToggleButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new ToggleButton("  ");
                tile.getChildren().add(cells[i][j]);
            }
        }
    }

}