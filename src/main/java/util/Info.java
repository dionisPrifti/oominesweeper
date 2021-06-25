package util;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

public class Info {

    public Info() {
    }

    public static final Logger LOGGER = Logger.getLogger(Info.class.getName());

    public static Boolean showConfirmationDialog(String headerText, String contentText) {
        LOGGER.log(Level.INFO, headerText + ": " + contentText);

        Alert alert = new Alert(AlertType.CONFIRMATION);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/mine.png"));

        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType okButton = ButtonType.YES;
        ButtonType noButton = ButtonType.NO;
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES)
	    return true;

	return false;
    }

    public static Boolean showConfirmationDialogStartNewGame() {
        String header = "Start a new game";
	String content = "Are you sure you want to start a new game? The current game will be lost!";

	return showConfirmationDialog(header, content);
    }

}
