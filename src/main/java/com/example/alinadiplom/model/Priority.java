package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public enum Priority {
    CRITICAL ("Критический"),
    HIGH ("Высокий"),
    MEDIUM_PLUS ("Средний+"),
    MEDIUM ("Средний"),
    LOW ("Низкий");

    String title;
    Priority(String title){
        this.title = title;
    }
    void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }
    public static Priority getPriority(int days){
        if (days <= 6) return Priority.LOW;
        else if (days <= 14) return Priority.MEDIUM;
        else if (days <= 28) return Priority.MEDIUM_PLUS;
        else if (days <= 45) return Priority.HIGH;
        else return Priority.CRITICAL;
    }
}
