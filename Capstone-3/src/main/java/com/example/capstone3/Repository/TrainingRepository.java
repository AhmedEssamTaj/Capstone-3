package com.example.capstone3.Repository;

import com.example.capstone3.Model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Integer> {

    Training findTrainingById(Integer id);

    Training findFirstByCompletedFalseAndEnrolledVolunteersLessThan(Integer capacity);

}
