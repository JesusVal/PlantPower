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

    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        btnstart.setText(bundle.getString("main_btnstart"));
        btnload.setText(bundle.getString("main_btnload"));
        btnoption.setText(bundle.getString("main_btnoption"));
        btncredit.setText(bundle.getString("main_btncredits"));
        btnexit.setText(bundle.getString("main_btnexit"));

        String resourcesLocation = "messages.messages";
        ResourceBundle rb = ResourceBundle.getBundle(resourcesLocation);



        btnstart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    menuPane.getChildren().clear();

                    SelectLevelPane selectLevelPane = new SelectLevelPane(menuPane);

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "select_level_view.fxml"
                            )
                    );
                    loader.setResources(rb);
                    loader.setController(selectLevelPane);
                    menuPane.getChildren().add(loader.load());

                } catch (IOException oie) {
                    oie.printStackTrace();
                }
            }
        });

        btnoption.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    menuPane.getChildren().clear();
                    OptionMenu optionMenu = new OptionMenu(menuPane);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("option_view.fxml"));

                    loader.setResources(rb);
                    loader.setController(optionMenu);
                    menuPane.getChildren().add(loader.load());

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
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
                    loader.setResources(rb);
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
