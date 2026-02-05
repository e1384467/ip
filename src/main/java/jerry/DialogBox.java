package jerry;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private Label text;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox by loading its layout from FXML and setting the
     * text and display picture.
     *
     * @param text Message to be shown in the dialog.
     * @param image Image to be displayed alongside the message.
     */
    public DialogBox(String text, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.text.setText(text);
        this.displayPicture.setImage(image);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        text.getStyleClass().add("reply-label");
    }

    /**
     * Creates and returns a dialog box representing a user message.
     *
     * @param userText Text entered by the user.
     * @param userImage The image representing the user.
     * @return A {@code DialogBox} that contains the user input and image.
     */
    public static DialogBox getUserDialog(String userText, Image userImage) {
        return new DialogBox(userText, userImage);
    }

    /**
     * Creates and returns a dialog box representing Jerry’s reply.
     *
     * @param jerryText Text to be displayed from Jerry.
     * @param jerryImage Image representing Jerry.
     * @return A {@code DialogBox} configured for Jerry’s response.
     */
    public static DialogBox getJerryDialog(String jerryText, Image jerryImage) {
        var db = new DialogBox(jerryText, jerryImage);
        db.flip();
        return db;
    }
}
