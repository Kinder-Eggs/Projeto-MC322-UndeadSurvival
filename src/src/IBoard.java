/**
 * Interface for the board
 * Declares methods that will be used publicly by other classes (Mostly Window)
 */
public interface IBoard {
    int getTableSize();
    int getTurns();
    IEntity[][] getEntities();
    int movePlayer(char direction) throws MovementOutOfBounds;
    int playerAttack(char direction) throws UselessAttack;
}
