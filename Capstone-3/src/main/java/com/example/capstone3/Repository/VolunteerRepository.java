package com.example.capstone3.Repository;

import com.example.capstone3.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer,Integer> {

    Volunteer findVolunteerById(Integer id);
}
