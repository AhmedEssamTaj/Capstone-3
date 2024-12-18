package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.EventDTO;
import com.example.capstone3.DTO.EventDTOout;
import com.example.capstone3.DTO.VolunteerDTO;
import com.example.capstone3.Model.*;
import com.example.capstone3.Repository.AttendanceRepository;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.StadiumRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

// Bushra

public class EventService {
    private final EventRepository eventRepository;
    private final StadiumRepository stadiumRepository;
    private final AttendanceRepository attendanceRepository;
    public List<EventDTOout> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) throw new ApiException("No events found");
        return convertToDTOList(events);
    }
    public List<EventDTOout> getAllEventsWithoutRole() {
        List<Event> events = eventRepository.findAll();
        List<Event> eventWithNoRole = new ArrayList<>();
        if (events.isEmpty()) throw new ApiException("No events found");
        for (Event event : events) {
            if (event.getRoles().size() == 0) eventWithNoRole.add(event);
        }
        return convertToDTOList(eventWithNoRole);
    }
    public List<Volunteer> getAllVolunteersInEvent(Integer event) {
        List<Volunteer>eventVolunteers = new ArrayList<>();
        Event event1 = eventRepository.findEventById(event);
        if (event1 == null) throw new ApiException("No events found");
        for (Role role : event1.getRoles()) {
            eventVolunteers.add(role.getVolunteer());
        }
        return eventVolunteers;
    }
    public EventDTOout getEventDetailsById(Integer id) {
        Event event = findEvent(id);
        return convertToDTOout(event);
    }
    public List<EventDTOout> getEventsByStatus(String status) {
        List<Event> events = eventRepository.findByStatus(status);
        if (events.isEmpty()) throw new ApiException("No events found with status: " + status);
        return convertToDTOList(events);
    }
    public EventDTOout getEventsByDateAndStadium(Integer stadium_id, LocalDate date) {
        if (stadiumRepository.findStadiumById(stadium_id) == null)
        {throw new ApiException("No stadium found with id: ");}

        Event event = eventRepository.findEventByStadium_IdAndDate(stadium_id,date);
        if (event==null) throw new ApiException("No event found on the specified date");
        return convertToDTOout(event);
    }
    public List<EventDTOout> getEventsBetweenDates(LocalDate start, LocalDate end) {
        List<Event> events = eventRepository.findByDateBetween(start, end);
        if (events.isEmpty())
            throw new ApiException("No events found in the specified date range");
        return convertToDTOList(events);
    }
    public List<EventDTOout> getUpcomingEvents() {
        LocalDate today = LocalDate.now();
        List<Event> events = eventRepository.findByDateAfter(today);
        if (events.isEmpty()) throw new ApiException("No upcoming events found");
        return convertToDTOList(events);
    }
    public List<EventDTOout> getPastEvents() {
        LocalDate today = LocalDate.now();
        List<Event> events = eventRepository.findByDateBefore(today);
        if (events.isEmpty()) throw new ApiException("No past events found");
        return convertToDTOList(events);
    }
    public void addEvent(EventDTO dto) {
        if(stadiumRepository.findStadiumById(dto.getStadium_id()) == null){
            throw new ApiException("No stadium found with id: ");
        }
        if (eventRepository.findEventByDateAndStartTimeAndEndTime(dto.getDate(),dto.getStartTime(),dto.getEndTime()) != null)
            throw new ApiException("conflict with other event");
        if (stadiumRepository.findStadiumById(dto.getStadium_id()).getStatus().equals("Available")){
            Event event = createEventFromDTO(dto);
            eventRepository.save(event);
        }else {
            throw new ApiException("stadium are not available");
        }
    }

    public void updateEventStatus(Integer id, String status) {
        if (eventRepository.findEventById(id) == null) {throw new ApiException("Event not found");}

        Event event = findEvent(id);
        event.setStatus(status);
        eventRepository.save(event);
    }
    public void updateEventDate(Integer id, LocalDate date) {
        if (eventRepository.findEventById(id) == null) {throw new ApiException("Event not found");}

        Event event = findEvent(id);
        attendanceRepository.findByEvent(event).remove(attendanceRepository.findByEvent(event));
        event.setDate(date);
        eventRepository.save(event);
    }
    public void updateEventTime(Integer id, LocalTime startTime, LocalTime endTime) {
        if (eventRepository.findEventById(id) == null) {throw new ApiException("Event not found");}
        Event event = findEvent(id);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        List<Attendance> attendanceList=attendanceRepository.findByEvent(event);
        for (Attendance attendance : attendanceList) {
            if (attendance.getCheckIn()!=null){
                attendance.setCheckIn(event.getStartTime());
                attendanceRepository.save(attendance);
            }
        }
        eventRepository.save(event);
    }
    public void deleteEventById(Integer id) {
        if (eventRepository.findEventById(id) == null) {throw new ApiException("Event not found");}

        Event event = findEvent(id);
        eventRepository.delete(event);
    }
    private Event findEvent(Integer id) {

        Event event = eventRepository.findEventById(id);
        if (eventRepository.findEventById(id) == null) {throw new ApiException("Event not found");}

        if (event == null) {
            throw new ApiException("Event not found");
        }
        return event;
    }
    private Event createEventFromDTO(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDate(dto.getDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setMaxCapacity(dto.getMaxCapacity());
        event.setStatus(dto.getStatus());
        event.setStadium(stadiumRepository.findStadiumById(dto.getStadium_id()));
        return event;
    }
    private EventDTOout convertToDTOout(Event event) {
        return new EventDTOout(event.getName(), event.getDate(), event.getStartTime(), event.getEndTime(),
                event.getStatus(), event.getStadium().getName());
    }
    private List<EventDTOout> convertToDTOList(List<Event> events) {
        List<EventDTOout> list = new ArrayList<>();
        for (Event e : events) {
            list.add(convertToDTOout(e));
        }
        return list;
    }

    //list of all upcoming (future)  events for this stadium  (Aishtiaq-4)
    public List<Event> getUpcomingEventsForStadium(Integer stadiumId) {
        return eventRepository.findByStadiumIdAndDateAfter(stadiumId, LocalDate.now());
    }

    // Get a list of Full Events (Aishtiaq-9)

    public List<Event> getFullEvents() {
        List<Event> fullEvents = eventRepository.findFullEvents();
        if (fullEvents.isEmpty()) {
            throw new RuntimeException("No full events found.");
        }
        return fullEvents;
    }
}


