import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class  PaczkamatyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout/dblogin_screen.fxml"));
        primaryStage.setTitle("Paczkamaty");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    private static Scene scene;
//    private double xOffset = 0;
//    private double yOffset = 0;
//
//    @Override
//    public void start(Stage stage) throws Exception {
//
//        Parent p = loadFXML("./layout/paczkamatFX");
//
//        p.setOnMousePressed(mouseEvent -> {
//            xOffset = mouseEvent.getSceneX();
//            yOffset = mouseEvent.getSceneY();
//        });
//
//        p.setOnMouseDragged(mouseEvent -> {
//            stage.setX(mouseEvent.getScreenX() - xOffset);
//            stage.setY(mouseEvent.getScreenY() - yOffset);
//        });
//
//        String osName = System.getProperty("os.name");
//        if( osName != null && osName.startsWith("Windows") ) {
//
//            //
//            // Windows hack b/c unlike Mac and Linux, UNDECORATED doesn't include a shadow
//            //
//            scene = (new WindowsHack()).getShadowScene(p);
//            stage.initStyle(StageStyle.TRANSPARENT);
//
//        } else {
//            scene = new Scene( p );
//            stage.initStyle(StageStyle.UNDECORATED);
//        }
//
//        scene.getStylesheets().add("layout/paczkamatCSS.css");
//
//        stage.setScene(scene);
//        stage.setTitle("Paczkamaty");
//        stage.setMinWidth(1280);
//        stage.setMinHeight(800);
//        stage.show();
//    }
//
//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(PaczkamatyApp.class.getResource(fxml + ".fxml"));
//        return fxmlLoader.load();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
}


// class WindowsHack {
//
//    public Scene getShadowScene(Parent p) {
//        Scene scene;
//        VBox outer = new VBox();
//        outer.getChildren().add( p );
//        outer.setPadding(new Insets(10.0d));
//        outer.setBackground( new Background(new BackgroundFill( Color.rgb(0,0,0,0), new CornerRadii(0), new
//                Insets(0))));
//
//        p.setEffect(new DropShadow());
//        ((VBox)p).setBackground( new Background(new BackgroundFill( Color.WHITE, new CornerRadii(0), new Insets(0)
//        )));
//
//        scene = new Scene( outer );
//        scene.setFill( Color.rgb(0,255,0,0) );
//        return scene;
//    }
//}
