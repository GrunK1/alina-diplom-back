package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Priority {
    @Id
    @GeneratedValue
    Integer priorityId;
    String priorityType;
}
