package com.example.alinadiplom.DTO;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@XmlRootElement(name = "routeList")
@XmlAccessorType(XmlAccessType.FIELD)  // Важно!
@Data
@RequiredArgsConstructor
public class RouteListDTO {
    private Integer mlNumber;
    private Date plannedStartDate;
    private Date plannedEndDate;
    private Date pollDate;

    @XmlElementWrapper(name = "puSerialNumbers")
    @XmlElement(name = "puSerialNumber")
    private List<String> puSerialNumber;

    private String responsibleOrganization;
}
