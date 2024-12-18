package com.example.capstone3.Controller;

import com.example.capstone3.DTO.EventDTO;
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
}
