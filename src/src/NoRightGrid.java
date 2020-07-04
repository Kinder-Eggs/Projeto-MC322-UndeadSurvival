/**
 * Exception for a failed attempt to move right
 */
public class NoRightGrid extends MovementOutOfBounds {
    public NoRightGrid() {
        super();
    }

    public NoRightGrid(String message) {
        super(message);
    }
}
