package bankutim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 768;
    private final double MINIMUM_WINDOW_HEIGHT = 575;
    private double withScreen;
    private double heightScreen;


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setTitle("BankUTIM");
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);

        Screen screen = Screen.getPrimary(); //get user current screen size
        Rectangle2D bounds = screen.getVisualBounds();
        this.withScreen = bounds.getWidth();
        this.heightScreen = bounds.getHeight();

        /*Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("BankUTIM");
        primaryStage.setScene(new Scene(root, 768, 575));
        primaryStage.show();
        */

        gotoMainWindow();
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainApp.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainApp.class.getResource(fxml));
        BorderPane page;
        try {
            page = (BorderPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene(); //resize windows to maximum size available
        return (Initializable) loader.getController();
    }



    public void gotoMainWindow() {
        try {
            MainController mainWindow = (MainController) replaceSceneContent("main.fxml");
            mainWindow.setApp(this);
            stage.setX(0);
            stage.setY(0);
            stage.setWidth(this.withScreen);
            stage.setHeight(this.heightScreen);
        } catch (Exception ex) {
            //log error
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getWithScreen() {
        return withScreen;
    }

    public double getHeightScreen() {
        return heightScreen;
    }


}
