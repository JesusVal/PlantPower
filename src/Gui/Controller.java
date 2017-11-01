package Gui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Pane contentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            String resourcesLocation = "messages.messages";
            ResourceBundle rb = ResourceBundle.getBundle(resourcesLocation);

            contentPane.getChildren().clear();

            GameMenu inicioController = new GameMenu();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "menu_view.fxml"
                    )
            );

            loader.setResources(rb);
            loader.setController(inicioController);
            contentPane.getChildren().add(loader.load());

        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }

    }
}
