package com.example.capstone3.Controller;

import com.example.capstone3.Model.Event;
import com.example.capstone3.Service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(eventService.getAllEventDTOs());
    }

    @PostMapping("/add")
    public ResponseEntity addEvent(@RequestBody @Valid Event event) {
        eventService.addEvent(event);
        return ResponseEntity.ok("Event added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid Event event) {
        eventService.updateEvent(id, event);
        return ResponseEntity.ok("Event updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted");
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
}
