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
     * Returns a string briefly summarizing the game's state.<br><br>
     * Should only be used for output when using the textual UI
     * @return a string brief string with the type of game, game's state (who's turn/win
     * it is), and a representation of the current state of the board
     */
    @Override
    public String toString() {
        return "***Tic tac tac game***\nTurn/state: " + getGameStateMessage() + "\n\n" + this.getGrid().toString();
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

    private void switchTurns() {
        if(this.state == GameState.XTURN) {
            this.setState(GameState.OTURN);
        } else if(this.state == GameState.OTURN) {
            this.setState(GameState.XTURN);
        }
    }


    //Accessors and Mutators
    
    
    public GameState getState() {
        return this.state;
    }

    public void setState(GameState gState) {
        this.state = gState;
    }
}
