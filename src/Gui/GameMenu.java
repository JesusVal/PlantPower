package Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenu implements Initializable {

    public BorderPane menuBP;
    public Button btnstart, btnload, btnoption, btncredit;
    public Button btnexit;
    public Pane menuPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnstart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        btncredit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try{
                    menuPane.getChildren().clear();

                    CreditPanel creditPanel = new CreditPanel(menuPane);

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "credit_view.fxml"
                            )
                    );
                    loader.setController(creditPanel);
                    menuPane.getChildren().add(loader.load());

                } catch (IOException oie) {
                    oie.printStackTrace();
                }

            }
        });

        btnexit.setOnAction(event -> System.exit(0));


    }


}
