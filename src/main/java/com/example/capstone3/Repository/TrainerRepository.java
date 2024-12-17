package com.example.capstone3.Repository;

import com.example.capstone3.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Integer> {

    Trainer findTrainerById(Integer id);
}
