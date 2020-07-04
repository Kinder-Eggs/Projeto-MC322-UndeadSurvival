/**
 * Exception for a failed attempt to move up
 */
public class NoUpperGrid extends MovementOutOfBounds {
    public NoUpperGrid() {
        super();
    }

    public NoUpperGrid(String message) {
        super(message);
    }
}