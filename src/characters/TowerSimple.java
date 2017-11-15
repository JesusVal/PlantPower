package characters;

public class TowerSimple {

    private int damage = 100;
    private int velocity = 5;

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



}
