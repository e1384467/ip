package jerry;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Jerry using FXML.
 */
public class Main extends Application {

    private Jerry jerry = new Jerry();

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(700);
            stage.setMinWidth(500);
            fxmlLoader.<MainWindow>getController().setJerry(jerry); // inject the Jerry instance
            fxmlLoader.<MainWindow>getController().startUpMessage();
            stage.setTitle("Jerry");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
