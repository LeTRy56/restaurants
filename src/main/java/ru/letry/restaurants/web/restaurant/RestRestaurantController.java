package ru.letry.restaurants.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.letry.restaurants.dto.RestaurantDTO;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.model.Vote;

import java.util.List;

@RestController
@RequestMapping(value = RestRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/rest/restaurants";

    @Override
    @GetMapping
    public List<RestaurantDTO> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @PostMapping("/{id}/vote")
    public Vote voteForRestaurant(@PathVariable int id) {
        return super.voteForRestaurant(id);
    }
}
