import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Henchman using FXML.
 */
public class HenchmanMain extends Application {

    private final Henchman henchman = new Henchman();

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Henchman Task Manager");
            stage.getIcons().add(new Image("/images/henchman.png"));
            FXMLLoader fxmlLoader = new FXMLLoader(HenchmanMain.class.getResource("/view/MainWindow.fxml"));

            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<HenchmanUi>getController().setHenchman(henchman);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
