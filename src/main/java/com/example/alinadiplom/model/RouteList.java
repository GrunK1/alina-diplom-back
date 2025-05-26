package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
public class RouteList {
    @Id
    Integer mlNumber;
    Date plannedStartDate;
    Date plannedEndDate;
    String responsibleOrganization;
}
