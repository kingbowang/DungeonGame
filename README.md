## Dungeon game

### 1. About/Overview

After a player enters the dungeon, he will walk along the tunnel from the starting cave to the end cave. During this process, the player can collect treasure and arrows along the way. The player has three arrows at the beginning. The number of caves in the dungeon is determined by row and col, the number of tunnels is determined by interconnectivity, and the number of treasures and arrows is determined by the number of caves and the probability of treasure appearing together. Otyughs will randomly appear in caves in the dungeon. Users can use arrows to attack Otyughs. If the player enters the cave of Otyughs, the player will be eaten by Otyughs. The user can control the player's actions by entering different commands. The M (move) command allows the player to move on the map, the P (pick) command allows the player to pick up the treasure or arrow in the cave, and the S (shoot) command allows the player to shoot arrows at the cave at the specified distance and direction. I designed two views: Text-based and GUI-based. The game runs well in both modes.

### 2. List Of Features

- When using the Kruskal algorithm for dungeon creation, I used a private method, which ensures that the dungeon creation is not visible to the outside.
- The game only needs one controller object from the beginning to the end, so when creating the controller object, I used the singleton mode.
- I overloaded the equals method and hashcode method in the Position class to make the two Position objects with equal row and col equal.
- The program uses asynchronous calls to process user commands.
- When the game is running, users can get game information from the GUI and the text in the console.
- Text and GUI will run together.

### 3. How To Run/How to Use the Program

Click [here](https://www.jetbrains.com/help/idea/compiling-applications.html) to see how to run the JAR file.
At the very beginning, the game will run with the default settings of row = 6, col = 6, wrapping = true, interconnectivity = 3, treasure probability = 0.35, and the number of otyughs = 1. The user can set a new dungeon game through New Game in the dungeon menu (click here). After the setting is completed, the user clicks start, and then also needs to click Restart to start a new game (because the previous game is running, it needs to be refreshed). If the user selects New Game at the end of the game, there is no need to click Restart. The player can move through the arrows on the keyboard or use the mouse to click on the nearby cave. When the player encounters treasures or arrows, click on the treasures or arrows displayed on the left side to get them. When the player wants to shoot, press S (uppercase), shooting distance (number), direction (NSWE) in the keyboard, like S-1-N, or press S and then use the mouse to click on the nearby cave. Restart and Reuse can be clicked at will during the game, but after the game is over, that is, when the player reaches the end or is eaten by the monster, the user should click OK, and then directly click Restart or New game to start a new game. During this period, any other operations will make the program unavailable. Reuse is not available after the game is over, it can only be used while the game is in progress. At last, Exit can work at any time. If the program crashes, click Exit and then run the program again.

### 4. Description of Example

Example 1 -- New Game.png
Demonstrate the successful operation of the setup dungeon.

Example 2 -- Dungeon Game.png
Demonstrate the successful operation of the dungeon game GUI.

Example 3 -- Dungeon Menu.png
Demonstrate the successful operation of the dungeon menu.

### 5. Design/Model Changes

The original version has been modified in detail. The classes are not change, some methods have been added, and some useless parameters have been changed.

### 6. Assumptions

- The treasure collected by the players will be held until the end of the game, and the value of each treasure is different.
- After the game starts, players are allowed to choose not to pick up treasure when they can pick up treasure.
- I regard arrows as a kind of treasure. When the probability of treasure is 20%, it means that treasure and arrow account for 20% of the total caves.

### 7. Limitations

- In a GUI-based game, the command to shoot should be first press s, then press the distance to shoot, and finally, press the direction of the arrow. If the order of command input is wrong, the program may be abnormal.
- Since swing is a multi-threaded interface, the program will occasionally show exceptions thrown by swing, but this does not affect the operation of the game.

### 8. Citations

Wikimedia Foundation. (2021, April 25). Kruskal's algorithm. Wikipedia. Retrieved November 1, 2021, from https://en.wikipedia.org/wiki/Kruskal's_algorithm. 
