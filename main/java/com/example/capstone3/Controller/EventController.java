package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.DTO.EventDTO;
import com.example.capstone3.DTO.EventDTOout;
import com.example.capstone3.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
//bushra
@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/get-all")
    public ResponseEntity getAllEvents() {
        return ResponseEntity.status(200).body(eventService.getAllEvents());
    }

    @GetMapping("/get-from-dates/{from}/{to}")
    public ResponseEntity getEventById(@PathVariable LocalDate from,@PathVariable LocalDate to) {
        return ResponseEntity.status(200).body(eventService.getEventsBetweenDates(from,to));
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getEventById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(eventService.getEventDetailsById(id));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity getEventsByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(eventService.getEventsByStatus(status));
    }

    @GetMapping("/get-by-date/{date}")
    public ResponseEntity getEventsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.status(200).body(eventService.getEventsByDate(date));
    }

    @PostMapping("/add")
    public ResponseEntity addEvent(@RequestBody @Valid EventDTO eventDTO) {
        eventService.addEvent(eventDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Event added successfully"));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity updateEventStatus(@PathVariable Integer id, @RequestBody @Valid String status) {
        eventService.updateEventStatus(id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Event status updated successfully"));
    }

    @PutMapping("/update-date/{id}")
    public ResponseEntity updateEventDate(@PathVariable Integer id, @RequestBody @Valid LocalDate date) {
        eventService.updateEventDate(id, date);
        return ResponseEntity.status(200).body(new ApiResponse("Event date updated successfully"));
    }

    @PutMapping("/update-time/{id}")
    public ResponseEntity updateEventTime(@PathVariable Integer id, @RequestBody @Valid LocalTime startTime, @RequestBody @Valid LocalTime endTime) {
        eventService.updateEventTime(id, startTime, endTime);
        return ResponseEntity.status(200).body(new ApiResponse("Event time updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {
        eventService.deleteEventById(id);
        return ResponseEntity.status(200).body(new ApiResponse("Event deleted successfully"));
    }

    @DeleteMapping("/delete-by-status/{status}")
    public ResponseEntity deleteEventsByStatus(@PathVariable String status) {
        eventService.deleteEventsByStatus(status);
        return ResponseEntity.status(200).body(new ApiResponse("Events with status deleted successfully"));
    }

    @GetMapping("/get-upcoming")
    public ResponseEntity getUpcomingEvents() {
        return ResponseEntity.status(200).body(eventService.getUpcomingEvents());
    }

    @GetMapping("/get-past")
    public ResponseEntity getPastEvents() {
        return ResponseEntity.status(200).body(eventService.getPastEvents());
    }
}
