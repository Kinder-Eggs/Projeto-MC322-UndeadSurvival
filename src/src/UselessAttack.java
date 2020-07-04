/**
 * Base exception for failed attempts to attack
 * Is extended by EmptyDownGrid, EmptyLeftGrid, EmptyRightGrid and EmptyUpperGrid
 */
public class UselessAttack extends Exception {
    public UselessAttack() {
        super();
    }

    public UselessAttack(String message) {
        super(message);
    }
}