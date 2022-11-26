# Eyoel's Game Suite

A game suite that provides a text based UI for playing tic tac toe as well as a GUI for that allows the user to choose between playing Numerical tic tac toe and regular tic tac toe.

## Description

For Connect Four's rules see: https://www.gamesver.com/the-rules-of-connect-4-according-to-m-bradley-hasbro/
<br><br>
In this implementation two players play on the same keyboard and are labled player 1 and 2 (player 1 being the player that plays first). The current board can be saved and the game quitted during any player's turn. At the beginning of the game, you are given the choice of loading a saved game from a user-specified file. 

### Dependencies

* JDK 11
* Gradle 7.5.1
* JUnit 4.13



### Executing program

* ```cd``` into the directory within the project folder containing build.gradle in the case of this game it should be in the A2 directory
* run ```gradle clean build```
* if errors are encountered, refer to gradle documentation
* if no errors are encountered run ```java -cp build/classes/java/main connectfour.ConnectFour```
* the first line of out put should be "Welcome to Eyoel's Connect Four game!" 

## Limitations

- You can only save and load data for player one (I guess 
you can consider player 2 a glorified CPU)

## Author Information

Author: Eyoel Matiwos  
Email: ematiwos@ouguelph.ca


