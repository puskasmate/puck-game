package puckgame.results;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Class representing the result of a game played by a specific player.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class GameResult {

    /**
     * The id of the result in database.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the player.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The color of the puck the player played with.
     */
    @Column(nullable = false)
    private String color;

    /**
     * The steps of the winner player.
     */
    @Column(nullable = false)
    private int steps;

    /**
     * The duration of the game.
     */
    @Column(nullable = false)
    private Duration duration;

    /**
     * The time when the player won the game.
     */
    @Column(nullable = false)
    private ZonedDateTime created;

    /**
     * A method that creates the time when did the player win.
     */
    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }
}
