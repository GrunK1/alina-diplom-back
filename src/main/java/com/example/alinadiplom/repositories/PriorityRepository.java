package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated(forRemoval = true, since = "Теперь Enum.")
public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}