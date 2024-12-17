package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Model.VolunteerApplication;
import com.example.capstone3.Service.VolunteerApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/volunteer-application")
@AllArgsConstructor
public class VolunteerApplicationController {

    private final VolunteerApplicationService volunteerApplicationService;

    @GetMapping("/get-all")
    public ResponseEntity getAll (){
        return ResponseEntity.status(200).body(volunteerApplicationService.getAllVolunteerApplications());
    }
    @PostMapping("/add")
    public ResponseEntity addVolunteerApplication(@RequestBody @Valid VolunteerApplication volunteerApplication) {
        volunteerApplicationService.addVolunteerApplication(volunteerApplication);
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

}
