package com.example.capstone3.Controller;

import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Service.VolunteerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
