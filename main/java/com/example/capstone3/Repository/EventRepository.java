package com.example.capstone3.Repository;

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
    @Query("SELECT e FROM Event e WHERE e.maxCapacity >= ?1")
    List<Event> findByMaxCapacityGreaterThanEqual(Integer capacity);
    @Query("SELECT e FROM Event e WHERE e.startTime = ?1")
    List<Event> findByStartTime(LocalTime time);
    @Query("SELECT e FROM Event e WHERE e.endTime = ?1")
    List<Event> findByEndTime(LocalTime time);
    @Query("SELECT e FROM Event e WHERE e.date = ?1")
    List<Event> findByDate(LocalDate date);
    @Query("SELECT e FROM Event e WHERE e.stadium.location = ?1")
    List<Event> findByStadium_Location(String location);
    @Query("SELECT e FROM Event e WHERE e.stadium = ?1 AND e.date = ?2")
    Event findByStadiumAndDate(Stadium stadium, LocalDate date);

    @Query("SELECT e FROM Event e WHERE e.maxCapacity BETWEEN ?1 AND ?2")
    List<Event> findByMaxCapacityBetween(Integer min, Integer max);

    @Query("SELECT e FROM Event e ORDER BY e.id ASC")
    Event findTopByOrderByIdAsc();

    Event findEventById(Integer id);

    List<Event> findByStatusIn(List<String> statuses);

    List<Event> findByDateBefore(LocalDate date);

    List<Event> findByDateAfter(LocalDate date);
    Event findTopByOrderByDateDesc();
    List<Event> findEventsByStadium(Stadium stadium);
    List<Event> findByStadium_Name(String stadiumName);
    List<Event> findByStadium_CapacityBetween(Integer min, Integer max);

}
