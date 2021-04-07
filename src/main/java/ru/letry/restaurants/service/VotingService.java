package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.repository.RestaurantRepository;
import ru.letry.restaurants.repository.VoteRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ru.letry.restaurants.util.MapUtil.*;

@Service
public class VotingService {
    private final VoteRepository repository;

    private final RestaurantRepository restaurantRepository;

    //<restaurantId, number of votes>
    private final Map<Integer, Integer> results = new ConcurrentHashMap<>();

    public VotingService(VoteRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    private void init() {
        refresh();
    }

    private void refresh() {
        List<Vote> votes = getByDay(LocalDate.now());

        //<userId, lastUserVote>
        Map<Integer, Vote> map = new HashMap<>();
        votes.stream()
                .sorted(Comparator.comparing(Vote::getDateTime))
                .forEach(vote -> map.put(vote.getUser().id(), vote));

        results.clear();

        List<Restaurant> restaurants = restaurantRepository.getAll();
        for (Restaurant restaurant : restaurants) {
            results.put(restaurant.id(), 0);
        }

        for (Vote vote : map.values()) {
            plusVote(vote);
        }
    }

    private void minusVote(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        results.computeIfPresent(vote.getRestaurant().id(), (key, val) -> val <= 0 ? 0 : val - 1);
    }

    private void plusVote(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        int restaurantId = vote.getRestaurant().id();
        if (!results.containsKey(restaurantId)) {
            results.put(restaurantId, 0);
        }
        results.computeIfPresent(vote.getRestaurant().id(), (key, val) -> val + 1);
    }

    public Vote vote(Vote vote) {
        Assert.notNull(vote, "vote must not be null");

        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            return null;
        }

        Vote lastVote = getLastUserVote(vote.getUser().id(), vote.getDateTime().toLocalDate());
        Vote saved = repository.save(vote);

        if (saved == null) {
            return null;
        }
        if (lastVote != null) {
            minusVote(lastVote);
        }
        plusVote(saved);
        return saved;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        results.put(restaurant.id(), 0);
        return restaurant;
    }

    public void deleteRestaurant(int id) {
        results.remove(id);
    }

    public Vote getLastUserVote(int userId, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return repository.getLastUserVote(userId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }

    private List<Vote> getByDay(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return repository.getBetweenHalfOpen(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }

    public Map<Integer, Integer> getResults() {
        return Collections.unmodifiableMap(results);
    }

    public Map<Integer, Integer> getSortedResults() {
        return sortByValue(results);
    }
}
