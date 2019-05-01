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

        Image flagImage = new Image(getClass().getResourceAsStream("images/flag.png"));
        ImageView flagImageView = new ImageView(flagImage);
        Label flagsNumberLabel = new Label("04");

        Image mineImage = new Image(getClass().getResourceAsStream("images/mine.png"));
        ImageView mineImageView = new ImageView(mineImage);
        Label totalMinesLabel = new Label("99");
        
        controlLayout.getChildren().addAll(gameDifficultyCombo, newGameBtn, 
            smilingImageView, flagImageView, flagsNumberLabel, mineImageView, totalMinesLabel);
        
        //Building a 9x9 grid
        TilePane tile = new TilePane();
        tile.setPadding(new Insets(5, 0, 5, 0));
        tile.setPrefColumns(16);
        tile.setPrefColumns(16);

        ToggleButton cells[][] = new ToggleButton[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                cells[i][j] = new ToggleButton("  ");
                tile.getChildren().add(cells[i][j]);
            }
        }

        mainLayout.getChildren().addAll(controlLayout, tile);
        
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}