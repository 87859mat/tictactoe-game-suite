package game;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import numerical.NumericalView;
import tictactoe.TicTacToeView;

import java.awt.BorderLayout;
import java.io.File;

import utilities.GameSaveLoadManager;
import utilities.Player;

/**
 * A class that extends JFrame and acts as the base of the Game suite.
 * It is responsible for switching between screens for different games and
 * maintaining/modifying UI elements that remain regardless of what game is
 * being played (e.g. the menuBar)
 * @author Eyoel Matiwos
 */
public class GameSuiteUI extends JFrame{
    public static final int HEIGHT = 500;
    public static final int WIDTH = 900;
    
    private JPanel suiteContainer;
    private JMenuBar menuBar;
    private JFileChooser playerIOFileChooser;
    private Player player1;
    private Player player2;

    public GameSuiteUI() {
        player1 = new Player();
        player2 = new Player();
        playerIOFileChooser = new JFileChooser(".");
        
        this.setTitle("Eyoel's Game Suite");
        this.setSize(WIDTH, HEIGHT);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        suiteContainer = new JPanel();
        this.add(suiteContainer);
        startScreen();
    }

    public void startScreen() {
        this.setUpMenuBar();
        this.setJMenuBar(this.menuBar);
        suiteContainer.removeAll();
        suiteContainer.setLayout(new BorderLayout());
        suiteContainer.add(makeWelcomeMessage(), BorderLayout.NORTH);
        suiteContainer.add(makeChooseGamePanel(), BorderLayout.CENTER);

        this.getContentPane().repaint();
        this.getContentPane().revalidate();
        //pack();
    }

    private void setUpMenuBar() {
        menuBar = new JMenuBar();
        JMenu playerIOMenu = new JMenu("Player Save/Load");
        menuBar.add(playerIOMenu);

        JMenuItem savePlayerItem = new JMenuItem("Save current player's info");
        savePlayerItem.addActionListener(e -> savePlayerToFile());
        JMenuItem loadPlayerItem = new JMenuItem("Load a player's info from a file");
        loadPlayerItem.addActionListener(e -> loadPlayerFromFile());

        playerIOMenu.add(savePlayerItem);
        playerIOMenu.add(loadPlayerItem);
    }

    /*
     * Set up the panel that has the buttons for the user to choose whether they
     * would like to play tictacto or numerical tictactoe
     */
    private JPanel makeChooseGamePanel() {
        JPanel chooseGamePanel = new JPanel();
        //chooseGamePanel.setLayout(new BoxLayout(chooseGamePanel, BoxLayout.X_AXIS));

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
        //welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        JLabel welcomeMsg = new JLabel("Welcome to Eyoel's Game Suite! Click one of the buttons below to start a game");

        welcomePanel.add(welcomeMsg);

        return welcomePanel;
    }

    private void startTTT() {
        suiteContainer.removeAll();
        suiteContainer.add(new TicTacToeView(this),BorderLayout.CENTER);
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
        //pack();
    }

    private void startNumerical() {
        suiteContainer.removeAll();
        suiteContainer.add(new NumericalView(this),BorderLayout.CENTER);
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
        //pack();
    }

    /*
     * 
     * NOTE - calling this should use the JFileChooser class to give the user a 
     * little pop up to choose their file
     */
    private void savePlayerToFile() {
        try{
            GameSaveLoadManager.save(player1, promptUserForFile());
        } catch(Exception e) {
            displayNotification("There was trouble saving this player: \n" + e.getMessage());
        }
    }

    /*
     * 
     * NOTE - calling this should use the JFileChooser class to give the user a 
     * little pop up to choose their file
     */
    private void loadPlayerFromFile() {
        try{
            GameSaveLoadManager.loadPlayer(player1, promptUserForFile());
        } catch(Exception e) {
            displayNotification("There was trouble loading this player: \n" + e.getMessage());
        }
    }

    /**
     * Increments the scores of the players based on the results of the game
     * @param winningPlayer An integer describing which player won the game.
     * 1 indicates that player 1 won the game, 2 indicates that player 2 won the
     * game and any other value indicates a tie
     */
    public void incrementPlayerScores(int winningPlayer) {
        if(winningPlayer == 1) {
            player1.updateWithWin();
            player2.updateWithNonWin();
        } else if(winningPlayer == 2) {
            player1.updateWithNonWin();
            player2.updateWithWin();
        } else {
            player1.updateWithNonWin();
            player2.updateWithNonWin();
        }
    }

    private String promptUserForFile() {
        if(playerIOFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String pathToFile = getRelativePathOfIOFile(playerIOFileChooser.getSelectedFile().getAbsolutePath());
                return pathToFile; 
            } catch(Exception e) {
                displayNotification("There was an issue with opening that file:\n" + e.getMessage());
            }
        } else {
            displayNotification("There was an issue with opening a file");
        }
        return null;
    }

    private String getRelativePathOfIOFile(String absolutePath) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        String pathToWorkingDirectory = currentDirectory.getAbsolutePath();
        
        String pathOfIOFile = absolutePath.replace(pathToWorkingDirectory, "");
        pathOfIOFile = pathOfIOFile.substring(1);
        return pathOfIOFile;
    } 

    private void displayNotification(String notiString) {
        JOptionPane.showMessageDialog(null, notiString);
    }

    //Accessor and Mutators

    public JMenuBar getSaveLoadMenuBar() {
        return this.menuBar;
    }

    public static void main(String[] args) {
        GameSuiteUI gameUI = new GameSuiteUI();
        gameUI.setVisible(true);
    }
}
