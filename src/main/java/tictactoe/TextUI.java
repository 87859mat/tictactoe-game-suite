package tictactoe;

import java.util.Scanner;

import utilities.GameSaveLoadManager;

/**
 * Represents the textual user interface that the user can interact
 * with when playing this game though the newline
 */
public class TextUI {
    private Scanner inputScanner;
    private TicTacToeGame game;

    public TextUI() {
        inputScanner = new Scanner(System.in);
        game = new TicTacToeGame();
    }

    private void makeMove() {
        boolean validMove = false;
        String playerInput;
        int move;
        while(!validMove) {
            System.out.println(game);
            System.out.println("Please make your move by entering a single digit");
            System.out.print("Boxes are numbered from left to right with box #1 being the top left box, box #2");
            System.out.println(" being the top middle box, and box #9 being the bottom right box");
            System.out.print("If you would like to load a previously saved game, enter L. ");
            System.out.println("If you would like to save this current game, enter S. Enter Q to quit:");
            playerInput = inputScanner.nextLine();
            move = getGetMoveFromInput(playerInput) - 1;
            try{
                if(this.game.takeTurn(move % 3, move/3, this.game.getCurrentCharacter())){
                    validMove = true;
                } else if(playerInput.equalsIgnoreCase("L")) {
                    loadGameMessage();
                } else if(playerInput.equalsIgnoreCase("S")) {
                    saveGameMessage();
                } else if(playerInput.equalsIgnoreCase("Q")){
                    quitGameMessage();
                } else {
                    System.out.println("That was not a valid move\n");
                }
            } catch (RuntimeException e) {
                System.out.println("That was not a valid move\n");
            }
        }
    }

    private void loadGameMessage() {
        System.out.print("Please enter the file or relative path (from the 'A3' directory) ");
        System.out.println("of the file you want to load from:");
        String fName = inputScanner.nextLine();
        try {
            GameSaveLoadManager.loadGame(game, fName, game.getTurnCharacters(),game.getMoveCharacters());
        } catch(Exception e) {
            System.out.println("\n\nERROR: game could not be loaded from the selected file:\n" + e.getMessage());
        }
    }

    private void saveGameMessage() {
        System.out.print("Please enter the file or relative path (from the 'A3' directory) ");
        System.out.println("of the file where you would like to save the game:");
        String fileName = inputScanner.nextLine();
        try { 
            GameSaveLoadManager.save(game, fileName);
        } catch (Exception e) {
            System.out.println("\nERROR: Unable to save to that file:\n" + e.getMessage());
        }
    }

    private void quitGameMessage() {
        System.out.println("\n\nQuitting Game\n\nThanks for playing!\n");
        System.exit(1);
    }

    /*
     * Returns -1 if no integer can be found in the input
     */
    private int getGetMoveFromInput(String userInput) {
        int move;
        try{
            move = Integer.parseInt(userInput);
        } catch(NumberFormatException e) {
            move = -1;
        }
        return move;
    }

    public void play() {
        while(!game.isDone()) {
            makeMove();
            game.switchTurns();
        }
        System.out.println("\n\nGame Over: " + game.getGameStateMessage() + "\n"); //let's user know end result of game
    }

    public static void main(String[] args) {
        TextUI txt = new TextUI();
        txt.play();
    }

    
}
