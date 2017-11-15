package Gui;

import characters.BugSimple;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameFrame implements Initializable {

    public Pane gamePane;
    public Path pathway;
    public Button btnreturn;

    private Pane parent;
    private Pane grandfather;

    //private final Rectangle rectPath = new Rectangle (0, 0, 40, 40);;

    public GameFrame (Pane inParent, Pane inGrandfather) {
        this.parent = inParent;
        this.grandfather = inGrandfather;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializaButtons();

        gamePane.setStyle("-fx-background-color: darkslateblue;");

        BugSimple bugSimple = new BugSimple();



        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(pathway);
        pathTransition.setNode(bugSimple.getHitbox());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("chan chan chaaaaaaan");
                gamePane.getChildren().remove(bugSimple.getHitbox());
            }
        });


        gamePane.getChildren().add(bugSimple.getHitbox());


    }

    private void initializaButtons () {
        btnreturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String resourcesLocation = "messages.messages";
                    ResourceBundle rb = ResourceBundle.getBundle(resourcesLocation);

                    parent.getChildren().clear();

                    SelectLevelPane levelController = new SelectLevelPane(grandfather);

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "select_level_view.fxml"
                            )
                    );

                    loader.setResources(rb);
                    loader.setController(levelController);
                    parent.getChildren().add(loader.load());

                } catch ( IOException ioe ) {
                    ioe.printStackTrace();
                }
            }
        });
    }
}
