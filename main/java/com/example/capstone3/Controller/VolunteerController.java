package com.example.capstone3.Controller;

import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Service.VolunteerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/volunteer")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService volunteerService;

    @GetMapping("/get")
    public ResponseEntity getAllVolunteer(){

        return ResponseEntity.status(200).body(volunteerService.getAllVolunteer());
    }

    @GetMapping("/get-volunteer-DTO")
    public ResponseEntity getVolunteerDTO(){
       return  ResponseEntity.status(200) .body(volunteerService.getVolunteerDTO());
    }

   @PostMapping("/add")
    public ResponseEntity addVolunteer(@RequestBody @Valid Volunteer volunteer){
        volunteerService.addVolunteer(volunteer);
        return ResponseEntity.status(200).body("Volunteer added successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateVolunteer(@PathVariable Integer id, @RequestBody @Valid Volunteer volunteer){

       volunteerService.updateVolunteer(id, volunteer);
        return ResponseEntity.status(200).body("Volunteer updated successfully");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVolunteer(@PathVariable Integer id){

       volunteerService.deleteVolunteer(id);
        return ResponseEntity.status(200).body("Volunteer deleted successfully");

    }


    // List of Volunteers Who Did Not Apply for Any Events (Aishtiag-7)
    @GetMapping("/without-applications")
    public ResponseEntity<List<Volunteer>> getVolunteersWithoutApplications() {
        List<Volunteer> volunteers = volunteerService.getVolunteersWithoutApplications();
        return ResponseEntity.ok(volunteers);
    }

    //Get a list of trained volunteers only (Aishtiaq-8)
    @GetMapping("/trained")
    public ResponseEntity<List<Volunteer>> getTrainedVolunteers() {
        List<Volunteer> volunteers = volunteerService.getTrainedVolunteers();
        return ResponseEntity.ok(volunteers);
    }

    //Returns a list of volunteers who did not attend an event (inactive volunteers)  (Aishtiaq-10)

    @GetMapping("/inactive")
    public ResponseEntity<List<Volunteer>> getVolunteersWithNoAttendance() {
        List<Volunteer> volunteers = volunteerService.getVolunteersWithNoAttendance();
        return ResponseEntity.ok(volunteers);
    }
}
