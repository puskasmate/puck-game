package puckgame.results;

import util.jpa.GenericJpaDao;

import javax.transaction.Transactional;
import java.util.List;

/**
 * DAO class for the {@link GameResult} entity.
 */
public class GameResultDao extends GenericJpaDao<GameResult> {

    public GameResultDao() {
        super(GameResult.class);
    }

    /**
     * Returns the list of {@code n} best results with respect to the number of steps
     * and the time spent for playing the game.
     *
     * @param n the maximum number of results to be returned
     * @return the list of {@code n} best results with respect to the number of steps
     * and the time spent for playing the game
     */
    @Transactional
    public List<GameResult> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r order by r.steps asc, r.duration asc", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }
}
