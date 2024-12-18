package com.example.capstone3.Repository;

import com.example.capstone3.Model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Integer> {

    Training findTrainingById(Integer id);

    //  get Trainer's Most Recent Upcoming Event (Aishtiaq-6)    @Query("SELECT t FROM Training t WHERE t.trainer.id = ?1 AND t.startDate > CURRENT_DATE ORDER BY t.startDate ASC")
    List<Training> findUpcomingEventsByTrainerId(Integer trainerId);
}
