/**
 * Abstract class to represent any general entity
 * Implements IEntity
 * Has position saved as private ints and methods to get and increment positions
 */
public abstract class Entity implements IEntity {
    private int coordX, coordY;

    public Entity(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    @Override
    public int getCoordX() {
        return this.coordX;
    }

    @Override
    public int getCoordY() {
        return this.coordY;
    }

    @Override
    public void incrementCoordX(int coordX) {
        this.coordX += coordX;
    }

    @Override
    public void incrementCoordY(int coordY) {
        this.coordY += coordY;
    }

}
