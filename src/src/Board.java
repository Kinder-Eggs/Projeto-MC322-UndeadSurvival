import java.util.Random;

/**
 * Class to represent game board
 * Has tableSize, number of turns, number of enemies and a vector of entities saved as private values
 * Keeps the player class saved
 * Has methods to initialize board, move the player, move the enemies, attack the enemies and spawn enemies
 *      all of which changes the entities vector that is read by the window to render the game
 */
public class Board implements IBoard{
    private int tableSize;
    private IEntity[][] entities;
    private int turns;
    private Player player;
    private int nEnemies;


    public Board(int tableSize) {
        this.tableSize = tableSize;
        entities = new IEntity[tableSize][tableSize];
        initializeBoard();
        turns = 0;
    }


    public int getTableSize() {
        return tableSize;
    }


    public int getTurns() { return turns; }

    // Return the entities vector. Used by Window to render the entities on the proper places
    public IEntity[][] getEntities(){
        return entities;
    }

    // Initialize board with main set positions. Can be changed
    private void initializeBoard() {
        player = new Player(tableSize/2, tableSize/2);
        entities[tableSize/2][tableSize/2] = player;
        entities[0][0] = new Enemy(0, 0);
        entities[tableSize-1][0] = new Enemy(0, tableSize-1);
        nEnemies = 2;
    }


    public int movePlayer(char direction) throws MovementOutOfBounds {
        switch(direction) {
            case 'w':  // Move up
                if(player.getCoordY() == 0  || entities[player.getCoordY() - 1][player.getCoordX()] != null)
                    throw new NoUpperGrid("No space to move up");
                entities[player.getCoordY()][player.getCoordX()] = null;
                player.incrementCoordY(-1);
                entities[player.getCoordY()][player.getCoordX()] = player;
                break;
            case 's':  // Move down
                if(player.getCoordY() + 1 == tableSize  || entities[player.getCoordY() + 1][player.getCoordX()] != null)
                    throw new NoUpperGrid("No space to move down");
                entities[player.getCoordY()][player.getCoordX()] = null;
                player.incrementCoordY(1);
                entities[player.getCoordY()][player.getCoordX()] = player;
                break;
            case 'a':  // Move left
                if(player.getCoordX() == 0  || entities[player.getCoordY()][player.getCoordX() - 1] != null)
                    throw new NoUpperGrid("No space to move to the left");
                entities[player.getCoordY()][player.getCoordX()] = null;
                player.incrementCoordX(-1);
                entities[player.getCoordY()][player.getCoordX()] = player;
                break;
            case 'd':  // Move right
                if(player.getCoordX() + 1 == tableSize  || entities[player.getCoordY()][player.getCoordX() + 1] != null)
                    throw new NoUpperGrid("No space to move to the right");
                entities[player.getCoordY()][player.getCoordX()] = null;
                player.incrementCoordX(1);
                entities[player.getCoordY()][player.getCoordX()] = player;
                break;
        }
        turns++;
        return moveEnemies();
    }


    public int playerAttack(char direction) throws UselessAttack {
        switch(direction) {
            case 'i':  // Attack up
                if(player.getCoordY() == 0 || entities[player.getCoordY() - 1][player.getCoordX()] == null)
                    throw new EmptyUpperGrid("Nothing to attack up");
                entities[player.getCoordY() - 1][player.getCoordX()] = null;
                nEnemies--;
                break;
            case 'k':  // Attack down
                if(player.getCoordY() + 1 == tableSize || entities[player.getCoordY() + 1][player.getCoordX()] == null)
                    throw new EmptyUpperGrid("Nothing to attack down");
                entities[player.getCoordY() + 1][player.getCoordX()] = null;
                nEnemies--;
                break;
            case 'j':  // Attack left
                if(player.getCoordX() == 0 || entities[player.getCoordY()][player.getCoordX() - 1] == null)
                    throw new EmptyUpperGrid("Nothing to attack to the left");
                entities[player.getCoordY()][player.getCoordX() - 1] = null;
                nEnemies--;
                break;
            case 'l':  // Attack right
                if(player.getCoordX() + 1 == tableSize || entities[player.getCoordY()][player.getCoordX() + 1] == null)
                    throw new EmptyUpperGrid("Nothing to attack to the right");
                entities[player.getCoordY()][player.getCoordX() + 1] = null;
                nEnemies--;
                break;
        }
        turns++;
        return moveEnemies();
    }


    private int moveEnemies() {
        IEntity[][] auxEntities = new IEntity[tableSize][tableSize];
        for(int i = 0; i < tableSize; i++) {  // Creates an empty auxiliar IEntity vector with just the player in it
            for(int j = 0; j < tableSize; j++) {
                if(player.getCoordY() == i && player.getCoordX() == j)
                    auxEntities[i][j] = entities[i][j];
                else
                    auxEntities[i][j] = null;
            }
        }

        // Procceds to fill the auxiliary vector with the new enemy positions after they move towards the player
        for(int i = 0; i < tableSize; i++) {
            for(int j = 0; j < tableSize; j++) {
                if(entities[i][j] != null) {
                    if(entities[i][j].getType() == "Enemy") {
                        if(i == player.getCoordY()) {
                            if(j < player.getCoordX()) {
                                if(auxEntities[i][j+1] == null)
                                    auxEntities[i][j + 1] = new Enemy(j + 1, i);
                                else
                                    auxEntities[i][j] = entities[i][j];
                                if(j+1 == player.getCoordX())
                                    return 0;
                            } else {
                                if(auxEntities[i][j-1] == null)
                                    auxEntities[i][j - 1] = new Enemy(j - 1, i);
                                 else
                                     auxEntities[i][j] = entities[i][j];
                                if(j-1 == player.getCoordX())
                                    return 0;
                            }
                        } else if(j == player.getCoordX()) {
                            if(i < player.getCoordY()) {
                                if(auxEntities[i+1][j] == null)
                                    auxEntities[i + 1][j] = new Enemy(j, i + 1);
                                else
                                     auxEntities[i][j] = entities[i][j];
                                if(i+1 == player.getCoordY())
                                    return 0;
                            } else {
                                if (auxEntities[i - 1][j] == null)
                                    auxEntities[i - 1][j] = new Enemy(j, i - 1);
                                else
                                    auxEntities[i][j] = entities[i][j];
                                if (i - 1 == player.getCoordY())
                                    return 0;
                            }
                        } else {
                            Random random = new Random();
                            if(random.nextInt(2) == 1) {
                                if(j < player.getCoordX()) {
                                    if(auxEntities[i][j+1] == null)
                                        auxEntities[i][j + 1] = new Enemy(j + 1, i);
                                    else
                                        auxEntities[i][j] = entities[i][j];
                                } else {
                                    if(auxEntities[i][j-1] == null)
                                        auxEntities[i][j - 1] = new Enemy(j - 1, i);
                                    else
                                        auxEntities[i][j] = entities[i][j];
                                }
                            } else {
                                if(i < player.getCoordY()) {
                                    if(auxEntities[i+1][j] == null)
                                        auxEntities[i + 1][j] = new Enemy(j, i + 1);
                                    else
                                        auxEntities[i][j] = entities[i][j];
                                } else {
                                    if(auxEntities[i-1][j] == null)
                                        auxEntities[i - 1][j] = new Enemy(j, i - 1);
                                    else
                                        auxEntities[i][j] = entities[i][j];
                                }
                            }
                        }
                    }
                }
            }
        }
        entities = auxEntities;
        spawnEnemies();
        return 1;
    }

    // Randomly spawns an amount of enemies from 0 to 4 based on the turn
    private void spawnEnemies() {
        if(nEnemies > 5) // If there is already 6 or more enemies on the board, doesnt spawn more
            return;
        Random random = new Random();
        int randomInt = random.nextInt(turns*5);
        if(randomInt >= 100) {  // Spawns 4 enemies, can only happen from turn 20 onwards
            if(entities[0][0] == null) {
                entities[0][0] = new Enemy(0, 0);
                nEnemies++;
            }
            if(entities[tableSize-1][0] == null) {
                entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                nEnemies++;
            }
            if(entities[0][tableSize-1] == null) {
                entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                nEnemies++;
            }
            if(entities[tableSize-1][tableSize-1] == null) {
                entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                nEnemies++;
            }
        } else if (randomInt >= 75) {  // Spawns 3 enemies, can only happen from turn 15 onwards
            switch(random.nextInt(4)) {  // Chooses 3 random corners to spawn the enemies
                case 0:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    break;
                case 1:
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
                case 2:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
                case 3:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
            }
        // Spawns 2 enemies, can only happen on turn 8 onwards. Always happen when there are no enemies left on the board
        } else if (randomInt >= 40 || nEnemies == 0) {
            switch(random.nextInt(6)) {  // Chooses 2 random corners to spawn the enemies
                case 0:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    break;
                case 1:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    break;
                case 2:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
                case 3:
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    break;
                case 4:
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
                case 5:
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
            }
        } else if (randomInt >= 15) {  // Spawns 1 enemy on the board, can only happen from turn 3 onwards
            switch(random.nextInt(4)) {  // Chooses a random corner for the enemy to spawn
                case 0:
                    if(entities[0][0] == null) {
                        entities[0][0] = new Enemy(0, 0);
                        nEnemies++;
                    }
                    break;
                case 1:
                    if(entities[tableSize-1][0] == null) {
                        entities[tableSize - 1][0] = new Enemy(0, tableSize - 1);
                        nEnemies++;
                    }
                    break;
                case 2:
                    if(entities[0][tableSize-1] == null) {
                        entities[0][tableSize - 1] = new Enemy(tableSize - 1, 0);
                        nEnemies++;
                    }
                    break;
                case 3:
                    if(entities[tableSize-1][tableSize-1] == null) {
                        entities[tableSize - 1][tableSize - 1] = new Enemy(tableSize - 1, tableSize - 1);
                        nEnemies++;
                    }
                    break;
            }
        }
    }
}
