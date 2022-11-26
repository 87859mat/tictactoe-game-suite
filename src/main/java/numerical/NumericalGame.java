package numerical;

import java.util.ArrayList;
import java.util.Arrays;

import javax.management.RuntimeErrorException;

import boardgame.BoardGame;
import boardgame.Saveable;

public class NumericalGame extends BoardGame implements Saveable{
    private enum GameState{ETURN, OTURN, EWIN, OWIN, TIE}
    private GameState state;
    private ArrayList<Integer> validOdd = new ArrayList<Integer>();
    private ArrayList<Integer> validEven = new ArrayList<Integer>();

    /**
     * Default constructor. Intilizes the game's {@code Grid} to
     * A 3 x 3 empty grid and initilizes the game's iterator to the
     * iterator of that grid's {@code data} attribute
     */
    public NumericalGame() {
        super(3,3);
        this.state = GameState.OTURN;
        this.setGrid(new NumericalGrid());
        validOdd.addAll(Arrays.asList(1,3,5,7,9));
        validEven.addAll(Arrays.asList(2,4,6,8));
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
        int inputNum;
        try {
           inputNum = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new RuntimeException("That is not a valid move");
        }
        
        if(!isValidMove(inputNum)) {
            throw new RuntimeException("That is not a valid move");
        } else if(across < 0 || across > 2 || down < 0 || down > 2) {
            throw new RuntimeException("ERROR: Somehow, an invalid row/column was selected");
        } else if(!this.getGrid().getValue(across + 1, down + 1).equals(" ")) {
            return false;
        } else {
            //we have to do +1 because the textUI's calculations can only use the real indexes (starting from 0)
            this.setValue(across + 1, down + 1, input);
            removePossibleMove(inputNum);
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
        if(!isValidMove(input)) {
            throw new RuntimeException("ERROR: Invalid Move");
        } else if(across < 0 || across > 2 || down < 0 || down > 2) {
            throw new RuntimeException("ERROR: Somehow, an invalid row/column was selected");
        } else if(!this.getGrid().getValue(across + 1, down + 1).equals(" ")) {
            return false;
        } else {
            //we have to do +1 because the textUI's calculations can only use the real indexes (starting from 0)
            this.setValue(across + 1, down + 1, input);
            removePossibleMove(input);
            return true;
        }
    }
        
    
   
    
    // /** 
    //  * Returns true when game is over, false otherwise.  Method must be overridden.
    //  * <br>Note that THIS IS THE METHOD THAT CHANGES THE GAME'S STATE MEMBER TO WIN/TIE
    //  * so it should be called immedeately after every player move
    //  * @return boolean
    //  */
    // @Override
    public boolean isDone(){
        if(checkForHorizontalWin() || checkForVerticalWin() || checkForDiagonalWin()) {
            if(this.getState() == GameState.ETURN) {
                this.setState(GameState.EWIN);
            } else if(this.getState() == GameState.OTURN) {
                this.setState(GameState.OWIN);
            }
            return true;
        } else if(this.gridIsFull()) {
            this.setState(GameState.TIE);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkForVerticalWin(){
        
            for(int i = 1; i <= 3; i++) {
                try{
                    if(Integer.parseInt(this.getGrid().getValue(i, 1)) + Integer.parseInt(this.getGrid().getValue(i, 2))
                    + Integer.parseInt(this.getGrid().getValue(i, 3)) == 15) {
                        return true;
                    }
                } catch(NumberFormatException e) {
                    return false;
                } 
            }
 
        return false;
    }

    private boolean checkForHorizontalWin(){
            
            for(int i = 1; i <= 3; i++) {
                try{      
                    if(Integer.parseInt(this.getGrid().getValue(1, i)) + Integer.parseInt(this.getGrid().getValue(2, i))
                        + Integer.parseInt(this.getGrid().getValue(3, i)) == 15) {
                            return true;
                        }
                } catch(NumberFormatException e) {
                    continue;
                }
            }

        return false;
    }

    private boolean checkForDiagonalWin() {
        try {
            if (Integer.parseInt(this.getGrid().getValue(1,1)) + Integer.parseInt(this.getGrid().getValue(2,2))
                + Integer.parseInt(this.getGrid().getValue(3,3)) == 15) {
                return true;
            } 
        } catch(NumberFormatException e){

        }

        try{
            if(Integer.parseInt(this.getGrid().getValue(1,3)) + Integer.parseInt(this.getGrid().getValue(2,2))
                + Integer.parseInt(this.getGrid().getValue(3,1)) == 15) {
                    return true;
            } 
        } catch (NumberFormatException e) {

        }
        return false;
    }

    public boolean gridIsFull() {
        boolean noEmptySpaces = true;
        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 3; j++) {
                if(this.getGrid().getValue(i,j).equals(" ")) {
                    noEmptySpaces = false;
                }
            }
        }
        return noEmptySpaces;
    }
   
    /**
     * Note that Player 1 is assumed to be X and player 2 is assumed to be O
     * @return 0 for tie, 1 for player 1, 2 for player 2, -1 if no winner
     */
    @Override
    public int getWinner() {
        if(this.state == GameState.OWIN) {
            return 1;
        } else if(this.state == GameState.EWIN) {
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
        if(this.getState() == GameState.ETURN) {
            return "Even's turn";
        } else if (this.getState() == GameState.OTURN) {
            return "Odd's turn";
        } else if (this.getState() == GameState.EWIN){
            return "Even has won";
        } else if (this.getState() == GameState.OWIN) {
            return "Odd has won";
        } else if (this.getState() == GameState.TIE){
            return "tie game";
        } else {
            return null;
        }
    }

    public String getCurrentCharacter() {
        if(this.state==GameState.ETURN) {
            return "E";
        } else if(this.state==GameState.OTURN) {
            return "O";
        } else {
            return null;
        }
    }

    public char[] getTurnCharacters() {
        return new char[]{'E', 'O'};
    }

    public boolean isValidMove(int move) {
        return (this.getState() == GameState.ETURN && validEven.contains(move)) 
        || (this.getState() == GameState.OTURN && validOdd.contains(move));
    }

    /*
     * The set of characters that are valid for representing moves is
     * identical to the set of characters valid for representing which 
     * player's turn it is
     */
    public char[] getMoveCharacters() {
        return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    public ArrayList<Integer> getCurrentMoveOptions() {
        if(this.state == GameState.ETURN) {
            return this.validEven;
        } else if(this.state == GameState.OTURN) {
            return this.validOdd;
        } else {
            return null;
        }
    }

    private GameState charToState(char stateChar) {
        if(stateChar == 'E') {
            return GameState.ETURN;
        } else if (stateChar == 'O') {
            return GameState.OTURN;
        } else {
            return null;
        }
    }

    public void switchTurns() {
        if(this.state == GameState.ETURN) {
            this.setState(GameState.OTURN);
        } else if(this.state == GameState.OTURN) {
            this.setState(GameState.ETURN);
        }
    }

    private void removePossibleMove(int move) {
        if(move % 2 == 0) {
            validEven.remove(validEven.indexOf(move));
        } else {
            validOdd.remove(validOdd.indexOf(move));
        }
    }

    public String getStringToSave(){
        String saveString = "";
        saveString += this.getCurrentCharacter() + "\n";
        saveString += this.getGrid().toString().replace("-+-+-\n", "");
        saveString = saveString.replace("|", ",");
        saveString = saveString.replace(" ", "");
        return saveString;

    }

    /* Object parses the string given as a parameter and restores
    its state based on the values in the string
    Note that this function assumes the String toLoad is valid since any
    Strings to be loaded should have been checked in GameSaveLoadManager*/
    public void loadSavedString(String toLoad) {
        String thisLine;
        String[] lines = toLoad.split("\n");
        this.setState(this.charToState(lines[0].charAt(0)));
        for(int i = 1; i < lines.length; i++) {
            thisLine = lines[i].replace(",", "");
            for(int j = 0; j < thisLine.length(); j++) {
                this.setValue(j + 1, i, Character.toString(thisLine.charAt(j)));
            }
        }
        this.setState(charToState(toLoad.charAt(0)));
        updateValidMoves();
    }

    private void updateValidMoves() {
        this.validEven = new ArrayList<Integer>(Arrays.asList(2,4,6,8));
        this.validOdd = new ArrayList<Integer>(Arrays.asList(1,3,5,7,9));
        String element = this.getNextValue();
        int elementNum;
        
        while(element != null){
            try {
                elementNum = Integer.parseInt(element);
                if(validEven.contains(elementNum)) {
                    validEven.remove(validEven.indexOf(elementNum));
                } else if(validOdd.contains(elementNum)) {
                    validOdd.remove(validOdd.indexOf(elementNum));
                }
            } catch (NumberFormatException e) {

            }
            element = this.getNextValue();
        } 
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
