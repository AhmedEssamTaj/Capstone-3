package com.example.capstone3.Repository;

import com.example.capstone3.Model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
    Stadium findStadiumById(Integer id);

    @Query("SELECT s FROM Stadium s WHERE s.capacity >= ?1")
    List<Stadium> findByMinimumCapacity(Integer capacity);

    @Query("SELECT s FROM Stadium s WHERE s.status = ?1")
    List<Stadium> findByStatus(String status);
}
