/**
 * Class to represent the enemies
 * Extends Entity abstract classs
 * Has method to return "Enemy" as type
 */
public class Enemy extends Entity {

    public Enemy(int coordX, int coordY) {
        super(coordX, coordY);
    }

    @Override
    public String getType() {
        return "Enemy";
    }
}
