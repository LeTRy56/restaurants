package ru.letry.restaurants.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.model.Restaurant;
import ru.letry.restaurants.web.restaurant.RestaurantRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestaurantServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private RestaurantRestController restaurantController;

    @Override
    public void init() throws ServletException {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        restaurantController = springContext.getBean(RestaurantRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(req);
                restaurantController.delete(id);
                resp.sendRedirect("restaurants");
            }
            case "create", "update" -> {
                final Restaurant restaurant = "create".equals(action) ?
                        new Restaurant("Restaurant", Set.of(
                                new Dish("Dish 1", BigDecimal.TEN),
                                new Dish("Dish 2", BigDecimal.TEN),
                                new Dish("Dish 3", BigDecimal.TEN))) :
                        restaurantController.get(getId(req));
                req.setAttribute("restaurant", restaurant);
                req.getRequestDispatcher("/restaurantForm.jsp").forward(req, resp);
            }
            default -> {
                req.setAttribute("restaurants", restaurantController.getAll());
                req.getRequestDispatcher("/restaurants.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Restaurant restaurant = new Restaurant(
                req.getParameter("restaurantName"),
                Collections.emptySet());

        Set<Dish> dishes = new HashSet<>();

        Map<String, String[]> map = req.getParameterMap();

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


        if (StringUtils.hasLength(req.getParameter("restaurantId"))) {
            restaurantController.update(restaurant, getId(req));
        } else {
            restaurantController.create(restaurant);
        }
        resp.sendRedirect("restaurants");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("restaurantId"));
        return Integer.parseInt(paramId);
    }
}
