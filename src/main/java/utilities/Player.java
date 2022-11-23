package utilities;

import boardgame.Saveable;

/**
 * Class representing a player's profile. Responsible for storing
 * and modifying the information of player's profiles
 * @author Eyoel Matiwos
 */
public class Player implements Saveable{
    private String name;
    private int gamesPlayed;
    private int gamesWon;

    public Player() {
        this.name = "Anonymous";
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    public Player(String pName) {
        this.name = pName;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    /* Object returns a string in the format required for 
    a text save file for that object */
    public String getStringToSave(){
        return this.getName() + "," + Integer.toString(this.getGamesPlayed()) + "," 
                + Integer.toString(this.getGamesWon());
    }
    /* Object parses the string given as a parameter and restores
    its state based on the values in the string*/
    public void loadSavedString(String toLoad) {
        String[] seperated = toLoad.split(",");
        this.setName(seperated[0]);
        this.gamesPlayed = Integer.parseInt(seperated[1]);
        this.gamesWon = Integer.parseInt(seperated[2]);
    }

    public void updateWithWin() {
        this.gamesPlayed++;
        this.gamesWon++;
    }

    public void updateWithNonWin() {
        this.gamesPlayed++;
    }

    //Accessors and Mutators
    private String getName() {
        return this.name;
    }

    private void setName(String pName) {
        this.name = pName;
    }

    private int getGamesPlayed() {
        return this.gamesPlayed;
    }

    private int getGamesWon() {
        return this.gamesWon;
    }

    private int getGamesLost() {
        return this.getGamesPlayed() - this.getGamesWon();
    }
}
    

