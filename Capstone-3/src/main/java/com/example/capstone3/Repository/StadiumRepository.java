package com.example.capstone3.Repository;

import com.example.capstone3.Model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {

    Stadium findStadiumById(Integer id);
}
