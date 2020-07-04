/**
 * Exception for a failed attempt to move down
 */
public class NoDownGrid extends MovementOutOfBounds {
    public NoDownGrid() {
        super();
    }

    public NoDownGrid(String message) {
        super(message);
    }
}