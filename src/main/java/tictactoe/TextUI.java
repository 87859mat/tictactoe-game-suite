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

    
}
