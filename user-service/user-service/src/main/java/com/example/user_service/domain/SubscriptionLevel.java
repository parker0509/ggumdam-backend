package com.example.user_service.domain;

public enum SubscriptionLevel {
    LEVEL_1("Level 1", "Basic subscription"),
    LEVEL_2("Level 2", "Standard subscription"),
    LEVEL_3("Level 3", "Premium subscription");

    private final String name;
    private final String description;

    SubscriptionLevel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
