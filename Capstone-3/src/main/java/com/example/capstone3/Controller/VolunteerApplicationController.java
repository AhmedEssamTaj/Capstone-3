package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Model.VolunteerApplication;
import com.example.capstone3.Repository.VolunteerApplicationRepository;
import com.example.capstone3.Service.VolunteerApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/volunteer-application")
@AllArgsConstructor

// Aishtiaq

public class VolunteerApplicationController {

    private final VolunteerApplicationService volunteerApplicationService;
    private final VolunteerApplicationRepository volunteerApplicationRepository;

    @GetMapping("/get-all")
    public ResponseEntity getAll (){
        return ResponseEntity.status(200).body(volunteerApplicationService.getAllVolunteerApplications());
    }
    @PostMapping("/add/{volunteerId}/{eventId}")
    public ResponseEntity addVolunteerApplication(@RequestBody @Valid VolunteerApplication volunteerApplication
    , @PathVariable Integer volunteerId, @PathVariable Integer eventId) {
        volunteerApplicationService.addVolunteerApplication(volunteerApplication, volunteerId, eventId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully added volunteer application"));
    }
    @PutMapping("/update")
    public ResponseEntity updateVolunteerApplication(@RequestBody @Valid VolunteerApplication volunteerApplication) {
        volunteerApplicationService.updateVolunteerApplication(volunteerApplication);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated volunteer application"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVolunteerApplication(@PathVariable Integer id) {
        volunteerApplicationService.deleteVolunteerApplication(id);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted volunteer application"));
    }

    // =========================== ========================= ===================== ====================
    @GetMapping("/get-volunteer/{volunteerId}")
    public ResponseEntity getAllByVolunteer (@PathVariable Integer volunteerId) {
        return ResponseEntity.status(200).body(volunteerApplicationService.getVolunteerApplications(volunteerId));
    }

    // Endpoint to accept volunteer application ---- Ahmed (1) -----
    @PutMapping("/accept/{volunteerApplicationId}")
    public ResponseEntity acceptVolunteerApplication (@PathVariable Integer volunteerApplicationId, @RequestBody @Valid RoleDTO roleDTO) {
        volunteerApplicationService.acceptVolunteerApplication(volunteerApplicationId, roleDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully accepted role application"));
    }

    // Endpoint to reject volunteer application ---- Ahmed (2) -----
    @PutMapping("/reject/{volunteerApplicationId}")
    public ResponseEntity rejectVolunteerApplication (@PathVariable Integer volunteerApplicationId) {
        volunteerApplicationService.rejcetApplication(volunteerApplicationId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully rejected volunteer application"));
    }

    // Endpoint to get all volunteers applications for an event --- Ahmed (8) ---
    @GetMapping("/get-event/{eventId}")
    public ResponseEntity getVolunteerApplicationsByEventId (@PathVariable Integer eventId) {
        return ResponseEntity.status(200).body(volunteerApplicationService.getVolunteerApplicationsByEventId(eventId));
    }

    // Endpoint to count number of times volunteer was accepted in a event
    @GetMapping("/count-approved/{volunteerId}")
    public  ResponseEntity getNumberOfApprovedEventsForVolunteer (@PathVariable Integer volunteerId) {
        return ResponseEntity.status(200).body(new ApiResponse("this volunteer accepted in "+ volunteerApplicationService.getNumberOfApprovedEventsForVolunteer(volunteerId)));
    }



}
