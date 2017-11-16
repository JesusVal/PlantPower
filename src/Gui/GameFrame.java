package Gui;

import characters.BugSimple;
import characters.PrincipalTree;
import characters.TowerSimple;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameFrame implements Initializable {

    public Pane gamePane;
    public Path pathway;
    public Button btnreturn;
    public Button btnplant;
    public Rectangle region1;
    public Label lblcoins;
    public Rectangle maintreeshape;


    private Pane parent;
    private Pane grandfather;

    private PrincipalTree principalTree;
    private boolean treeinstack = false;
    private BugSimple simplebug1 = new BugSimple(),
            simplebug2 = new BugSimple(),
            simplebug3 = new BugSimple();
    private PathTransition pathTransition = new PathTransition();

    private List<TowerSimple> treelist = new ArrayList<TowerSimple>();
    private int timercounter = 0;


    //private final Rectangle rectPath = new Rectangle (0, 0, 40, 40);;

    public GameFrame (Pane inParent, Pane inGrandfather) {
        this.parent = inParent;
        this.grandfather = inGrandfather;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        principalTree = new PrincipalTree();
        lblcoins.setText(Integer.toString(principalTree.getCoins()));

        initializeButtons();
        initializePath();

        //bugSimple.getHitbox().



        gamePane.setStyle("-fx-background-color: aliceblue;");


        //gamePane.getChildren().add(bugSimple.getHitbox());



        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                timercounter++;
                System.out.println("Refresh" + timercounter + " second on UI thread");
                if(timercounter == 5) {
                    pathTransition.setNode(simplebug1.getHitbox());
                    gamePane.getChildren().add(simplebug1.getHitbox());
                    pathTransition.play();
                }

            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();


    }

    private void initializeButtons () {
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

        btnplant.setOnAction(event -> {
            if(( (principalTree.getCoins() - 400) > 0) && (!treeinstack) ) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                treeinstack = true;
            } else if (treeinstack ) {
                System.out.println("Ya tienes un arbol en cola");
            } else {
                System.out.println("Insufisientes monedas");
            }

            System.out.println(treelist.size() + "a");

        });

        region1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(treeinstack) {
                    Image image = new Image("resourses/towertree1.png");
                    region1.setFill(new ImagePattern(image));
                    treeinstack = false;
                }
            }
        });

    }

    private void initializePath (){
        //PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(70000));
        pathTransition.setPath(pathway);
        //pathTransition.setNode(bugSimple.getHitbox());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
        //pathTransition.play();

        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("chan chan chaaaaaaan");
                gamePane.getChildren().remove(simplebug1.getHitbox());
            }
        });
    }

    private void bugDameged() {

    }
}
