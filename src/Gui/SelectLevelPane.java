package Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectLevelPane implements Initializable {

    public Pane levelselectPane;
    public Button btnlvl1;
    public Button btnreturn;

    private Pane parent;

    public SelectLevelPane (Pane inPane) {
        this.parent = inPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        btnreturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String resourcesLocation = "messages.messages";
                    ResourceBundle rb = ResourceBundle.getBundle(resourcesLocation);

                    parent.getChildren().clear();

                    GameMenu inicioController = new GameMenu();

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "menu_view.fxml"
                            )
                    );

                    loader.setResources(rb);
                    loader.setController(inicioController);
                    parent.getChildren().add(loader.load());

                } catch ( IOException ioe ) {
                    ioe.printStackTrace();
                }
            }
        });


    }
}
