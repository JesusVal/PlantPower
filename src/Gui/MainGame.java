package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainGame extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Controller mainController = new Controller();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));

        loader.setController(mainController);

        primaryStage.setTitle("Power Plant");
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(new Scene((Parent)loader.load(), 500, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
