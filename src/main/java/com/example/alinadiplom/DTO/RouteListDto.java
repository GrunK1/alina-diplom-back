package com.example.alinadiplom.DTO;

import java.util.Date;

public record RouteListDto(
        Integer mlNumber,
        Date plannedStartDate,
        Date plannedEndDate,
        Date pollDate,
        String puSerialNumber,
        String priority
) {}

