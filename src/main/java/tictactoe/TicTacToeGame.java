package tictactoe;

import boardgame.BoardGame;
import boardgame.Saveable;

/**
 * This class represents a game of tic tac toe and 
 * is responsible for tracking and manipulating the state
 * of that tic tac toe game
 * 
 * @author Eyoel Matiwos
 */
public class TicTacToeGame extends BoardGame implements Saveable {
    private enum GameState{XTURN, OTURN, XWIN, OWIN, TIE};
    private GameState state;

    /**
     * Default constructor. Intilizes the game's {@code Grid} to
     * A 3 x 3 empty grid and initilizes the game's iterator to the
     * iterator of that grid's {@code data} attribute
     */
    public TicTacToeGame() {
        super(3,3);
        this.state = GameState.XTURN;
        this.setGrid(new TicTacToeGrid());
    }

        
    /** 
     * Overloaded method that facilitates the placement of an input on the board 
     * with String input. Method should be overriden 
     * to validate input prior to placing the input value.  Overridden method 
     * should throw exceptions when validating input.
     * @param across across index, 1 based
     * @param down  down index, 1 based
     * @param input  String input from game
     * @return boolean  returns true if input was placed false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, String input) {
        if(!input.equals(this.getCurrentCharacter())) {
            return false;
        } else if(across < 0 || across > 2 || down < 0 || down > 2) {
            return false;
        } else if(!this.getGrid().getValue(across + 1, down + 1).equals(" ")) {
            return false;
        } else {
            //we have to do +1 because the textUI's calculations can only use the real indexes (starting from 0)
            this.setValue(across + 1, down + 1, input);
            switchTurns();
            return true;
        }
    }


    
    /** 
     * Overloaded method that facilitates the placement of an input on the board
     *  with integer input. Method should be overriden 
     * to validate input prior to placing the input value. 
     *  Overridden method should throw exceptions when validating input.<br><br>
     * This should never be used in a tic tac toe game
     * @param across across index, zero based
     * @param down  down index, zero based
     * @param input  int input from game
     * @return boolean  returns true if input was placed false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, int input){
        return false;
    }
        
    
   
    
    /** 
     * Returns true when game is over, false otherwise.  Method must be overridden.
     * @return boolean
     */
    @Override
    public boolean isDone(){
        if(this.state == GameState.XWIN || this.state == GameState.OWIN || this.state == GameState.TIE) {
            return true;
        } else {
            return false;
        }
    }
   
    /**
     * Note that Player 1 is assumed to be X and player 2 is assumed to be O
     * @return 0 for tie, 1 for player 1, 2 for player 2, -1 if no winner
     */
    @Override
    public int getWinner() {
        if(this.state == GameState.XWIN) {
            return 1;
        } else if(this.state == GameState.OWIN) {
            return 2;
        } else if(this.state == GameState.TIE) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Takes this game's {@code state} attribute and returns a string representation
     * of it
     * @return a string representing the state of the board
     */
    @Override
    public String getGameStateMessage(){
        if(this.getState() == GameState.XTURN) {
            return "X's turn";
        } else if (this.getState() == GameState.OTURN) {
            return "O's turn";
        } else if (this.getState() == GameState.XWIN){
            return "X has won";
        } else if (this.getState() == GameState.OWIN) {
            return "O has won";
        } else if (this.getState() == GameState.TIE){
            return "tie game";
        } else {
            return null;
        }
    }

    public String getCurrentCharacter() {
        if(this.state==GameState.XTURN) {
            return "X";
        } else if(this.state==GameState.OTURN) {
            return "O";
        } else {
            return null;
        }
    }

    public char[] getTurnCharacters() {
        return new char[]{'X', 'O'};
    }

    private void switchTurns() {
        if(this.state == GameState.XTURN) {
            this.setState(GameState.OTURN);
        } else if(this.state == GameState.OTURN) {
            this.setState(GameState.XTURN);
        }
    }

    public String getStringToSave(){
        return null;
    }
    /* Object parses the string given as a parameter and restores
    its state based on the values in the string*/
    public void loadSavedString(String toLoad) {
        
    }

    /**
     * Returns a string briefly summarizing the game's state.<br><br>
     * Should only be used for output when using the textual UI
     * @return a string brief string with the type of game, game's state (who's turn/win
     * it is), and a representation of the current state of the board
     */
    @Override
    public String toString() {
        return "***Tic tac tac game***\nTurn/state: " + getGameStateMessage() + "\n\n" + this.getGrid().toString();
    }


    //Accessors and Mutators
    
    
    public GameState getState() {
        return this.state;
    }

    public void setState(GameState gState) {
        this.state = gState;
    }
}
