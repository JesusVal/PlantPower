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
import javafx.geometry.Bounds;
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
    private PathTransition pathTransition1 = new PathTransition();
    private PathTransition pathTransition2 = new PathTransition();

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
                if(timercounter == 20) {
                    pathTransition1.setNode(simplebug2.getHitbox());
                    gamePane.getChildren().add(simplebug2.getHitbox());
                    pathTransition1.play();
                }
                if(timercounter == 50) {
                    pathTransition2.setNode(simplebug3.getHitbox());
                    gamePane.getChildren().add(simplebug3.getHitbox());
                    pathTransition2.play();
                }

                if( simplebug1.isDead()) {
                    pathTransition.setDuration(Duration.millis(1)); System.out.println("flag78");
                }
                if(simplebug2.isDead()) {
                    pathTransition1.setDuration(Duration.millis(1));
                }
                if(simplebug3.isDead()) {
                    pathTransition2.setDuration(Duration.millis(1));
                }

                treelist.forEach(item -> {
                    if(towerIntersects(simplebug1.getHitbox(),item.getRectangle())) {
                        simplebug1.damaged(item.getDamage()); System.out.println("bug 1 damaged");
                    }
                    if (towerIntersects(simplebug2.getHitbox(), item.getRectangle())) {
                        simplebug2.damaged(item.getDamage()); System.out.println("bug 2 damaged");
                    }
                    if (towerIntersects(simplebug3.getHitbox(), item.getRectangle())) {
                        simplebug3.damaged(item.getDamage()); System.out.println("bug 3 damaged");
                    }
                });

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
                    treelist.get(0).setRectangle(region1);
                    treeinstack = false;
                }
            }
        });

    }

    private void initializePath (){
        //PathTransition pathTransition = new PathTransition();

        pathway.setVisible(false);
        pathTransition.setDuration(Duration.millis(70000));
        pathTransition.setPath(pathway);

        pathTransition1.setDuration(Duration.millis(70000));
        pathTransition1.setPath(pathway);

        pathTransition2.setDuration(Duration.millis(70000));
        pathTransition2.setPath(pathway);

        //pathTransition.setNode(bugSimple.getHitbox());
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

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

    private boolean towerIntersects (Rectangle rect1, Rectangle rect2) {
        if(rect1 ==null || rect2 == null)
            return false;


/*
        System.out.println("rect2 X" + rect1.getLayoutX() + "--" + rect2.getLayoutX() +
                "Y" + rect1.getLayoutY() + "--" + rect2.getLayoutY() +
                "\nWidth" + rect1.getWidth() + "--" + rect2.getWidth() +
                "Height" + rect1.getHeight() + "--" + rect2.getHeight());*/
/*
        System.out.println("Rect 1 layoutx" + rect1.getLayoutX() +
        "doublevalue" + rect1.xProperty().doubleValue()
        +"getx"+ rect1.getX() + "scale x " + rect1.getScaleX() + " translate " + rect1.getTranslateX());
*/
        //System.out.println("bounds : " + rect1.getLayoutBounds().contains(rect2.getLayoutBounds()));


        //rect1.getLayoutBounds().contains(rect2.getLayoutBounds());/*
        /*
        double esquina1a = rect2.getLayoutX()-50.0;
        double esquina1b = rect2.getLayoutY()-50.0;

        double esquina2a = esquina1a+100.0+rect2.getWidth();
        double esquina2b = esquina1b;

        double esquina3a = esquina1a;
        double esquina3b = rect2.getLayoutY()+ rect2.getHeight() + 50.0;

        double esquina4a = esquina2a;
        double esquina4b = esquina3b;

        System.out.println("bug x: " + rect1.getTranslateX() + " tree x: " + rect2.getLayoutX());
        System.out.println("bug y: " + rect1.getTranslateY() + "tree y:" + rect2.getLayoutY());

        if( ((rect1.getTranslateX()) <= esquina1a && (rect1.getTranslateX()+rect1.getWidth() >= esquina1a)
                && (rect1.getTranslateY() <= esquina1b) && (rect1.getTranslateY()+rect1.getHeight() >= esquina1b) )

                ||

                ((rect1.getTranslateX()) <= esquina2a && (rect1.getTranslateX()+rect1.getWidth() >= esquina2a)
                        && (rect1.getTranslateY() <= esquina2b) && (rect1.getTranslateY()+rect1.getHeight() >= esquina2b) )

                ||

                ((rect1.getTranslateX()) <= esquina3a && (rect1.getTranslateX()+rect1.getWidth() >= esquina3a)
                        && (rect1.getTranslateY() <= esquina3b) && (rect1.getTranslateY()+rect1.getHeight() >= esquina3b) )

                ||

                ((rect1.getTranslateX()) <= esquina4a && (rect1.getTranslateX()+rect1.getWidth() >= esquina4a)
                        && (rect1.getTranslateY() <= esquina4b) && (rect1.getTranslateY()+rect1.getHeight() >= esquina4b) )
                ) {
            System.out.println("Colision :DDDDDDDDDDDDDDD");
        }*/

        //Rectangle rectangle1 = new Rectangle(rect1.getTranslateX(), rect1.getTranslateY(), rect1.getWidth(), rect1.getHeight());
        Rectangle rectangle2 = new Rectangle(rect2.getLayoutX() - 50.0, rect2.getLayoutY() -50.0,
                rect2.getWidth()+ 100.0, rect2.getHeight()+100.0);


        if(rectangle2.contains(rect1.getTranslateX(), rect2.getTranslateY())
                || rectangle2.contains(rect1.getTranslateX()+rect1.getWidth(), rect1.getTranslateY() )
                || rectangle2.contains(rect1.getTranslateX(),rect1.getTranslateY()+rect1.getHeight())
                || rectangle2.contains(rect1.getTranslateX()+rect1.getWidth(), rect1.getTranslateY()+rect1.getHeight())) {
            return true;
        }
        //rectangle2.contains(rect1.getTranslateX(), rect2.getTranslateY());

        return false;
    }
}
