/**
 * Class to represent the player
 * Extends Entity abstract class
 * Has method to return "Player" as type
 */
public class Player extends Entity {

    public Player(int coordX, int coordY) {
        super(coordX, coordY);
    }

    @Override
    public String getType() { return "Player"; }
}
