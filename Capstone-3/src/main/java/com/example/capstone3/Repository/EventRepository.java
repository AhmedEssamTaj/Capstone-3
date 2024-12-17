package com.example.capstone3.Repository;

import com.example.capstone3.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findEventById(Integer id);

    @Query("SELECT e FROM Event e WHERE e.date = ?1")
    List<Event> findByDate(Date date);

    @Query("SELECT e FROM Event e WHERE e.stadium.id = ?1")
    List<Event> findByStadiumId(Integer stadiumId);

    @Query("SELECT e FROM Event e WHERE e.status = ?1")
    List<Event> findByStatus(String status);
}
