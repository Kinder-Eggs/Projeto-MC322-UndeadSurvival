/**
 * Base exception for failed attempts to move
 * Is extended by NoDownGrid, NoLeftGrid, NoRightGrid and NoUpperGird
 */
public class MovementOutOfBounds extends Exception {
    public MovementOutOfBounds() {
        super();
    }

    public MovementOutOfBounds(String message) {
        super(message);
    }
}