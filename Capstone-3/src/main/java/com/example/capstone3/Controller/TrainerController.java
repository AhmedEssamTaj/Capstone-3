package com.example.capstone3.Controller;


import com.example.capstone3.Model.Trainer;
import com.example.capstone3.Model.Training;
import com.example.capstone3.Service.TrainerService;
import com.example.capstone3.Service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor

// Aishtiaq

public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping("/get")
    public ResponseEntity getAllTrainer(){
        return ResponseEntity.status(200).body(trainerService.getAllTraining());
    }

    @GetMapping("/get-trainer-dto")
    public ResponseEntity getTrainerDTO(){
        return ResponseEntity.status(200).body(trainerService.getTrainerDTO());
    }

    @PostMapping("/add")
    public ResponseEntity addTrainer(@RequestBody @Valid Trainer trainer){
        trainerService.addTrainer(trainer);
        return ResponseEntity.status(200).body("Trainer added successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity updateTrainer(@PathVariable Integer id, @RequestBody @Valid Trainer trainer){

       trainerService.updateTrainer(id, trainer);
        return ResponseEntity.status(200).body("Trainer updated successfully");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTrainer(@PathVariable Integer id){

        trainerService.deleteTrainer(id);
        return ResponseEntity.status(200).body("Trainer deleted successfully");

    }

    // ahmed (6)
    @PutMapping("/assign-trainer-training/{trainerId}/{trainingId}")
    public ResponseEntity assignTrainerToTraining (@PathVariable Integer trainerId, @PathVariable Integer trainingId){
        trainerService.assignTrainerToTraining(trainerId, trainingId);
        return ResponseEntity.status(200).body("Trainer assigned successfully");
    }

    // ahmed (7)
    @PutMapping("/close-training/{trainerId}/{trainingId}")
    public ResponseEntity closeTraining (@PathVariable Integer trainerId, @PathVariable Integer trainingId){
        trainerService.closeTraining(trainerId, trainingId);
        return ResponseEntity.status(200).body("Trainer closed training successfully");
    }

}
