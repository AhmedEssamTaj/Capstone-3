package com.example.capstone3.Repository;

import com.example.capstone3.Model.VolunteerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRatingRepository extends JpaRepository<VolunteerRating, Integer> {

    VolunteerRating findVolunteerRatingById (Integer id);

    List<VolunteerRating> findVolunteerRatingByVolunteerId(Integer volunteerId);
}

