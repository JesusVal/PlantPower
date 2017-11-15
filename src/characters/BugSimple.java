package characters;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class BugSimple {

    private int lifepoints = 500;
    private int velocity = 2;
    private int damage = 100;
    private Rectangle hitbox;
    private Image simplebugImage = new Image("resourses/simplebug.png");

    public BugSimple () {
        this(500,2,100);
    }

    public BugSimple(int lifepoints, int velocity, int damage) {
        this.lifepoints = lifepoints;
        this.velocity = velocity;
        this.damage = damage;

        this.hitbox = new Rectangle(0, 0, 40, 40);
        hitbox.setArcHeight(10);
        hitbox.setArcWidth(10);
        hitbox.setFill(Color.TRANSPARENT);
        hitbox.setFill(new ImagePattern(simplebugImage));
    }



    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    public int getLifepoints() {
        return lifepoints;
    }
    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
    }
    public int getVelocity() {
        return velocity;
    }
    public Image getSimplebugImage() {
        return simplebugImage;
    }
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setSimplebugImage(Image simplebugImage) {
        this.simplebugImage = simplebugImage;
    }

    public boolean isDead () {
        return (this.lifepoints <= 0) ? true : false;
    }

    public boolean damaged (int damagepoints) {
        this.lifepoints -= damagepoints;
        return this.isDead();
    }

}
