package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.TrainerDTO;
import com.example.capstone3.Model.Trainer;
import com.example.capstone3.Model.Training;
import com.example.capstone3.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;


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
}
