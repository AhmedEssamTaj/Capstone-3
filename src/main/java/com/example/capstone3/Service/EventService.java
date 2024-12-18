package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.EventDTO;
import com.example.capstone3.DTO.EventDTOout;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final StadiumRepository stadiumRepository;

    public Event getEventById(Integer id) {
        return findEvent(id);
    }

    public List<EventDTOout> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) throw new ApiException("No events found");
        return convertToDTOList(events);
    }

    public EventDTOout getEventDetailsById(Integer id) {
        Event event = findEvent(id);
        return convertToDTOout(event);
    }

    public List<EventDTOout> getEventsByStatus(String status) {
        validateStatus(status);
        List<Event> events = eventRepository.findByStatus(status);
        if (events.isEmpty()) throw new ApiException("No events found with status: " + status);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsByDate(LocalDate date) {
        validateDate(date);
        List<Event> events = eventRepository.findByDate(date);
        if (events.isEmpty()) throw new ApiException("No events found on the specified date");
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsBetweenDates(LocalDate start, LocalDate end) {
        validateDateRange(start, end);
        List<Event> events = eventRepository.findByDateBetween(start, end);
        if (events.isEmpty()) throw new ApiException("No events found in the specified date range");
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsByStartTime(LocalTime time) {
        validateTime(time);
        List<Event> events = eventRepository.findByStartTime(time);
        if (events.isEmpty()) throw new ApiException("No events found with the specified start time");
        return convertToDTOList(events);
    }


    public List<EventDTOout> getUpcomingEvents(LocalDate today) {
        List<Event> events = eventRepository.findByDateAfter(today);
        if (events.isEmpty()) throw new ApiException("No upcoming events found");
        return convertToDTOList(events);
    }

    public List<EventDTOout> getPastEvents(LocalDate today) {
        List<Event> events = eventRepository.findByDateBefore(today);
        if (events.isEmpty()) throw new ApiException("No past events found");
        return convertToDTOList(events);
    }


    public void addEvent(EventDTO dto) {
        validateEventConflict(dto);
        validateStadiumAvailability(dto.getStadium_id());
        Event event = createEventFromDTO(dto);
        eventRepository.save(event);
    }

    public void updateEventStatus(Integer id, String status) {
        Event event = findEvent(id);
        validateStatus(status);
        event.setStatus(status);
        eventRepository.save(event);
    }

    public void updateEventDate(Integer id, LocalDate date) {
        validateDate(date);
        Event event = findEvent(id);
        event.setDate(date);
        eventRepository.save(event);
    }



    public void updateEventTime(Integer id, LocalTime startTime, LocalTime endTime) {
        validateTimeRange(startTime, endTime);
        Event event = findEvent(id);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        eventRepository.save(event);
    }

    public void deleteEventById(Integer id) {
        Event event = findEvent(id);
        eventRepository.delete(event);
    }

    public void deleteEventsByStatus(String status) {
        validateStatus(status);
        List<Event> events = eventRepository.findByStatus(status);
        if (events.isEmpty()) throw new ApiException("No events with status: " + status);
        eventRepository.deleteAll(events);
    }

    private Event findEvent(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        return event;
    }

    private void validateStatus(String status) {
        if (!"Run".equals(status) && !"Ended".equals(status)) {
            throw new ApiException("Invalid status: " + status);
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new ApiException("Invalid date: " + date);
        }
    }

    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new ApiException("Invalid date range");
        }
    }


    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new ApiException("Invalid time");
        }
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new ApiException("Invalid time range");
        }
    }

    private void validateEventConflict(EventDTO dto) {
        for (Event e : eventRepository.findAll()) {
            if (e.getDate().equals(dto.getDate()) &&
                    e.getStartTime().isBefore(dto.getEndTime()) &&
                    dto.getStartTime().isBefore(e.getEndTime()) &&
                    e.getStadium().getId().equals(dto.getStadium_id())) {
                throw new ApiException("Event conflict detected for the specified time and stadium");
            }
        }
    }

    private void validateStadiumAvailability(Integer stadiumId) {
        String status = stadiumRepository.findStadiumById(stadiumId).getStatus();
        if (!"Available".equalsIgnoreCase(status)) {
            throw new ApiException("Stadium is not available");
        }
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
}
