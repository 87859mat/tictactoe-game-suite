package tictactoe;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import game.GameSuiteUI;
import utilities.GameSaveLoadManager;
import boardgame.ui.PositionAwareButton;

public class TicTacToeView extends JPanel{
    private PositionAwareButton[][] buttons;
    private GameSuiteUI rootFrame;
    private TicTacToeGame game;
    private JButton quitButton;
    private JLabel gameStateText;
    private JFileChooser gameIOFileChooser;

    public TicTacToeView(GameSuiteUI rootUI) {
        super();
        this.rootFrame = rootUI;
        this.game = new TicTacToeGame();
        gameIOFileChooser = new JFileChooser(".");

        switchToGameMenuBar();
        this.setLayout(new BorderLayout());
        this.add(makeGameStateText(),BorderLayout.NORTH);
        this.add(makeButtonGrid(3, 3));
        updateGameStateText();
    }

    private void switchToGameMenuBar() {
        JMenuBar rootMenuBar = rootFrame.getSaveLoadMenuBar();
        rootMenuBar.removeAll();
        JMenu gameIOMenu = new JMenu("Game Save/Load");
        rootMenuBar.add(gameIOMenu);

        JMenuItem saveGameItem = new JMenuItem("Save current game a to file");
        saveGameItem.addActionListener(e -> saveGameToFile());
        JMenuItem loadGameItem = new JMenuItem("Load a game from a file");
        loadGameItem.addActionListener(e -> loadGameFromFile());

        gameIOMenu.add(saveGameItem);
        gameIOMenu.add(loadGameItem);
    }

    private JPanel makeButtonGrid(int tall, int wide){
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
        for (int y=0; y<wide; y++){
            for (int x=0; x<tall; x++){ 
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x+1); //made the choice to be 1-based
                buttons[y][x].setDown(y+1);
                buttons[y][x].addActionListener(e->{
                                        displayMoveMade(e);
                                        updateGameStateText();
                                        });
                panel.add(buttons[y][x]);
            }
        }
        return panel;    
    }
    
    private JPanel makeGameStateText() {
        this.gameStateText = new JLabel();
        JPanel textPanel = new JPanel();

        this.quitButton = new JButton("Quit Game");
        quitButton.addActionListener(e -> returnToSuiteMain());
        textPanel.add(this.gameStateText);
        textPanel.add(quitButton);

        return textPanel;
    }

    private void displayMoveMade(ActionEvent clickEvent) {
        PositionAwareButton boxSelected = (PositionAwareButton)clickEvent.getSource();
        try {
            if(game.takeTurn(boxSelected.getAcross() - 1, boxSelected.getDown() - 1, this.game.getCurrentCharacter())) {
                boxSelected.setText(game.getCurrentCharacter());
                checkGameOver();
            } else {
                displayNotification("ERROR: the box you selected is already full");
            }
        } catch (RuntimeException e) {
            displayNotification(e.getMessage());
        }
    }

    private void checkGameOver() {
        if(this.game.isDone()){
            quitButton.setText("Return to start screen");    
            this.gameStateText.setText("Game Over: " + this.game.getGameStateMessage());
            disableButtonGrid();
            rootFrame.incrementPlayerScores(game.getWinner());
        } else {
            game.switchTurns();
        }
    }

    /*
     * 
     * 
     */
    private void updateButtonGrid(){
        for(int i = 0; i < game.getHeight(); i++) {
            for(int j = 0; j < game.getWidth(); j++){
                buttons[i][j].setText(game.getCell(buttons[i][j].getAcross(), buttons[i][j].getDown()));
            }
        }
    }

    private void updateGameStateText(){
        String currentStateText = this.game.getGameStateMessage();
        this.gameStateText.setText(currentStateText);
    }

    private void displayNotification(String notiString) {
        JOptionPane.showMessageDialog(null, notiString);
    }

    /**
     * Disables all buttons on the button grid
     */
    private void disableButtonGrid(){
        for(int i = 0; i < game.getHeight(); i++) {
            for(int j = 0; j < game.getWidth(); j++){
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void returnToSuiteMain() {
        rootFrame.getSaveLoadMenuBar().removeAll();
        rootFrame.startScreen();
    }

    private void saveGameToFile(){
        if(gameIOFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String pathToFile = getRelativePathOfIOFile(gameIOFileChooser.getSelectedFile().getAbsolutePath());
                GameSaveLoadManager.save(this.game, pathToFile);
            } catch(Exception e) {
                displayNotification("There was an issue with saving your file:\n" + e.getMessage());
            }
        } else {
            displayNotification("There was an issue with saving your file:\n");
        }
    }

    private void loadGameFromFile() {
        if(gameIOFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String pathToFile = getRelativePathOfIOFile(gameIOFileChooser.getSelectedFile().getAbsolutePath());
                GameSaveLoadManager.loadGame(game, pathToFile, game.getTurnCharacters(), game.getMoveCharacters());
            } catch(Exception e) {
                displayNotification("There was an issue with loading your file:\n" + e.getMessage());
            }
            updateButtonGrid();
        } else {
            displayNotification("There was an issue with loading your file:\n");
        }
    }

    
    private String getRelativePathOfIOFile(String absolutePath) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        String pathToWorkingDirectory = currentDirectory.getAbsolutePath();
        
        String pathOfIOFile = absolutePath.replace(pathToWorkingDirectory, "");
        pathOfIOFile = pathOfIOFile.substring(1);
        return pathOfIOFile;
    }  
}
