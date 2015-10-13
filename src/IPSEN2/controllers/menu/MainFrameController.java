package IPSEN2.controllers.menu;

import IPSEN2.ContentLoader;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController extends ContentLoader implements Initializable{

    private static double xOffset = 0;
    private static double yOffset = 0;
    private Stage primaryStage;



    @FXML private ImageView menuButton;
    @FXML private AnchorPane navigatieMenu;
    @FXML private StackPane contentHolder;
    @FXML private Label titelLabel;
    @FXML private Pane contentCover;
    @FXML private Pane logoButton, homeNav, gastenNav, wijnenNav,
    bestellingenNav, evenementenNav, handleidingNav;


    public void handleMousePressed(MouseEvent event) throws Exception{
        primaryStage = getPrimaryStage(event);
        xOffset = primaryStage.getX() - event.getScreenX();
        yOffset = primaryStage.getY() - event.getScreenY();
    }

    @FXML
    public void handleMouseDragged(MouseEvent event) throws Exception{
        primaryStage.setX(event.getScreenX() + xOffset);
        primaryStage.setY(event.getScreenY() + yOffset);
    }

    public void handleMinimizeButton(MouseEvent event) {
        getPrimaryStage(event).toBack();
    }

    @FXML
    public void handleCloseButton(MouseEvent event) throws Exception {
            getPrimaryStage(event).close();
    }

    @FXML
    public void handleNavButton(MouseEvent event) throws Exception{
        Object selectedPane = event.getSource();
           if (selectedPane == homeNav || selectedPane == logoButton ){
               addContent(MAINMENU);
           } else if (selectedPane == gastenNav){
              addContent(GUESTS);
           } else if (selectedPane == wijnenNav) {
               addContent(WINE);
           } else if (selectedPane == bestellingenNav) {
               addContent(ORDER);
           } else if (selectedPane == evenementenNav) {
               addContent(EVENTS);
           } else if (selectedPane == handleidingNav) {
               addContent(GUESTS);
           }

        closeNavMenu();
    }


    private void prepareSlideMenuAnimation() {

    menuButton.setOnMouseClicked(event -> {
            if (navigatieMenu.getTranslateX() != 0) {
                openNavMenu();
                contentCover.setOnMouseClicked(e -> closeNavMenu());
            } else {
                closeNavMenu();
            }
        });
    }

    private void openNavMenu() {
        contentCover.setDisable(false);
        TranslateTransition openNavigatieMenu = new TranslateTransition(new Duration(350), navigatieMenu);
        openNavigatieMenu.setToX(0);

        openNavigatieMenu.play();
        contentCover.setStyle("-fx-background-color:rgba(0,0,0,0.2);");
        FadeTransition animation = new FadeTransition(Duration.millis(200), contentCover);
        animation.setFromValue(0);
        animation.setToValue(1.0);
        animation.play();
    }


    private void closeNavMenu() {
        contentCover.setDisable(true);
        TranslateTransition sluitNavigatieMenu = new TranslateTransition(new Duration(350), navigatieMenu);
        sluitNavigatieMenu.setToX(-(navigatieMenu.getWidth()));
        sluitNavigatieMenu.play();
        contentCover.setStyle(null);
    }

    public void setTitle(String titel){
        titelLabel.setText(titel);
    }

    public void setContent(Node node) {
       contentHolder.getChildren().setAll(node);
    }

    public void removeContent() { contentHolder.getChildren().removeAll();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareSlideMenuAnimation();
    }
}