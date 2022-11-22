package tictactoe;

public class TicTacToeGrid extends boardgame.Grid{
    public TicTacToeGrid() {
        super(3, 3);
    }


    /**
     * returns a string representation of the board in the format nececary for the
     * the text ui of a tic tac toe game
     * @return a string representation of the tic tac toe game being played on this grid
     */
    @Override
    public String getStringGrid() {
        String tictactoeString;
        tictactoeString = this.getStringGrid().replace(" \n", "\n-+-+-");
        tictactoeString.replace(" ", "|");
        return tictactoeString;
    }
}
