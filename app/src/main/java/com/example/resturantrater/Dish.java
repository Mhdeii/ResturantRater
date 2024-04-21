package com.example.resturantrater;

public class Dish {
    private long dishId;
    private String name;
    private String type;
    private double rating;
    private long restaurantId;

    public Dish(String name, String type, double rating, long restaurantId) {
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.restaurantId = restaurantId;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}

