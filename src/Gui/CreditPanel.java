package Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditPanel implements Initializable {

    public AnchorPane creditPane;
    public Button btnreturn;

    private Pane parent;

    CreditPanel (Pane parentPane){
        this.parent = parentPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnreturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    parent.getChildren().clear();

                    GameMenu inicioController = new GameMenu();
                    //InicioView inicioController = new InicioView(modeloApp);

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "menu_view.fxml"
                            )
                    );

                    loader.setController(inicioController);

                    parent.getChildren().add(loader.load());

                } catch ( IOException ioe ) {
                    ioe.printStackTrace();
                }
            }
        });

    }

    public AnchorPane getCreditPane () {
        return this.creditPane;
    }
}
