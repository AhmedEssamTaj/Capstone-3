package com.example.capstone3.Controller;

import com.example.capstone3.ApiResponse.ApiResponse;
import com.example.capstone3.Model.VolunteerRating;
import com.example.capstone3.Service.VolunteerRatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/volunteer-rating")
@AllArgsConstructor
public class VolunteerRatingController {

    private final VolunteerRatingService volunteerRatingService;

    @GetMapping("/get-all")
    public ResponseEntity getAll (){
        return ResponseEntity.status(200).body(volunteerRatingService.getAllVolunteerRating());
    }
    @PostMapping("/add")
    public ResponseEntity addVolunteerRating(@RequestBody @Valid VolunteerRating volunteerRating) {
        volunteerRatingService.addVolunteerRating(volunteerRating);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully added volunteer rating"));
    }
    @PutMapping("/update")
    public ResponseEntity updateVolunteerRating(@RequestBody @Valid VolunteerRating volunteerRating) {
        volunteerRatingService.updateVolunteerRating(volunteerRating);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated volunteer rating"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVolunteerRating(@PathVariable Integer id) {
        volunteerRatingService.deleteVolunteerRating(id);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted volunteer rating"));
    }

    // =========================== ========================= ===================== ====================
    @GetMapping("/get-volunteer/{volunteerId}")
    public ResponseEntity getAllRatingByVolunteer (@PathVariable Integer volunteerId) {
        return ResponseEntity.status(200).body(volunteerRatingService.getAllVolunteerRatingByVolunteerId(volunteerId));
    }

    //(Aishtiaq-1)
    @GetMapping("/get-Average-Ratings/{volunteerId}")
    public ResponseEntity getTheAverageRatingForAVolunteer(@PathVariable Integer volunteerId){
        volunteerRatingService.getTheAverageRatingForAVolunteer(volunteerId);
        return  ResponseEntity.status(200).body(new ApiResponse("Volunteer's average ratings fetched successfully"));
    }

    //(Aishtiaq-5)
    @GetMapping("/get-Average-Ratings-Higher/{minAverageRating}")
    public ResponseEntity getVolunteersWithAverageRatingHigherThan(@PathVariable double minAverageRating){
        volunteerRatingService.getVolunteersWithAverageRatingHigherThan(minAverageRating);
        return ResponseEntity.status(200).body(new ApiResponse("All volunteers who received an above average rating were recruited") );
    }


}
