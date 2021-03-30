package ru.letry.restaurants.repository.jpa;

import org.springframework.transaction.annotation.Transactional;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.repository.VoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

public class JpaVoteRepository implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Vote save(Vote vote) {
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else {
            return em.merge(vote);
        }
    }

    @Override
    public Vote get(int userId) {
        return null;
    }

    @Override
    public List<Vote> getAll(LocalDate date) {
        return null;
    }
}
