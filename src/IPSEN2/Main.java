package IPSEN2;

import IPSEN2.services.guest.GuestService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

/**
 * Created by Philip on 18-09-15.
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(ContentLoader.loadMainFrame());
        scene.getStylesheets().add((ContentLoader.STYLE));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }

}
