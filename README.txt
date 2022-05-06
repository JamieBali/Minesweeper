# Minesweeper

Author : Jamie Bali (jb862)
Version : 3
Date : 4th May 2020

Minesweeper is a simple game of logic. Randomly generated boards contain mines. 
Upon a left click, a tile (and all surrounding non-mine tiles) will be revealed
to the player, displaying the number of mines there are neighboring that tile.
Right clicking a tile will flag it as a mine. The users job is to flag all the
mine tiles, and leave the others unflagged, all while never left clicking on a
mine.


## Functionality

### Displaying the Data

The GUI in the project is created using JavaFX. The tiles are displayed as an
array of buttons, each showing a png file. There are only 12 different png files
meaning the image data is minimal.
Upon every click of the tiles, be it mining or flagging a tile, the entire board
gets updated. This is by no means the most efficient way of doing it, but with 
the speed that this is computed, it is not significantly more inefficient than
other solutions.
Upon these updates, the GUI goes through every tile on the board using a nested
for-loop and compares it to its respective tile on the board held within the 
game class*, updating the tile if the image is incorrect.
Eg. A tile currently blank that has now been flagged will get updated to display
    a flag; or
    A tile previously in its default untouched state that has now been revealed
    by clicking a nearby black tile will now display the correct number for the
    amount of neighbors it has.
By this means, the GUI doesn't know the state of the board, and therefore there
is no chance of accidentally having all the bomb locations displayed when they 
shouldn't be.


### Editing the Data

This seperation between the GUI and the actual game class means that each button
on the GUI doesn't directly impact the gamestate, but rather implicitly affects
the state of the board.
Each button on the board has metadata** associated with it which gets parsed 
into 2 integer values representing the X and Y values of the tile that has been
pressed. This data, along with the information as to which mouse button was
pressed is then passed to the game class, where the processing actually happens.

### Extra Functionality

There is functionality in the game to allow the user to repeat the same board if
they want, start a new game part way through a previous one, and change the 
difficulty of the game. There are 4 preset difficulties for the game which are:
Easy : a 15x10 board with 20 mines (difficulty rating*** of 7.5)
Medium : a 20x15 board with 55 mines (difficulty rating of 5.5)
Hard : a 30x16 board with 99 mines (difficulty rating of 4.8)
Expert : a 40x20 board with 220 mines (difficulty rating of 3.6)





## Footnotes

* The game class is called version2 as it was refactored from a previous version
  of the game. This version including the GUI is listed as version3 in the 
  documentation
** the metadata is stored in the local storage for each button within JavaFX 
   called UserData, as this was the best way to store it. There are probably
   more efficient ways, but since i already had a parser built this seemed best
   situationally 
*** difficulty rating is a minesweeper rating system for determining how much
    skill is required to complete a board. It's assumed that a score of 10 or
    more can be solved by randomly clicking, and a score lower than 3 is
    practically impossible to solve without guesswork and luck.