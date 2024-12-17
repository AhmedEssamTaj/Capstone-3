package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.Model.VolunteerApplication;
import com.example.capstone3.Model.VolunteerSkills;
import com.example.capstone3.Service.VolunteerSkillsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/volunteer-skills")
@AllArgsConstructor
public class VolunteerSkillsController {

    private final VolunteerSkillsService volunteerSkillsService;

    @GetMapping("/get-all")
    public ResponseEntity getAll (){
        return ResponseEntity.status(200).body(volunteerSkillsService.getAllVolunteerSkills());
    }
    @PostMapping("/add")
    public ResponseEntity addVolunteerSkill(@RequestBody @Valid VolunteerSkills volunteerSkills) {
        volunteerSkillsService.addVolunteerSkills(volunteerSkills);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully added volunteer skill"));
    }
    @PutMapping("/update")
    public ResponseEntity updateVolunteerSkill(@RequestBody @Valid VolunteerSkills volunteerSkills) {
        volunteerSkillsService.updateVolunteerSkills(volunteerSkills);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated volunteer skills"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVolunteerSkills(@PathVariable Integer id) {
        volunteerSkillsService.deleteVolunteerSkills(id);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted volunteer skills"));
    }

    // =========================== ========================= ===================== ====================
    @GetMapping("/get-volunteer/{volunteerId}")
    public ResponseEntity getAllSkillsByVolunteer (@PathVariable Integer volunteerId) {
        return ResponseEntity.status(200).body(volunteerSkillsService.getVolunteerSkillsByVolunteerId(volunteerId));
    }


}
