package com.example.l8411.wut2eat29.Model;

public class Restaurant {
    private String name;
    private String vicinity;

    public Restaurant(String name, String vicinity) {
        this.name = name;
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
