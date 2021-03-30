package ru.letry.restaurants.repository.datajpa;

import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

public class DataJpaVoteRepository implements VoteRepository {
    @Override
    public Vote save(Vote vote) {
        return null;
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
