package tictactoe;

import boardgame.BoardGame;
import boardgame.Saveable;

/**
 * This class represents a game of tic tac toe and 
 * is responsible for tracking and manipulating the state
 * of that tic tac toe game
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
    }

    /**
     * Returns a string briefly summarizing the game's state.<br><br>
     * Should only be used for output when using the textual UI
     * @return a string brief string with the type of game, game's state (who's turn/win
     * it is), and a representation of the current state of the board
     */
    @Override
    public String toString() {
        return "Tic tac tac game\nTurn/state: " + stateToTurnString() + "\n\n" + this.getGrid().toString();
    }

    protected String stateToTurnString() {
        if(this.getState() == GameState.XTURN) {
            return "X";
        } else if (this.getState() == GameState.OTURN) {
            return "O";
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


    //Accessors and Mutators
    
    
    public GameState getState() {
        return this.state;
    }

    public void setState(GameState gState) {
        this.state = gState;
    }
}
