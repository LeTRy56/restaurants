package ru.letry.restaurants.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.letry.restaurants.AuthorizedUser;

import java.util.Objects;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        return Objects.requireNonNull(safeGet(), "No authorized user found");
    }

    public static int authUserId() {
        return get().getUserDTO().id();
    }
}
