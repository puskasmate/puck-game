package puckgame.model;

public class Player {

    private String name;

    private int playerId;

    private int stepCount;

    public Player (String name, int playerId, int stepCount) {
        this.name = name;
        this.playerId = playerId;
        this.stepCount = stepCount;
    }

    public Player() { }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}
