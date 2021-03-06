package characters;


import javafx.scene.shape.Rectangle;

public class TowerSimple {

    private int damage = 400;
    private int velocity = 5;
    private int cost = 400;
    private Rectangle rectangle;

    public TowerSimple(int damage, int velocity) {
        this.damage = damage;
        this.velocity = velocity;
    }

    public TowerSimple() { }

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getVelocity() {
        return velocity;
    }
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
