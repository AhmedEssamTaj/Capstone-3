package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.EventDTO;
import com.example.capstone3.DTO.EventDTOout;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Stadium;
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
        return convertToDTOList(events);
    }

    public EventDTOout getEventDetailsById(Integer id) {
        Event event = findEvent(id);
        return convertToDTOout(event);
    }

    public List<EventDTOout> getEventsByStatus(String status) {
        List<Event> events = eventRepository.findByStatus(status);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsByDate(LocalDate date) {
        List<Event> events = eventRepository.findByDate(date);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsBetweenDates(LocalDate start, LocalDate end) {
        List<Event> events = eventRepository.findByDateBetween(start, end);
        return convertToDTOList(events);
    }

    public Event getLatestEvent() {
        return eventRepository.findTopByOrderByDateDesc();
    }

    public List<EventDTOout> getEventsByCapacity(Integer capacity) {
        List<Event> events = eventRepository.findByMaxCapacityGreaterThanEqual(capacity);
        return convertToDTOList(events);
    }

    public long countEvents() {
        return eventRepository.count();
    }

    public List<EventDTOout> getEventsByStartTime(LocalTime time) {
        List<Event> events = eventRepository.findByStartTime(time);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsByEndTime(LocalTime time) {
        List<Event> events = eventRepository.findByEndTime(time);
        return convertToDTOList(events);
    }

    public boolean existsById(Integer id) {
        return eventRepository.existsById(id);
    }

    public List<EventDTOout> getUpcomingEvents(LocalDate today) {
        List<Event> events = eventRepository.findByDateAfter(today);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getPastEvents(LocalDate today) {
        List<Event> events = eventRepository.findByDateBefore(today);
        return convertToDTOList(events);
    }

    public EventDTOout getFirstEvent() {
        Event event = eventRepository.findTopByOrderByIdAsc();
        return convertToDTOout(event);
    }


    public List<EventDTOout> getEventsWithinCapacityRange(Integer min, Integer max) {
        List<Event> events = eventRepository.findByMaxCapacityBetween(min, max);
        return convertToDTOList(events);
    }

    public boolean isEventAvailable(Integer id) {
        Event event = findEvent(id);
        return "Available".equalsIgnoreCase(event.getStatus());
    }

    public List<EventDTOout> getEventsByMultipleStatuses(List<String> statuses) {
        List<Event> events = eventRepository.findByStatusIn(statuses);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsWithStadiumName(String stadiumName) {
        List<Event> events = eventRepository.findByStadium_Name(stadiumName);
        return convertToDTOList(events);
    }


    public List<EventDTOout> getEventsByStadiumCapacityRange(Integer min, Integer max) {
        List<Event> events = eventRepository.findByStadium_CapacityBetween(min, max);
        return convertToDTOList(events);
    }

    public List<EventDTOout> getEventsByStadiumLocation(String location) {
        List<Event> events = eventRepository.findByStadium_Location(location);
        return convertToDTOList(events);
    }


    public void addEvent(EventDTO dto) {
        for (Event e : eventRepository.findAll()) {
            if (e.getDate()==dto.getDate()&&e.getStartTime()==dto.getStartTime()){
                throw new ApiException("there is another event in  the same time");
            }
        }
        if (stadiumRepository.findStadiumById(dto.getStadium_id()).getStatus()!="Available"){
            throw new ApiException("the stadium is not Available");
        }
        validateStadiumStatus(dto.getStadium_id());
        Event event = createEventFromDTO(dto);
        eventRepository.save(event);
    }


    public void addEventWithFixedStatus(EventDTO dto, String status) {
        validateStadiumStatus(dto.getStadium_id());
        Event event = createEventFromDTO(dto);
        event.setStatus(status);
        eventRepository.save(event);
    }

    public void addEventsInBulk(List<EventDTO> dtos) {
        for (EventDTO dto : dtos) {
            addEvent(dto);
        }
    }

    public void addEventToStadium(Integer stadiumId, EventDTO dto) {

        validateStadiumStatus(stadiumId);
        Event event = createEventFromDTO(dto);
        event.setStadium(stadiumRepository.findStadiumById(stadiumId));
        eventRepository.save(event);
    }


    public void updateEventStatus(Integer id, String status) {
        Event event = findEvent(id);
        event.setStatus(status);
        eventRepository.save(event);
    }

    public void updateEventDate(Integer id, LocalDate date) {
        Event event = findEvent(id);
        event.setDate(date);
        eventRepository.save(event);
    }

    public void updateEventCapacity(Integer id, Integer capacity) {
        Event event = findEvent(id);
        event.setMaxCapacity(capacity);
        eventRepository.save(event);
    }

    public void updateEventTime(Integer id, LocalTime startTime, LocalTime endTime) {
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
        List<Event> events = eventRepository.findByStatus(status);
        if (events.isEmpty()) throw new ApiException("No events with status: " + status);
        eventRepository.deleteAll(events);
    }

    public void deleteEventsByStadium(Integer stadiumId) {

        List<Event> events = eventRepository.findEventsByStadium(stadiumRepository.findStadiumById(stadiumId));
        eventRepository.deleteAll(events);
    }

    public void deleteEventsBeforeDate(LocalDate date) {
        List<Event> events = eventRepository.findByDateBefore(date);
        eventRepository.deleteAll(events);
    }


    private Event findEvent(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        return event;
    }


    private void validateStadiumStatus(Integer stadiumId) {

        if ("Maintenance".equalsIgnoreCase(stadiumRepository.findStadiumById(stadiumId).getStatus())) {
            throw new ApiException("Cannot add event. Stadium under maintenance.");
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
        return new EventDTOout(event.getName(), event.getDate(), event.getStartTime(), event.getEndTime(), event.getStatus(), event.getStadium().getName());
    }

    private List<EventDTOout> convertToDTOList(List<Event> events) {
        List<EventDTOout> list = new ArrayList<>();
        for (Event e : events) {
            list.add(convertToDTOout(e));
        }
        return list;
    }
}
