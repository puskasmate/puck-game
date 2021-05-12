## Puck game

A simple puck game played by 2 players.

### About the game 

The game is played on a 5x5 board with two types of pucks. The first one is the blue, and the other one is the red. 

Both players can only move 1 field to up/down/left/right. 

The player with the blue pucks wins the game if he/she is not able to move. The player with the red pucks wins the game if there are at least 2 blue pucks in the same column or row.

### Allowed moves

+ blue: a blue puck can move 1 field to up/down/left/right, if there is a red puck on the chosen field. The red puck will be knocked out of the table.
+ red: a red puck can move 1 field to up/down/left/right, if the chosen field is empty.

### Controls 

The current player has to click and drag the chosen puck to the chosen direction.

### Run the project

```bash
mvn clean compile exec:java
```

### Generate docs

```bash
mvn site
```

### Add Clover coverage report

```bash
mvn -P clover site
```

### Requirements

Building the project requires JDK 11 or later and [Apache Maven](https://maven.apache.org/).

**NOTE**

The latest version of JDK at the time when the project was created is 16. It is possible that you have to update the version of the dependencies and/or plugins to work with JDKs above 16.
