package ru.letry.restaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.letry.restaurants.dto.RestaurantDTO;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.service.DishService;
import ru.letry.restaurants.service.RestaurantService;
import ru.letry.restaurants.service.UserService;
import ru.letry.restaurants.service.VotingService;
import ru.letry.restaurants.util.DTOUtil;
import ru.letry.restaurants.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.letry.restaurants.util.ValidationUtil.assureIdConsistent;
import static ru.letry.restaurants.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected DishService dishService;

    @Autowired
    protected VotingService votingService;

    @Autowired
    protected UserService userService;

    public Restaurant create(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        checkNew(restaurant);
        log.info("create restaurant {} by user {}", restaurant, userId);
        return votingService.addRestaurant(restaurantService.create(restaurant, userId));
    }

    public Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get restaurant {} by user {}", id, userId);
        return restaurantService.get(id);
    }

    public List<RestaurantDTO> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll by user {}", userId);
        return DTOUtil.getRestaurantDTOs(restaurantService.getAll(), votingService.getResults());
    }

    public void update(Restaurant restaurant, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(restaurant, restaurantId);
        log.info("update restaurant {} by user {}", restaurant, userId);
        restaurantService.update(restaurant, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete restaurant {} by user {}", id, userId);
        restaurantService.delete(id, userId);
        votingService.deleteRestaurant(id);
    }

    public Vote voteRestaurant(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("vote for restaurant {} by user {}", restaurantId, userId);
        return votingService.vote(new Vote(
                LocalDateTime.now(),
                userService.get(userId),
                restaurantService.get(restaurantId)));
    }

    public void deleteDish(int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete dish {} for restaurant {} by user {}", id, restaurantId, userId);
        dishService.delete(id, restaurantId, userId);
    }

    public Dish createDish(Dish dish, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("create dish for restaurant {} by user {}", restaurantId, userId);
        return dishService.create(dish, restaurantId, userId);
    }

//    public Vote getLastVote(int userId) {
//        return votingService.getLastUserVote(userId, LocalDate.now());
//    }
}
