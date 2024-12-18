package com.example.capstone3.Controller;

import com.example.capstone3.DTO.EventDTO;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.status(200).body(eventService.getAllEventDTOs());
    }

    @PostMapping("/add")
    public ResponseEntity addEvent(@RequestBody @Valid EventDTO event) {
        eventService.addEvent(event);
        return ResponseEntity.status(200).body("Event added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDTO event) {
        eventService.updateEvent(id, event);
        return ResponseEntity.status(200).body("Event updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.status(200).body("Event deleted");
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {

        return ResponseEntity.status(200).body(eventService.getEventById(id));
    }

    //list of all upcoming (future)  events for this stadium  (Aishtiaq-4)

    @GetMapping("/stadium-upcoming-Events/{stadiumId}")
    public ResponseEntity<List<Event>> getUpcomingEventsForStadium(@PathVariable Integer stadiumId) {
        List<Event> events = eventService.getUpcomingEventsForStadium(stadiumId);
        return ResponseEntity.status(200).body(events);
    }


    // Get a list of Full Events (Aishtiaq-9)
    @GetMapping("/full")
    public ResponseEntity<List<Event>> getFullEvents() {
        List<Event> events = eventService.getFullEvents();
        return ResponseEntity.ok(events);
    }

}
