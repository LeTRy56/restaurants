package ru.letry.restaurants.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.letry.restaurants.model.Role;
import ru.letry.restaurants.service.UserService;

@Controller
public class RootController {

    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return userService.get(SecurityUtil.authUserId()).getRoles().contains(Role.ADMIN) ?
                "redirect:/admin/restaurants" :
                "redirect:restaurants";
    }

//    @PostMapping("/users")
//    public String setUser(HttpServletRequest request) {
//        int userId = Integer.parseInt(request.getParameter("id"));
//        SecurityUtil.setAuthUserId(userId);
//        return "redirect:/admin/restaurants";
//    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/srs")
    public String getSpecification() {
        return "srs";
    }
}
