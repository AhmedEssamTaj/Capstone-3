package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.EventDTO;
import com.example.capstone3.DTO.EventDTOout;
import com.example.capstone3.DTO.VolunteerDTO;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.StadiumRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final StadiumRepository stadiumRepository;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerService volunteerService;

    public Event getEventById(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        return event;
    }

    public List<EventDTOout> getAllEventDTOs() {
        List<Event> events = eventRepository.findAll();
        List<EventDTOout> eventDTOs = new ArrayList<>();
        for (Event e : events) {
            eventDTOs.add(convertToDTOout(e));
        }
        return eventDTOs;
    }


    public void addEvent(EventDTO event_in) {
        Event event=new Event();
        if(stadiumRepository.findStadiumById(event_in.getStadium_id()).getStatus()!="Maintenance"){
        event.setName(event_in.getName());
        event.setDate(event_in.getDate());
        event.setStartTime(event_in.getStartTime());
        event.setEndTime(event_in.getEndTime());
        event.setMaxCapacity(event_in.getMaxCapacity());
        event.setStatus(event_in.getStatus());
        event.setStadium(stadiumRepository.findStadiumById(event_in.getStadium_id()));
        eventRepository.save(event);}
        throw new ApiException("Event cant be added the stadium in Maintenance");
    }

    public void deleteEvent(Integer id) {
        Event event = getEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        eventRepository.delete(event);
    }

    public void updateEvent(Integer id, EventDTO updatedEvent) {
        Event event = getEventById(id);
        if (event == null) {
            throw new ApiException("Event not found");
        }
        event.setName(updatedEvent.getName());
        event.setDate(updatedEvent.getDate());
        event.setStartTime(updatedEvent.getStartTime());
        event.setEndTime(updatedEvent.getEndTime());
        event.setStatus(updatedEvent.getStatus());
        event.setStadium(stadiumRepository.findStadiumById(updatedEvent.getStadium_id()));
        eventRepository.save(event);
    }

    private EventDTOout convertToDTOout(Event event) {
        return new EventDTOout(event.getName(),event.getDate(),event.getStartTime(),event.getEndTime(),event.getStatus(),event.getStadium().getName());
    }


}
