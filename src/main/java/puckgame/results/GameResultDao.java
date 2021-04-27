package puckgame.results;

import util.jpa.GenericJpaDao;

import javax.transaction.Transactional;
import java.util.List;

public class GameResultDao extends GenericJpaDao<GameResult> {

    public GameResultDao() {
        super(GameResult.class);
    }

    @Transactional
    public List<GameResult> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r order by r.steps asc, r.duration asc", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }
}
