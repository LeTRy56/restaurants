package ru.letry.restaurants.web.restaurant;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.letry.restaurants.dto.RestaurantDTO;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin/restaurants")
public class JspAdminRestaurantController extends AbstractRestaurantController {

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
                final LocalDate date = LocalDate.parse(request.getParameter("date"));
                dishes.add(new Dish(dishId, dishName, dishPrice, date));
            }
        }

        RestaurantDTO restaurant = new RestaurantDTO(
                request.getParameter("restaurantName"),
                dishes);

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            super.create(restaurant);
        } else {
            super.update(restaurant, getId(request));
        }
        return "redirect:/restaurants";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable int id, Model model,
                         @Nullable @RequestParam("date")
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("restaurant", super.get(id, date));
        return "restaurantForm";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, HttpServletRequest request) {
        super.delete(id);
        return "redirect:/restaurants";
    }

    @GetMapping("/{restaurantId}/dishes/create")
    public String dishCreate(@PathVariable int restaurantId, Model model,
                             @Nullable @RequestParam("date")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        super.createDish(new Dish("Dish", BigDecimal.TEN, date), restaurantId);
        model.addAttribute("restaurant", super.get(restaurantId, date));
        return "restaurantForm";
    }

    @GetMapping("/{restaurantId}/dishes/{dishId}/delete")
    public String dishDelete(@PathVariable int restaurantId, @PathVariable int dishId, Model model,
                             @Nullable @RequestParam("date")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        super.deleteDish(dishId, restaurantId);
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("restaurant", super.get(restaurantId, date));
        return "restaurantForm";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
