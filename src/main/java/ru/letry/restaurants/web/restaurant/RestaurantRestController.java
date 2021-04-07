package ru.letry.restaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.letry.restaurants.dto.RestaurantDTO;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Vote;
import ru.letry.restaurants.service.RestaurantService;
import ru.letry.restaurants.service.UserService;
import ru.letry.restaurants.service.VotingService;
import ru.letry.restaurants.util.DTOUtil;
import ru.letry.restaurants.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.letry.restaurants.util.ValidationUtil.*;

@Controller
public class RestaurantRestController {
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    private final RestaurantService restaurantService;
    private final VotingService votingService;
    private final UserService userService;

    public RestaurantRestController(RestaurantService restaurantService, VotingService votingService, UserService userService) {
        this.restaurantService = restaurantService;
        this.votingService = votingService;
        this.userService = userService;
    }

    public Restaurant create(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        checkNew(restaurant);
        LOG.info("create restaurant {} by user {}", restaurant, userId);
        return votingService.addRestaurant(restaurantService.create(restaurant, userId));
    }

    public Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        LOG.info("get restaurant {} by user {}", id, userId);
        return restaurantService.get(id);
    }

    public List<RestaurantDTO> getAll() {
        int userId = SecurityUtil.authUserId();
        LOG.info("getAll by user {}", userId);
        return DTOUtil.getRestaurantDTOs(restaurantService.getAll(), votingService.getResults());
    }

    public void update(Restaurant restaurant, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(restaurant, restaurantId);
        LOG.info("update restaurant {} by user {}", restaurant, userId);
        restaurantService.update(restaurant, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        LOG.info("delete restaurant {} by user {}", id, userId);
        restaurantService.delete(id, userId);
        votingService.deleteRestaurant(id);
    }

    public Vote vote(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        LOG.info("vote for restaurant {} by user {}", restaurantId, userId);
        return votingService.vote(new Vote(
                LocalDateTime.now(),
                userService.get(userId),
                restaurantService.get(restaurantId)));
    }

    public Vote getLastVote(int userId) {
        return votingService.getLastUserVote(userId, LocalDate.now());
    }
}
