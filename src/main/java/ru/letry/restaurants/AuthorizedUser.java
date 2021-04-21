package ru.letry.restaurants;

import ru.letry.restaurants.dto.UserDTO;
import ru.letry.restaurants.model.User;
import ru.letry.restaurants.util.DTOUtil;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserDTO userDTO;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        //todo remove null in params:
        this.userDTO = DTOUtil.getUserDTO(user, null);
    }


    public int getId() {
        return userDTO.getId();
    }

    public void update(UserDTO newDTO) {
        userDTO = newDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    @Override
    public String toString() {
        return userDTO.toString();
    }
}
