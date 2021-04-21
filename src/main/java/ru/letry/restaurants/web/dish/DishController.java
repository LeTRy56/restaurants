package ru.letry.restaurants.web.dish;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.service.DishService;
import ru.letry.restaurants.web.SecurityUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.letry.restaurants.util.ValidationUtil.*;

@Controller
public class DishController {
    private final Logger log = getLogger(getClass());

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    public Dish create(Dish dish, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        checkNew(dish);
        log.info("create dish {} by user {}", dish, userId);
        return service.create(dish, restaurantId, userId);
    }

    public Dish get(int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("get dish {} by user {}", id, userId);
        return service.get(id, restaurantId);
    }

    public List<Dish> getAll(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll by user {}", userId);
        return service.getAll(restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(dish, id);
        log.info("update dish {} by user {}", id, userId);
        service.update(dish, restaurantId, userId);
    }

    public void delete(int id, int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete dish {} by user {}", id, userId);
        service.delete(id, restaurantId, userId);
    }
}
