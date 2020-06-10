package com.store.areas.news.models.service;

import com.store.areas.product.models.service.ProductServiceModel;
import com.store.areas.user.models.service.UserServiceModel;

import java.time.LocalDateTime;
import java.util.Set;

public class NewsServiceModel {
    private String id;

    private String name;

    private String img;

    private String description;
    private UserServiceModel user;

    private LocalDateTime createdAt;
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
