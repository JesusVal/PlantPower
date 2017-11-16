package characters;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PrincipalTree {

    private ImageView treeimg = new ImageView("resourses/arbolnavidad.png");
    private int life = 1000;
    private int coins = 500;

    public PrincipalTree () {

    }

    public ImageView getTreeimg() {
        return treeimg;
    }
    public void setTreeimg(ImageView treeimg) {
        this.treeimg = treeimg;
    }
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }


}
