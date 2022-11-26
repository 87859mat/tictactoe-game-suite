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
    public static void save(Saveable toSave, String fileName) throws IOException{
        try(FileWriter fWriter = new FileWriter(fileName)) {
            fWriter.write(toSave.getStringToSave());
        } catch(IOException e) {
            throw new IOException("ERROR: IOException was thrown by FileWriter.write() when trying to save");
        }
    }
    public static void loadGame(Saveable game, String fname, char[] validTurns, char[] validMoves) throws IOException{
        String fileString = getStringFromFile(fname);
        if(fileString == null){
            throw new IOException("ERROR: fileString was null\n");
        } else if(!validGameString(fileString, validTurns, validMoves)) {
            throw new IOException("ERROR: chosen file was not a valid game save file");
        } else {
            game.loadSavedString(fileString);
        }
    }

    public static void loadPlayer(Saveable player, String filename) throws IOException{
        String fileString = getStringFromFile(filename);
        if(fileString == null || fileString.length() < 4){ //smallest possible player file is 4 chars long (name = "")
            throw new IOException("ERROR: fileString was null");
        } else if(!validPlayerString(fileString)) {
            throw new IOException("ERROR: file was not a valid player file");
        } else {
            player.loadSavedString(fileString);
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
            } else if(gamesPlayed < 0 || gamesWon < 0) {
                return false;
            }

        } catch(NumberFormatException e) {
            return false;
        }

        return true;

    }

    
    
    private static boolean validGameString(String fileString, char[] validTurns, char[] validMoves) {
        if(fileString == null || fileString.length() < 10) { //empty grid will result in file with 10 characters
            return false;
        }

        String testString = fileFormatToGridFormat(fileString);
        
        //check to make sure first character in string indicating who's turn it is is properly formatted
        if(!charIsValid(fileString.charAt(0), validTurns)) {
            return false;
        } else if(fileString.charAt(1) != '\n') {
            return false;
        } else if(!gridIsProperlyFormatted(testString, validMoves)) {
            return false;
        }

        return true;

        
    }

    private static boolean gridIsProperlyFormatted(String testString, char[] validMoves) {
        int numCommas = 0; //represents the number of commas in one row of the string
        int numColumns = 0;

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
            } else if(!charIsValid(current, validMoves) && current != ' '){
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
                fileString += tempString;
                fileString += "\n";
                tempString = bReader.readLine();
            }
            fileString = fileString.substring(0, fileString.length() - 1);


        } catch (IOException e) {
            return null;
        }
        return fileString;
    }

    public static String fileFormatToGridFormat(String fileString) {
        //create a test sting where empty boxes are represented by spaces for ease of testing
        String testString = fileString.replace(",,", ", ,");
        testString = testString.replace(",\n", ", \n");
        testString = testString.replace("\n,", "\n ,");
        
        //in case the bottom right box is empty as the above replace() won't be able to insert a space to represent it
        if(testString.charAt(testString.length() - 1) == ',') { 
            testString += " "; 
        }
        
        return testString;
    }
}
