package com.example.alinadiplom.controllers;

import com.example.alinadiplom.DTO.RouteListDto;
import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.model.PollRegistryToRouteList;
import com.example.alinadiplom.model.RouteList;
import com.example.alinadiplom.repositories.PollRegistryRepository;
import com.example.alinadiplom.repositories.PollRegistryToRouteListRepository;
import com.example.alinadiplom.repositories.RouteListRepository;
import com.example.alinadiplom.services.RouteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/route-lists")
public class RouteListController {

    @Autowired
    private RouteListService service;
    @Autowired
    private RouteListRepository repository;
    @Autowired
    private PollRegistryToRouteListRepository prToRlRepository;

    @PostMapping
    public ResponseEntity<RouteList> create(@RequestBody RouteList routeList) {
        return new ResponseEntity<>(service.create(routeList), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RouteList>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteList> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteList> update(@PathVariable Integer id, @RequestBody RouteList routeList) {
        return ResponseEntity.ok(service.update(id, routeList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/enriched")
    public List<RouteListDto> getEnrichedRouteLists() {
        List<RouteList> routeLists = repository.findAll();

        return routeLists.stream()
                .map(routeList -> {
                    Optional<PollRegistryToRouteList> link = prToRlRepository
                            .findTopByRouteListNumberOrderByDateOfSendRouteListDesc(routeList);

                    if (link.isEmpty()) return null;

                    PollRegistry poll = link.get().getPrId();

                    long days = Duration.between(
                            poll.getPollDate().toInstant(),
                            Instant.now()
                    ).toDays();

                    String priority;
                    if (days <= 6) priority = "Низкий";
                    else if (days <= 14) priority = "Средний";
                    else if (days <= 28) priority = "Средний+";
                    else if (days <= 45) priority = "Высокий";
                    else priority = "Критический";

                    return new RouteListDto(
                            routeList.getMlNumber(),
                            routeList.getPlannedStartDate(),
                            routeList.getPlannedEndDate(),
                            poll.getPollDate(),
                            poll.getPuSerialNumber().getPuSerialNumber(),
                            priority
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }
}