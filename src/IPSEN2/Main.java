package IPSEN2;

import IPSEN2.controllers.menu.ContextMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Philip on 18-09-15.
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(laadStartPaneel());
        scene.getStylesheets().add("IPSEN2/styles/Menu.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Pane laadStartPaneel() throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource(ContentLoader.CONTEXTMENU)));

        Pane startPaneel =   loader.load();


        ContextMenuController mainController = loader.getController();

        ContentLoader.setMainController(mainController);
        ContentLoader.laadContent(ContentLoader.HOOFDMENU);

        return startPaneel;
    }

    public static void main(String args[]){
        launch(args);
    }

}
