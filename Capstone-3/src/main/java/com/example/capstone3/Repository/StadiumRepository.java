package com.example.capstone3.Repository;

import com.example.capstone3.Model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

// Bushra

public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
    @Query("SELECT s FROM Stadium s WHERE s.city = :city")
    List<Stadium> findByCity(String city);
    @Query("SELECT s FROM Stadium s WHERE s.capacity >= :capacity")
    List<Stadium> findByCapacityGreaterThanEqual(Integer capacity);
    @Query("SELECT s FROM Stadium s WHERE s.status = :status")
    List<Stadium> findByStatus(String status);
    Stadium findStadiumById(Integer id);
    Stadium findTopByOrderByCapacityDesc();


}
