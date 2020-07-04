/**
 * Exception for a failed attempt to move left
 */
public class NoLeftGrid extends MovementOutOfBounds {
    public NoLeftGrid() {
        super();
    }

    public NoLeftGrid(String message) {
        super(message);
    }
}