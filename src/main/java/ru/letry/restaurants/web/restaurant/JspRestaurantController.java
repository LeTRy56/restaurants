package ru.letry.restaurants.web.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letry.restaurants.model.Role;
import ru.letry.restaurants.web.SecurityUtil;

@Controller
@RequestMapping("/restaurants")
public class JspRestaurantController extends AbstractRestaurantController {

    @GetMapping
    public String getRestaurants(Model model) {
        super.populateModel(model);
        return SecurityUtil.get().getUserDTO().getRoles().contains(Role.ADMIN) ?
                "restaurantsAdmin" :
                "restaurants";
    }

    @GetMapping("/{id}/vote")
    public String vote(@PathVariable int id) {
        super.voteForRestaurant(id);
        return "redirect:/restaurants";
    }
}
