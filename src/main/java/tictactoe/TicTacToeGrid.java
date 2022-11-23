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
        // String tictactoeString;
        // tictactoeString = super.getStringGrid().replace("\n ", "\n-+-+-\n");
        // tictactoeString = tictactoeString.replace(" ", "|");
        // return tictactoeString;
        
        String gridString = "";
        for(int i = 1; i <= 3; i++) {
            gridString += this.getValue(1, i);
            gridString += "|" + this.getValue(2, i) + "|";
            gridString += this.getValue(3, i);
            if(i < 3) {
                gridString += "\n-+-+-\n";
            }
        }
        return gridString;
    }


}
