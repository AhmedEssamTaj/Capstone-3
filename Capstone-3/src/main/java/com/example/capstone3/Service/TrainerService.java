package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.TrainerDTO;
import com.example.capstone3.Model.Trainer;
import com.example.capstone3.Model.Training;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Repository.TrainerRepository;
import com.example.capstone3.Repository.TrainingRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

// Aishtiaq

public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final VolunteerRepository volunteerRepository;


    public List<TrainerDTO> getTrainerDTO(){
        List<Trainer>trainers=trainerRepository.findAll();
        List<TrainerDTO>trainerDTOS=new ArrayList<>();

        for (Trainer t:trainers){

            TrainerDTO trainerDTO=new TrainerDTO(t.getName(),t.getRole(),t.getExperienceYears());

           trainerDTOS.add(trainerDTO);

        }
        return trainerDTOS;
    }


    public List<Trainer> getAllTraining(){
      return   trainerRepository.findAll();
    }

    public void  addTrainer(Trainer trainer){
        trainerRepository.save(trainer);

    }


    public void  updateTrainer(Integer id, Trainer trainer){

        Trainer trainer1=trainerRepository.findTrainerById(id);
        if(trainer1==null){
            throw new ApiException("Trainer not found");
        }

        trainer1.setName(trainer.getName());
        trainer1.setEmail(trainer.getEmail());
        trainer1.setPhoneNumber(trainer.getPhoneNumber());
        trainer1.setRole(trainer.getRole());
        trainer1.setExperienceYears(trainer.getExperienceYears());
        trainerRepository.save(trainer1);
    }

    public void deleteTrainer(Integer id){

        Trainer trainer=trainerRepository.findTrainerById(id);
        if(trainer==null){
            throw new ApiException("Trainer not found");
        }

        trainerRepository.delete(trainer);
    }


    public void assignTrainerToTraining(Integer trainerId, Integer trainingId){
        Trainer trainer=trainerRepository.findTrainerById(trainerId);
        Training training = trainingRepository.findTrainingById(trainingId);
        if(trainer==null || training==null){
            throw new ApiException("Training or trainer not found");
        }

//        Set<Training> trainerTrainings = trainer.getTrainings();
//        if(trainerTrainings!=null){
//        for (Training t : trainerTrainings) {
//            if (t.getStartDate().equals(training.getStartDate())) {
//                throw new ApiException("Trainer already assigned to a training session on this date");
//            }
//        }
//        }
        training.setTrainer(trainer);
        trainingRepository.save(training);
    }

    public void closeTraining (Integer trainerId, Integer trainingId){
        Trainer trainer=trainerRepository.findTrainerById(trainerId);
        Training training=trainingRepository.findTrainingById(trainingId);
        if(trainer==null || training==null){
            throw new ApiException("Training or trainer not found");
        }

        if(!(training.getTrainer().equals(trainer))){
            throw new ApiException("this training session is not for the same trainer");
        }
        training.setCompleted(true);
         // something is wrong here
        Set<Volunteer> volunteers = training.getVolunteers(); // this line

        System.out.println(volunteers);
        for (Volunteer v:volunteers){
            v.setTrained(true);
            volunteerRepository.save(v);
        }
        trainingRepository.save(training);

    }

}
