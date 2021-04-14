package ru.letry.restaurants.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.letry.restaurants.model.User;

import static ru.letry.restaurants.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = RestProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestProfileController extends AbstractUserController {

    static final String REST_URL = "/rest/profile";

    @GetMapping
    public User get() {
        return super.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        super.update(user, authUserId());
    }
}
