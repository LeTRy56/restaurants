package ru.letry.restaurants.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {
    private final CrudVoteRepository crudRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Vote save(Vote vote) {
        if (!vote.isNew()) {
            return null;
        }
        return crudRepository.save(vote);
    }

    @Override
    public Vote getLastUserVote(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return crudRepository.getUserVotesByDay(userId, startDate, endDate).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate) {
        return crudRepository.getBetweenHalfOpen(startDate, endDate);
    }

    //    @Override
//    public List<Vote> getAll() {
//        return crudRepository.getAll();
//    }
}
