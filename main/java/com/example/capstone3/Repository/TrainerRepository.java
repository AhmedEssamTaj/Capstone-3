package com.example.capstone3.Repository;

import com.example.capstone3.Model.Trainer;
import com.example.capstone3.Model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Integer> {

    Trainer findTrainerById(Integer id);











}
