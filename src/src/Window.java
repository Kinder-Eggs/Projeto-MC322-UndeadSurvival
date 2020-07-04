import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Class that renders the window and controls the game
 * Extends JFrame to render game panels
 * Has private values for mode, board, and multiple panels
 * Has methods for updating panels, setting the different game modes (tutorial, game and game over)
 * Implements KeyListener to read keyboard input for the game
 */
public class Window extends JFrame implements KeyListener {
    private int mode;  // 0 Represents tutorial mode, 1 represents game, 2 represents game over
    private IBoard board;
    private JPanel[][] imagePanels;
    private JPanel gridPanel;
    private Container mainPanel;
    private GridLayout grid;


    public Window(int tableSize) {
        super();
        mode = 0;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1050);

        board = new Board(tableSize);

        mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());

        gridPanel = new JPanel();
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        addKeyListener(this);

        loadTutorial();

        grid = new GridLayout(0, board.getTableSize());
        gridPanel.setLayout(grid);
        imagePanels = new JPanel[board.getTableSize()][board.getTableSize()];

        updatePanels();
    }


    private void updatePanels() {
        gridPanel.removeAll();  // Deletes all images loaded on grids to rebuild the board

        for(int i = 0; i < board.getTableSize(); i++) {
            for(int j = 0; j < board.getTableSize(); j++) {
                imagePanels[i][j] = new JPanel();
                imagePanels[i][j].setLayout(new BorderLayout());
                IEntity[][] entities = board.getEntities();
                if(entities[i][j] != null) {
                    if (entities[i][j].getType() == "Player") {
                        imagePanels[i][j].add(new JLabel(new ImageIcon(getClass().getResource("/Character.jpg"))), BorderLayout.CENTER);
                    } else if (entities[i][j].getType() == "Enemy") {
                        imagePanels[i][j].add(new JLabel(new ImageIcon(getClass().getResource("/Enemy.jpg"))), BorderLayout.CENTER);
                    }
                }else {
                        imagePanels[i][j].add(new JLabel(new ImageIcon(getClass().getResource("/Border.jpg"))), BorderLayout.CENTER);
                }
                gridPanel.add(imagePanels[i][j]);
            }
        }
        mainPanel.removeAll();  // Removes existing panels from main panel to add the updated ones
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        JLabel roundsTextPanel = new JLabel("Round: " + board.getTurns());
        roundsTextPanel.setFont(new Font("Verdana", Font.BOLD,50));
        mainPanel.add(roundsTextPanel, BorderLayout.SOUTH);
        SwingUtilities.updateComponentTreeUI(this);
    }


    private void gameOver() {
        mode = 2;
        mainPanel.removeAll();
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(1,1));
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());
        gameOverPanel.add(new JLabel(new ImageIcon(getClass().getResource("/GameOver.jpg"))), BorderLayout.SOUTH);
        gridPanel.add(gameOverPanel);
        JLabel text = new JLabel("You survived " + board.getTurns() + " rounds!");
        text.setFont(new Font("Verdana",Font.BOLD,50));
        mainPanel.add(text, BorderLayout.SOUTH);

        SwingUtilities.updateComponentTreeUI(this);
    }


    private void loadTutorial() {
        gridPanel.setLayout(new GridLayout(1,1));
        JPanel tutorialPanel = new JPanel();
        tutorialPanel.setLayout(new BorderLayout());
        tutorialPanel.add(new JLabel(new ImageIcon(getClass().getResource("/Tutorial.jpg"))), BorderLayout.SOUTH);
        gridPanel.add(tutorialPanel);

        setVisible(true);
        SwingUtilities.updateComponentTreeUI(this);
        while(mode == 0) {  // Waits for mode to change (Keyboard input) to finish the task and load the game
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void restart() {
        board = new Board(6);
        gridPanel.setLayout(grid);
        mode = 1;
        updatePanels();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if(mode == 0) {  // While in tutorial mode, any key changes to game mode
            mode = 1;
        } else if(mode == 2) {  // While in game over mode, pressing r will restart the game
            if (keyEvent.getKeyChar() == 'r') {
                restart();
            }
        } else {
            char command = keyEvent.getKeyChar();
            if (command == 'w' || command == 's' || command == 'a' || command == 'd') {
                try {
                    if (board.movePlayer(command) == 0) {
                        gameOver();
                    } else
                        updatePanels();
                } catch (MovementOutOfBounds error) {
                    System.out.println("Non-allowed movement detected!");
                    System.out.println(error.getMessage());
                }
            } else if (command == 'i' || command == 'k' || command == 'j' || command == 'l') {
                try {
                    if (board.playerAttack(command) == 0) {
                        gameOver();
                    } else
                        updatePanels();
                } catch (UselessAttack error) {
                    System.out.println("Non-allowed attack detected!");
                    System.out.println(error.getMessage());
                }
            }
        }
    }

    // Not used functions from KeyListener Class
    public void keyPressed(KeyEvent keyEvent) { }
    public void keyReleased(KeyEvent keyEvent) { }
}


