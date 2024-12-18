package com.example.capstone3.Controller;


import com.example.capstone3.Model.Training;
import com.example.capstone3.Service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor

// Aishtiaq

public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/get")
    public ResponseEntity getAllTraining(){

        return ResponseEntity.status(200).body(trainingService.getAllTraining());
    }

    @GetMapping("/get-training-dto")
    public ResponseEntity getTrainingDTO(){
        return ResponseEntity.status(200).body(trainingService.getTrainingDTO());

    }

    //  get Trainer's Most Recent Upcoming Event (Aishtiaq-6)
    @GetMapping("/{trainerId}/upcoming-event")
    public ResponseEntity<Training> getUpcomingEvent(@PathVariable Integer trainerId) {
        Training event = trainingService.getMostRecentUpcomingEvent(trainerId);
        return ResponseEntity.ok(event);
    }

    @PostMapping("/add")
    public ResponseEntity addVolunteer(@RequestBody @Valid Training training){
        trainingService.addTraining(training);
        return ResponseEntity.status(200).body("Training added successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateTraining(@PathVariable Integer id, @RequestBody @Valid Training training){

        trainingService.updateTraining(id, training);
        return ResponseEntity.status(200).body("Training updated successfully");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTraining(@PathVariable Integer id){

       trainingService.deleteVolunteer(id);
        return ResponseEntity.status(200).body("Training deleted successfully");

    }
}
