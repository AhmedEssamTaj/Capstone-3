package com.example.capstone3.Repository;

import com.example.capstone3.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository

// Bushra

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.status = ?1")
    List<Event> findByStatus(String status);

    List<Event> findAll();

    @Query("SELECT e FROM Event e WHERE e.date BETWEEN ?1 AND ?2")
    List<Event> findByDateBetween(LocalDate start, LocalDate end);

    Event findEventById(Integer id);
    Event findEventByStadium_IdAndDate(Integer id,LocalDate date);
    Event findEventByDateAndStartTimeAndEndTime(LocalDate date, LocalTime startTime, LocalTime endTime);

    List<Event> findByDateBefore(LocalDate date);

    List<Event> findByDateAfter(LocalDate date);


    //list of all upcoming (future)  events for this stadium  (Aishtiaq-4)
    List<Event> findByStadiumIdAndDateAfter(Integer stadiumId, LocalDate currentDate);

    // Get a list of Full Events (Aishtiaq-9)
    @Query("SELECT e FROM Event e WHERE SIZE(e.volunteers) >= e.maxCapacity")
    List<Event> findFullEvents();

}
