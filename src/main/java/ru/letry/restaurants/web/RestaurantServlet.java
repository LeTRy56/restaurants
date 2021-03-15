package ru.letry.restaurants.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.letry.restaurants.web.restaurant.RestaurantRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
        super.doPost(req, resp);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
