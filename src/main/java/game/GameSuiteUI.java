package game;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import utilities.Player;

public class GameSuiteUI extends JFrame{
    public static final int HEIGHT = 500;
    public static final int WIDTH = 900;
    
    private JPanel suiteContainer;
    private JMenuBar menuBar;
    private Player player1;
    private Player player2;

    public GameSuiteUI() {
        this.setTitle("Eyoel's Game Suite");
        this.setSize(WIDTH, HEIGHT);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void startScreen() {
        suiteContainer.removeAll();
    }

    private void setUpMenuBar() {
        menuBar = new JMenuBar();
        JMenu playerIOMenu = new JMenu("Player Save/Load");
        menuBar.add(playerIOMenu);

        JMenuItem savePlayerItem = new JMenuItem("Save current player's info");
        savePlayerItem.addActionListener(e -> savePlayerToFile());
        JMenuItem loadPlayerItem = new JMenuItem("Load a player's info from a file");
        loadPlayerItem.addActionListener(e -> savePlayerToFile());

        playerIOMenu.add(savePlayerItem);
        playerIOMenu.add(loadPlayerItem);
    }

    /*
     * Set up the panel that has the buttons for the user to choose whether they
     * would like to play tictacto or numerical tictactoe
     */
    private JPanel makeChooseGamePanel() {
        JPanel chooseGamePanel = new JPanel();
        chooseGamePanel.setLayout(new BoxLayout(chooseGamePanel, BoxLayout.X_AXIS));

        JButton tictactoeButton = new JButton("Tic Tac Toe");
        tictactoeButton.addActionListener(e -> startTTT());
        JButton numericalButton = new JButton("Numerical Tic Tac Toe");
        numericalButton.addActionListener(e -> startNumerical());

        chooseGamePanel.add(tictactoeButton);
        chooseGamePanel.add(numericalButton);

        return chooseGamePanel;
    }

    private JPanel makeWelcomeMessage() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        JLabel welcomeMsg = new JLabel("Welcome to Eyoel's Game Suite!");
        JLabel promptMsg = new JLabel("Click one of the buttons below to start a game");

        welcomePanel.add(welcomeMsg);
        welcomePanel.add(promptMsg);

        return welcomePanel;
    }

    private void startTTT() {

    }

    private void startNumerical() {

    }

    /*
     * 
     * NOTE - calling this should use the JFileChooser class to give the user a 
     * little pop up to choose their file
     */
    private void savePlayerToFile() {

    }

    /*
     * 
     * NOTE - calling this should use the JFileChooser class to give the user a 
     * little pop up to choose their file
     */
    private void loadPlayerToFile() {

    }

    public static void main(String []args) {
        GameSuiteUI gameUI = new GameSuiteUI();
        gameUI.setVisible(true);
    }
}
