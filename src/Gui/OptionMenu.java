package Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class OptionMenu implements Initializable {

    public Pane optionPane;
    public Button btnreturn;
    public Button btnspanish;
    public Button btnenglish;

    private Pane parent;

    public  OptionMenu ( Pane inParent ) {
        this.parent = inParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnspanish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Locale.setDefault(new Locale.Builder().setLanguage("es").setRegion("MX").build());
            }
        });

        btnenglish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Locale.setDefault(new Locale.Builder().setLanguage("en").setRegion("US").build());
            }
        });

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
