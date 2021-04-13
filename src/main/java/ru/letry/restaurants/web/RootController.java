package ru.letry.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.letry.restaurants.service.RestaurantService;
import ru.letry.restaurants.service.UserService;
import ru.letry.restaurants.service.VotingService;
import ru.letry.restaurants.util.DTOUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class RootController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    private final VotingService votingService;

    private final UserService userService;

    public RootController(RestaurantService restaurantService, VotingService votingService, UserService userService) {
        this.restaurantService = restaurantService;
        this.votingService = votingService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:restaurants";
    }

    @GetMapping("/restaurants")
    public String getRestaurants(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll by user {}", userId);

        model.addAttribute("restaurants",
                DTOUtil.getRestaurantDTOs(restaurantService.getAll(), votingService.getResults()));

        model.addAttribute("user", DTOUtil.getUserDTO(
                userService.get(userId),
                votingService.getLastUserVote(userId, LocalDate.now())
        ));
        return "restaurants";
    }

    @GetMapping("/srs")
    public String getSpecification() {
        return "srs";
    }
}
