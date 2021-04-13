package ru.letry.restaurants.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.letry.restaurants.model.Dish;
import ru.letry.restaurants.web.dish.DishRestController;
import ru.letry.restaurants.web.restaurant.RestaurantController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

public class DishServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private DishRestController dishController;
    private RestaurantController restaurantController;

    @Override
    public void init() throws ServletException {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        dishController = springContext.getBean(DishRestController.class);
        restaurantController = springContext.getBean(RestaurantController.class);
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
                int dishId = getId(req, "dishId");
                int restaurantId = getId(req, "restaurantId");
                dishController.delete(dishId, restaurantId);
                resp.sendRedirect("restaurants");

//                req.setAttribute("action", "Edit restaurant");
//                req.setAttribute("restaurant", restaurantController.get(restaurantId));
//                req.removeAttribute("dishId");
//                req.getRequestDispatcher("/restaurantForm.jsp").forward(req, resp);
            }
            default -> resp.sendRedirect("restaurants");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Dish dish = new Dish("Dish", BigDecimal.TEN);
        if (StringUtils.hasLength(req.getParameter("dishId"))) {
            dishController.update(dish, getId(req, "dishId"), getId(req,"restaurantId"));
        } else {
            dishController.create(dish, getId(req, "restaurantId"));
        }
        req.setAttribute("restaurant", restaurantController.get(getId(req, "restaurantId")));
        req.getRequestDispatcher("/restaurantForm.jsp").forward(req, resp);
    }

    private int getId(HttpServletRequest request, String stringKeyId) {
        String paramId = Objects.requireNonNull(request.getParameter(stringKeyId));
        return Integer.parseInt(paramId);
    }
}
