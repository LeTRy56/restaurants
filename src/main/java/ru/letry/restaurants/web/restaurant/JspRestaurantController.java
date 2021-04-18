package ru.letry.restaurants.web.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurants")
public class JspRestaurantController extends AbstractRestaurantController {

    @GetMapping
    public String getRestaurants(Model model) {
        model.addAttribute("restaurants", super.getAll());
        model.addAttribute("user", super.getUser());
        return "restaurants";
    }

    @GetMapping("/{id}/vote")
    public String vote(@PathVariable int id) {
        super.voteRestaurant(id);
        return "redirect:/restaurants";
    }


}
