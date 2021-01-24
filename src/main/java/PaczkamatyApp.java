import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class  PaczkamatyApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {

        scene = new Scene(loadFXML("paczkamatFX"), 600, 400);
        scene.getStylesheets().add("paczkamatCSS.css");

        stage.setScene(scene);
        stage.setTitle("Paczkamaty");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PaczkamatyApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
