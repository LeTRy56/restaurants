package ru.letry.restaurants.repository;

import ru.letry.restaurants.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {
    // null if not found, when updated
    Vote save(Vote vote);

    // null if not found
    Vote getLastUserVote(int userId, LocalDateTime startDate, LocalDateTime endDate);

    // ORDERED dateTime desc
    List<Vote> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate);
}
