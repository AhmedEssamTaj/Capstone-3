package com.example.capstone3.Repository;
//bushra

import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Stadium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.status = ?1")
    List<Event> findByStatus(String status);

    List<Event> findAll();
    @Query("SELECT e FROM Event e WHERE e.date BETWEEN ?1 AND ?2")
    List<Event> findByDateBetween(LocalDate start, LocalDate end);

    Event findEventById(Integer id);
    Event findEventByStadium_IdAndDate(Integer id,LocalDate date);

    List<Event> findByDateBefore(LocalDate date);

    List<Event> findByDateAfter(LocalDate date);


}
