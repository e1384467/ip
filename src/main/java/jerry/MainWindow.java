package jerry;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jerry jerry;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image jerryImage = new Image(this.getClass().getResourceAsStream("/images/Jerry.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Jerry instance */
    public void setJerry(Jerry j) {
        jerry = j;
    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing Jerry's reply and then appends them to
     * the dialog container. Clears the user input after processing. If {@code isExist} is true then pause for 1 second
     * before closing the GUI window. Otherwise, do nothing.
     */
    @FXML
    private void handleUserInput() {
        String userText = "User: " + userInput.getText();
        String jerryText = "Jerry: \n" + jerry.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getJerryDialog(jerryText, jerryImage)
        );
        userInput.clear();

        if (jerry.isExit) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> Platform.exit());
            pause.play();
        }
    }

    /**
     * Displays the startup error message (if any) followed by the welcome message
     * in the dialog container when the GUI initializes.
     */
    public void startUpMessage() {
        if (!jerry.startUpError.isEmpty()) {
            DialogBox startupErrorDialogBox = DialogBox.getJerryDialog(jerry.startUpError, jerryImage);
            dialogContainer.getChildren().addAll(startupErrorDialogBox);
        }

        DialogBox welcomeDialogBox = DialogBox.getJerryDialog("Jerry: " + jerry.welcomeText(), jerryImage);
        dialogContainer.getChildren().addAll(welcomeDialogBox);
    }
}

