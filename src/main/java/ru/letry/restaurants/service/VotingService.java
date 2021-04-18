package ru.letry.restaurants.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.repository.RestaurantRepository;
import ru.letry.restaurants.repository.VoteRepository;

import javax.annotation.PostConstruct;
import java.time.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.letry.restaurants.util.MapUtil.sortByValue;

@Service
public class VotingService {
    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    //<restaurantId, number of votes>
    private final Map<Integer, Integer> results = new ConcurrentHashMap<>();

    private final static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public VotingService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    private void init() {
        refresh();
/*        //for check refreshAtMidnight() (uncomment getRemainSecondsOneDay()):
        results.put(100003, 4);*/
        refreshAtMidnight();
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

    private void refreshAtMidnight() {
        service.scheduleAtFixedRate(this::refresh, getRemainSecondsOneDay(), 60 * 60 * 24, TimeUnit.SECONDS);
    }

    public long getRemainSecondsOneDay() {
        ZonedDateTime nowZoned = ZonedDateTime.now();
        Instant midnight = nowZoned.toLocalDate().atStartOfDay(nowZoned.getZone()).toInstant();
        Duration duration = Duration.between(midnight, Instant.now());
/*        //for check refreshAtMidnight() (uncomment in init()):
        return 30;*/
        return 60 * 60 * 24 - duration.getSeconds();
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
//            return null;
        }

        Vote lastVote = getLastUserVote(vote.getUser().id(), vote.getDateTime().toLocalDate());
        Vote saved = voteRepository.save(vote);

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
        Assert.notNull(restaurant, "restaurant must not be null");
        results.put(restaurant.id(), 0);
        return restaurant;
    }

    public void deleteRestaurant(int id) {
        results.remove(id);
    }

    public Vote getLastUserVote(int userId, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getLastUserVote(userId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }

    private List<Vote> getByDay(LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return voteRepository.getBetweenHalfOpen(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }

    public Map<Integer, Integer> getResults() {
        return Map.copyOf(results);
    }

    public Map<Integer, Integer> getSortedResults() {
        return sortByValue(results);
    }
}
