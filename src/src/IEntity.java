/**
 * Interface for entity
 * Declares methods that will be used publicly by other classes (Mostly Board)
 */
public interface IEntity {
    int getCoordX();
    int getCoordY();
    void incrementCoordX(int coordX);
    void incrementCoordY(int coordY);
    String getType();
}
