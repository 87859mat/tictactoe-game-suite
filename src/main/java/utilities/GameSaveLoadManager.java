package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import boardgame.Saveable;

/**
 * Responsible for doing all file loading and saving operations (which
 * includes validing data that is about to be saved and loaded)
 * @author Eyoel Matiwos
 */
public class GameSaveLoadManager {
    public static boolean save(Saveable toSave, String fileName) {
        try(FileWriter fWriter = new FileWriter(fileName)) {
            fWriter.write(toSave.getStringToSave());
        } catch(IOException e) {
            return false;
        }

        return true;
    }
    public static boolean loadGame(Saveable game, String filename, char[] validTurns, char[] validMoves) {
        String fileString = getStringFromFile(filename);
        if(fileString == null){
            return false;
        } else if(!validGameString(fileString, validTurns, validMoves)) {
            return false;
        } else {
            game.loadSavedString(fileString);
            return true;
        }
    }

    public static boolean loadPlayer(Saveable player, String filename) {
        String fileString = getStringFromFile(filename);
        if(fileString == null){
            return false;
        } else if(!validPlayerString(fileString)) {
            return false;
        } else {
            player.loadSavedString(fileString);
            return true;
        }
    }

    private static boolean validPlayerString(String fileString) {
        String[] seperated = fileString.split(",");
        int gamesPlayed;
        int gamesWon;
        
        if(seperated.length != 3) {
            return false;
        }

        try {
            gamesPlayed = Integer.parseInt(seperated[1]);
            gamesWon = Integer.parseInt(seperated[2]);

            if(gamesPlayed < gamesWon){
                return false;
            } else if(gamesPlayed <0 || gamesWon < 0) {
                return false;
            }

        } catch(NumberFormatException e) {
            return false;
        }

        return true;

    }

    
    
    private static boolean validGameString(String fileString, char[] validTurns, char[] validMoves) {
        if(fileString == null) {
            return false;
        }
        
        int numCommas = 0; //represents the number of commas in one row of the string
        int numColumns = 0;

        //create a test sting where empty boxes are represented by spaces for ease of testing
        String testString = fileString.replace(",,", ", ,");
        testString = testString.replace(",\n", ", \n");
        testString = testString.replace("\n,", "\n ,");
        
        //check to make sure first character in string indicating who's turn it is is properly formatted
        if(!charIsValid(fileString.charAt(0), validTurns)) {
            return false;
        } else if(fileString.charAt(1) != '\n') {
            return false;
        }

        //check rest of string to make sure for any possible formatting errors
        for(int i = 2; i < testString.length(); i++) {
            char current = testString.charAt(i);
            if(current == '\n') {
                numCommas = 0;
                numColumns = 0;
                continue;
            } else if(current == ',') {
                numCommas++;
                if(numCommas > 2) {
                    return false;
                }
            } else if(!charIsValid(current, validMoves)){
                return false;
            } else if(i + 1 < testString.length() && testString.charAt(i + 1) != ','
            && testString.charAt(i + 1) != '\n') {
                return false;
            } else {
                numColumns++;
                if(numColumns > 3) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean charIsValid(char turn, char[] validChars) {
        for(char possibleTurn : validChars) {
            if(turn == possibleTurn) {
                return true;
            }
        }
        return false;
    }

    private static String getStringFromFile(String fileName) {
        String fileString = "";
        String tempString = null;
        
        try(BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
            tempString = bReader.readLine();
            while(tempString != null){
                tempString = bReader.readLine();
                fileString += tempString;
                fileString += "\n";
            }


        } catch (IOException e) {
            return null;
        }
        return fileString;
    }
}
