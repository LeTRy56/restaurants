package ru.letry.restaurants.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {

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

    @GetMapping("/srs")
    public String getSpecification() {
        return "srs";
    }
}
