import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        VBox mainLayout = new VBox();
        
        HBox controlLayout = new HBox();
        controlLayout.setPadding(new Insets(5,5,5,5));
        HBox statusLayout = new HBox();
        statusLayout.setPadding(new Insets(5,5,5,5));

        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "Easy",
                "Medium",
                "Hard"
            );
        ComboBox<String> gameDifficultyCombo = new ComboBox<String>(options);
        
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

        Image mineImage = new Image(getClass().getResourceAsStream("images/mine.png"));
        ImageView mineImageView = new ImageView(mineImage);
        mineImageView.setFitHeight(30);
        mineImageView.setFitWidth(30);
        
        Label totalMinesLabel = new Label("99");

        controlLayout.getChildren().addAll(gameDifficultyCombo, newGameBtn);
        statusLayout.getChildren().addAll(flagImageView, flagsNumberLabel, smilingImageView, mineImageView, totalMinesLabel);
        
        //Building a 9x9 grid
        TilePane tile = new TilePane();
        tile.setPadding(new Insets(5, 5, 5, 5));
        tile.setPrefColumns(9);
        tile.setPrefColumns(9);

        ToggleButton cells[][] = new ToggleButton[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new ToggleButton("  ");
                tile.getChildren().add(cells[i][j]);
            }
        }

        mainLayout.getChildren().addAll(controlLayout, statusLayout, tile);
        
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}