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
import ru.letry.restaurants.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.letry.restaurants.util.ValidationUtil.assureIdConsistent;
import static ru.letry.restaurants.util.ValidationUtil.checkNew;
import static ru.letry.restaurants.web.SecurityUtil.authUserId;

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
        log.info("getAll by user {}", authUserId());
        return DTOUtil.getRestaurantDTOs(restaurantService.getAll(), votingService.getResults());
    }

    protected void populateModel (Model model) {
        log.info("getAll by user {}", authUserId());
        model.addAttribute("restaurants", getAll());
        model.addAttribute("user", getUser());
        model.addAttribute("serverTime", LocalDateTime.now());
    }

    protected Restaurant get(int id) {
        log.info("get restaurant {} by user {}", id, authUserId());
        return restaurantService.get(id, LocalDate.now());
    }

    protected Restaurant get(int id, LocalDate date) {
        log.info("get restaurant {} by user {}", id, authUserId());
        return restaurantService.get(id, date);
    }

    protected UserDTO getUser() {
        log.info("get user {}", authUserId());
        return DTOUtil.getUserDTO(
                userService.get(authUserId()),
                votingService.getLastUserVote(authUserId(), LocalDate.now())
        );
    }

    protected Restaurant create(RestaurantDTO restaurant) {
        Restaurant newRestaurant = new Restaurant(restaurant.getName(), restaurant.getDishes());
        checkNew(newRestaurant);
        log.info("create restaurant {} by user {}", restaurant, authUserId());
        return votingService.addRestaurant(restaurantService.create(newRestaurant, authUserId()));
    }

    protected void update(RestaurantDTO restaurant, int restaurantId) {
        Restaurant newRestaurant = new Restaurant(restaurant.getName(), restaurant.getDishes());
        assureIdConsistent(newRestaurant, restaurantId);
        log.info("update restaurant {} by user {}", restaurant, authUserId());
        restaurantService.update(newRestaurant, authUserId());
    }

    protected void delete(int id) {
        log.info("delete restaurant {} by user {}", id, authUserId());
        restaurantService.delete(id, authUserId());
        votingService.deleteRestaurant(id);
    }

    protected Vote voteForRestaurant(int restaurantId) {
        if (LocalTime.now().isAfter(DateTimeUtil.END_VOTING_TIME)) {
            return null;
        }

        log.info("vote for restaurant {} by user {}", restaurantId, authUserId());
        return votingService.vote(new Vote(
                LocalDateTime.now(),
                userService.get(authUserId()),
                restaurantService.get(restaurantId, LocalDate.now())));
    }

    protected Dish createDish(Dish dish, int restaurantId) {
        log.info("create dish for restaurant {} by user {}", restaurantId, authUserId());
        return dishService.create(dish, restaurantId, authUserId());
    }

    protected void updateDish(Dish dish, int restaurantId) {
        log.info("update dish for restaurant {} by user {}", restaurantId, authUserId());
        dishService.update(dish, restaurantId, authUserId());
    }

    protected void deleteDish(int id, int restaurantId) {
        log.info("delete dish {} for restaurant {} by user {}", id, restaurantId, authUserId());
        dishService.delete(id, restaurantId, authUserId());
    }

    protected List<Dish> getAllDishes(int restaurantId, LocalDate date) {
        log.info("getAllDishes for restaurant {} date {} by user {}",restaurantId, date, authUserId());
        return dishService.getAll(restaurantId, date);
    }
}
