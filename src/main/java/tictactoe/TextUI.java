package tictactoe;

import java.util.Scanner;

/**
 * Represents the textual user interface that the user can interact
 * with when playing this game though the newline
 */
public class TextUI {
    Scanner inputScanner;
    TicTacToeGame tGame;

    public TextUI() {
        inputScanner = new Scanner(System.in);
        tGame = new TicTacToeGame();
    }

    private void makeMove() {
        boolean validMove = false;
        String playerInput;
        int move;
        while(!validMove) {
            System.out.println("Please make your move by entering a single digit");
            System.out.print("Boxes are numbered from left to right with box #1 being the top left box, box #2");
            System.out.println(" being the top middle box, and box #9 being the bottom right box");
            playerInput = inputScanner.nextLine();
            move = getGetMoveFromInput(playerInput);
            if(this.tGame.takeTurn(move % 3, move/3, this.tGame.getCurrentCharacter())){
                validMove = true;
            } else {
                System.out.println("That was not a valid move\n");
            }
        }
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
        while(!tGame.isDone()) {
            System.out.println(tGame);
            makeMove();
        }
        System.out.println(tGame.getGameStateMessage()); //let's user know end result of game
    }

    public static void main(String[] args) {
        TextUI txt = new TextUI();
        txt.play();
    }

    
}
