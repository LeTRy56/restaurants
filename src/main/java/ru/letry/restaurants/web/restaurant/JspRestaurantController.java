package ru.letry.restaurants.web.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/restaurants")
public class JspRestaurantController extends AbstractRestaurantController {

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("restaurant", new Restaurant("Restaurant", Set.of(
                new Dish("Dish 1", BigDecimal.TEN),
                new Dish("Dish 2", BigDecimal.TEN),
                new Dish("Dish 3", BigDecimal.TEN))));
        return "restaurantForm";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) {
        Restaurant restaurant = new Restaurant(
                request.getParameter("restaurantName"),
                Collections.emptySet());

        Set<Dish> dishes = new HashSet<>();

        Map<String, String[]> map = request.getParameterMap();

        Pattern pattern = Pattern.compile("dishName\\d*", Pattern.CASE_INSENSITIVE);
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            final String key = entry.getKey();
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                final String dishName = Arrays.stream(entry.getValue()).findFirst().orElse(null);
                final Integer dishId = Integer.parseInt(key.substring(8));
                final BigDecimal dishPrice = new BigDecimal(Objects.requireNonNull(
                        Arrays.stream(map.get("dishPrice" + dishId))
                                .findFirst()
                                .orElse(null)));
                dishes.add(new Dish(dishId, dishName, dishPrice));
            }
        }
        restaurant.setDishes(dishes);

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            super.create(restaurant);
        } else {
            super.update(restaurant, getId(request));
        }
        return "redirect:/restaurants";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable int id, Model model) {
        model.addAttribute("restaurant", super.get(id));
        return "restaurantForm";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpServletRequest request) {
        super.delete(id);
        return "redirect:/restaurants";
    }

    @PostMapping("/{id}/vote")
    public String vote(@PathVariable int id) {
        super.voteRestaurant(id);
        return "redirect:/restaurants";
    }

    @PostMapping("/{restaurantId}/dishes/create")
    public String dishCreate(@PathVariable int restaurantId, Model model) {
        //todo new controller for /dishes?
        super.createDish(new Dish("Dish", BigDecimal.TEN), restaurantId);
        model.addAttribute("restaurant", restaurantService.get(restaurantId));
        return "restaurantForm";
    }

    @GetMapping("/{restaurantId}/dishes/{dishId}/delete")
    public String dishDelete(@PathVariable int restaurantId, @PathVariable int dishId, Model model) {
        super.deleteDish(dishId, restaurantId);
        model.addAttribute("restaurant", restaurantService.get(restaurantId));
        return "restaurantForm";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
