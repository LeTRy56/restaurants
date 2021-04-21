package ru.letry.restaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.letry.restaurants.dto.RestaurantDTO;
import ru.letry.restaurants.dto.UserDTO;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.service.DishService;
import ru.letry.restaurants.service.RestaurantService;
import ru.letry.restaurants.service.UserService;
import ru.letry.restaurants.service.VotingService;
import ru.letry.restaurants.util.DTOUtil;
import ru.letry.restaurants.web.SecurityUtil;

import java.time.LocalDate;
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

    protected List<RestaurantDTO> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll by user {}", userId);
        return DTOUtil.getRestaurantDTOs(restaurantService.getAll(), votingService.getResults());
    }

    protected void populateModel (Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll by user {}", userId);
        model.addAttribute("restaurants", getAll());
        model.addAttribute("user", getUser());
    }

    protected Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get restaurant {} by user {}", id, userId);
        return restaurantService.get(id);
    }

    protected UserDTO getUser() {
        int userId = SecurityUtil.authUserId();
        log.info("get user {}", userId);
        return DTOUtil.getUserDTO(
                userService.get(userId),
                votingService.getLastUserVote(userId, LocalDate.now())
        );
    }

    protected Restaurant create(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        checkNew(restaurant);
        log.info("create restaurant {} by user {}", restaurant, userId);
        return votingService.addRestaurant(restaurantService.create(restaurant, userId));
    }

    protected void update(Restaurant restaurant, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(restaurant, restaurantId);
        log.info("update restaurant {} by user {}", restaurant, userId);
        restaurantService.update(restaurant, userId);
    }

    protected void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete restaurant {} by user {}", id, userId);
        restaurantService.delete(id, userId);
        votingService.deleteRestaurant(id);
    }

    protected Vote voteForRestaurant(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("vote for restaurant {} by user {}", restaurantId, userId);
        return votingService.vote(new Vote(
                LocalDateTime.now(),
                userService.get(userId),
                restaurantService.get(restaurantId)));
    }

    protected Dish createDish(Dish dish, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("create dish for restaurant {} by user {}", restaurantId, userId);
        return dishService.create(dish, restaurantId, userId);
    }

    protected void deleteDish(int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete dish {} for restaurant {} by user {}", id, restaurantId, userId);
        dishService.delete(id, restaurantId, userId);
    }
}
