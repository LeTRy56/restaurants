package ru.letry.restaurants.repository;

import ru.letry.restaurants.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    // null if not found, when updated
    Vote save(Vote vote);

    // null if not found
    Vote get(int userId);

    List<Vote> getAll(LocalDate date);
}
