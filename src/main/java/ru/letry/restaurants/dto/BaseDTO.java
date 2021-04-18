package ru.letry.restaurants.dto;

import ru.letry.restaurants.HasId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public abstract class BaseDTO implements HasId {
    protected Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    public BaseDTO() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
