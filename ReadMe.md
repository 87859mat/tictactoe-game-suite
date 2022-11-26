# Eyoel's Game Suite

A game suite that provides a text based UI for playing tic tac toe as well as a GUI for that allows the user to choose between playing Numerical tic tac toe and regular tic tac toe.

## Description

### Game Rules
For the tic tac toe game, the rules are identical to what was described in A1. For the numerical tic tac toe game the rules found at https://mathequalslove.net/numerical-tic-tac-toe/ were used.

### How To Play (Text UI)
The textual UI version of this program allows the user to select boxes by entering a number from 1 to 9 which corresponds to the the numbering of the boxes on the tic tac toe grid when you start counting at the top left box and go from left to right (so the top left box is 1, the middle left box is 4, and the bottom right box is 9). The user can choose to save, load, or quit during any player's turn by entering "s", "l", or "q" respectively. The text UI offers no mechansim for save or loading a player's data. 

### How To Play (Game Suite GUI)
The GUI version of the game suite starts at a "start screen" where the user is present with two buttons that allow them to begin either a numerical or regular tic tac toe game upon click. In the tic tac toe game, the user makes a move by clicking one of the boxes on the grid, after which, their move will be processed and if it is valid the button the clicked will display the character their corresponding character (X or O). In the numerical tic tac toe game, the user also has to click of the buttons on the grid to make a move. This will cause a pop window to appear which prompts the player to enter a number and displays what options they have for moves. If the player wants to quit at any point they can click the button at the top of the screen labeled "quit". When the game is over, all of the buttons on the grid are disabled and the player can click the "return to start screen" button at the top of the screen to return to the start screen.

### Player and Game Saving/Loading
The player can save or load a game at any point during a game by clicking the "game save/load" menu and selecting the corresponding menu item. Similarily, when on the start screen, player data can be saved and loaded by clicking the "player save/load" menu and selecting the corresponding menu item.

### Player Data File Format
Player data files are composed of a single line with the following values seperated by commas: The player's name, the number games they've played, the number of games they've won, and the number of games they've lost (in that order)

### Dependencies

* JDK 11
* Gradle 7.5.1

### Executing program

* ```cd``` into the directory within the project folder containing build.gradle in the case of this game it should be in a directory called "A3"
* run ```gradle clean build```
* if errors are encountered, refer to gradle documentation
* if no errors are encountered, run ```java -cp build/classes/java/main ticactactoe.TextUI``` to run the textual version of this game
    -  the first line of out put should be "\*\*\*Tic tac toe game\*\*\*"
* to start the Game Suite GUI, run ```java -jar build/libs/A3.jar```

## Limitations

- You can only save and load data for player one (I guess 
you can consider player 2 a glorified CPU)
- Players are all named "Anonymous"
- Players and games can only be saved/loaded to files in this project's working directory (Which should be called "A3")

## Author Information

Author: Eyoel Matiwos  
Email: ematiwos@ouguelph.ca


