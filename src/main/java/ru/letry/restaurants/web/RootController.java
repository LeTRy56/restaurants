package ru.letry.restaurants.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.letry.restaurants.model.Role;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:restaurants";
//        return SecurityUtil.get().getUserDTO().getRoles().contains(Role.ADMIN) ?
//                "redirect:/admin/restaurants" :
//                "redirect:restaurants";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/srs")
    public String getSpecification() {
        return "srs";
    }
}
