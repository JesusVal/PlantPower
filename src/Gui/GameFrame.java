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
    public Label lblcoins, lbllife, lblgameover,lblgamewin;
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

    private Timeline updater;

    private List<TowerSimple> treelist = new ArrayList<TowerSimple>();
    private int timercounter = 0;

    public Rectangle region1, region2,region3,region4,
            region5,region6,region7,region8,region9,
            region10,region11,region12,region13,region14;

    private boolean block1 = true, block2 = true, block3 =  true;


    //private final Rectangle rectPath = new Rectangle (0, 0, 40, 40);;

    public GameFrame (Pane inParent, Pane inGrandfather) {
        this.parent = inParent;
        this.grandfather = inGrandfather;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblgameover.setVisible(false);
        lblgamewin.setVisible(false);

        principalTree = new PrincipalTree();
        lblcoins.setText(Integer.toString(principalTree.getCoins()));
        lbllife.setText(Integer.toString(principalTree.getLife()));

        initializeButtons();
        initializePath();

        //bugSimple.getHitbox().



        gamePane.setStyle("-fx-background-color: aliceblue;");


        //gamePane.getChildren().add(bugSimple.getHitbox());



        updater = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

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
                    if(block1) {
                        pathTransition.stop();
                        pathTransition.setDuration(Duration.millis(1));
                        pathTransition.play();
                        System.out.println("bug 1 is dead");
                        block1 = false;
                    }
                }
                if(simplebug2.isDead()) {
                    if (block2) {
                        pathTransition1.stop();
                        pathTransition1.setDuration(Duration.millis(1));
                        pathTransition1.play();
                        System.out.println("bug 2 is dead");
                        block2 = false;
                    }
                }
                if(simplebug3.isDead()) {
                    if (block3) {
                        pathTransition2.stop();
                        pathTransition2.setDuration(Duration.millis(1));
                        pathTransition2.play();
                        System.out.println("bug 3 is dead");
                        block3 = false;
                    }
                }

                treelist.forEach(item -> {
                    if(towerIntersects(simplebug1.getHitbox(),item.getRectangle()) && block1) {
                        simplebug1.damaged(item.getDamage()); System.out.println("bug 1 damaged");
                    }
                    if (towerIntersects(simplebug2.getHitbox(), item.getRectangle()) && block2) {
                        simplebug2.damaged(item.getDamage()); System.out.println("bug 2 damaged");
                    }
                    if (towerIntersects(simplebug3.getHitbox(), item.getRectangle()) && block3) {
                        simplebug3.damaged(item.getDamage()); System.out.println("bug 3 damaged");
                    }
                });


                if((block1==false && block2==false && block3==false)){
                    gameOver();
                }

            }
        }));
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.play();


    }

    private void initializeButtons () {


        btnreturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    updater.stop();
                    pathTransition.stop();
                    pathTransition1.stop();
                    pathTransition2.stop();
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
            if(( (principalTree.getCoins() - 400) >= 0) && (!treeinstack) ) {


                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                treeinstack = true;
            } else if (treeinstack ) {
                System.out.println("Ya tienes un arbol en cola");
            } else {
                System.out.println("Insufisientes monedas");
            }

            System.out.println(treelist.size() + "a");

        });


        region1.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region1.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region1);
                treeinstack = false;
            }
        });
        region2.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region2.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region2);
                treeinstack = false;
            }
        });
        region3.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region3.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region3);
                treeinstack = false;
            }
        });
        region4.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region4.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region4);
                treeinstack = false;
            }
        });
        region5.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region5.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region5);
                treeinstack = false;
            }
        });
        region6.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region6.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region6);
                treeinstack = false;
            }
        });
        region7.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region7.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region7);
                treeinstack = false;
            }
        });
        region8.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region8.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region8);
                treeinstack = false;
            }
        });
        region9.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region9.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region9);
                treeinstack = false;
            }
        });
        region10.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region10.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region10);
                treeinstack = false;
            }
        });
        region11.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region11.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region11);
                treeinstack = false;
            }
        });
        region12.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region12.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region12);
                treeinstack = false;
            }
        });
        region13.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region13.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region13);
                treeinstack = false;
            }
        });
        region14.setOnMousePressed(event -> {
            if(treeinstack) {
                treelist.add(new TowerSimple());
                principalTree.setCoins(principalTree.getCoins() - 400);
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
                Image image = new Image("resourses/towertree1.png");
                region14.setFill(new ImagePattern(image));
                treelist.get(treelist.size()-1).setRectangle(region14);
                treeinstack = false;
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

        pathTransition.setOnFinished(event -> {
            if( simplebug1.isDead()) {
                principalTree.setCoins(principalTree.getCoins()+simplebug1.getValue());
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
            } else {
                principalTree.setLife(principalTree.getLife() - simplebug1.getDamage());
                if (principalTree.getLife() <= 0)
                    gameOver();

                lbllife.setText(Integer.toString(principalTree.getLife()));

            }
            gamePane.getChildren().remove(simplebug1.getHitbox());
        });

        pathTransition1.setOnFinished(event -> {
            if( simplebug2.isDead()) {
                principalTree.setCoins(principalTree.getCoins()+simplebug2.getValue());
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
            } else {
                principalTree.setLife(principalTree.getLife() - simplebug2.getDamage());
                if (principalTree.getLife() <= 0)
                    gameOver();

                lbllife.setText(Integer.toString(principalTree.getLife()));
            }
            gamePane.getChildren().remove(simplebug2.getHitbox());
        });

        pathTransition2.setOnFinished(event -> {
            if( simplebug3.isDead()) {
                principalTree.setCoins(principalTree.getCoins()+simplebug3.getValue());
                lblcoins.setText(Integer.toString(principalTree.getCoins()));
            } else {
                principalTree.setLife(principalTree.getLife() - simplebug3.getDamage());
                if (principalTree.getLife() <= 0)
                    gameOver();

                lbllife.setText(Integer.toString(principalTree.getLife()));
            }
            gamePane.getChildren().remove(simplebug3.getHitbox());
        });
    }

    private void gameOver() {
        if(block1 || block2 || block3) {
            lblgameover.setVisible(true);
        } else {
            lblgamewin.setVisible(true);
        }
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
