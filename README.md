## Puck game

A simple puck game played by 2 players.

### About the game 

The game is played on a 5x5 board with two types of pucks. The first one is the blue puck and the second one is the red puck. 

Both players can only move 1 field to up/down/left/right. 

The player with the blue pucks wins the game if he/she is not able to move. The player with the red pucks wins the game if there are at least 2 blue puck in the same column or row.

### Allowed moves

+ blue: a blue puck can move 1 field to up/down/left/right, if there is a red puck on the chosen field. The red puck will be knocked out of the table.
+ red: a red puck can move 1 field to up/down/left/right, if the chosen field is empty.

### Controls 

The current player have to click and drag the chosen puck to the chosen direction.

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

Building the project requires JDK 11 and [Apache Maven](https://maven.apache.org/).
