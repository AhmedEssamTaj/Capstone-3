package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Stadium;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Model.VolunteerRating;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.StadiumRepository;
import com.example.capstone3.Repository.VolunteerRatingRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VolunteerRatingService {

    private final VolunteerRatingRepository volunteerRatingRepository;
    private final VolunteerRepository volunteerRepository;
    private final StadiumRepository stadiumRepository;
    private final EventRepository eventRepository;


    // method to get ALL the volunteers rating
    public List<VolunteerRating> getAllVolunteerRating(){
        return volunteerRatingRepository.findAll();
    }

    // method to get ALL Ratings for a specific volunteer
    public List<VolunteerRating> getAllVolunteerRatingByVolunteerId(Integer volunteerId){

        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerId);
        if (volunteer == null){
           throw new ApiException("no volunteer with this id was found");
        }
        return volunteerRatingRepository.findVolunteerRatingByVolunteerId(volunteerId);

    }

    // method to add ration to a volunteer
    public void addVolunteerRating(VolunteerRating volunteerRating){

        Stadium stadium = stadiumRepository.findStadiumById(volunteerRating.getStadium().getId());
        Event event = eventRepository.findEventById(volunteerRating.getEvent().getId());
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerRating.getVolunteer().getId());

        if (volunteer == null || event == null || stadium == null){
            throw new ApiException("no volunteer or event or stadium was found");
        }
        // assign upon creation
        volunteerRating.setVolunteer(volunteer);
        volunteerRating.setEvent(event);
        volunteerRating.setStadium(stadium);
        volunteerRatingRepository.save(volunteerRating);

    }

    // update volunteer rating
    public void updateVolunteerRating(VolunteerRating volunteerRating){

        VolunteerRating oldVolunteerRating = volunteerRatingRepository.findVolunteerRatingById(volunteerRating.getId());
        if (oldVolunteerRating == null){
            throw new ApiException("no volunteer with this id was found");
        }
        oldVolunteerRating.setFeedback(volunteerRating.getFeedback());
        oldVolunteerRating.setRating(volunteerRating.getRating());
        volunteerRatingRepository.save(oldVolunteerRating);
    }

    // method to delete volunteer rating
    public void deleteVolunteerRating(Integer volunteerRatingId){
        VolunteerRating volunteerRating = volunteerRatingRepository.findVolunteerRatingById(volunteerRatingId);
        if (volunteerRating == null){
            throw new ApiException("no volunteer with this id was found");
        }
        volunteerRatingRepository.delete(volunteerRating);
    }



    // method to get the average rating for a volunteer (Aishtiaq-1)
     public double volunteersByTheHighestAverageOfRatings(Integer volunteerId){
      List<VolunteerRating> volunteerRating=volunteerRatingRepository.findVolunteerRatingByVolunteerId(volunteerId);
       ArrayList<Integer>sumArrayList=new ArrayList<>();
       if(volunteerRating==null){
           throw new ApiException("no volunteer with this id was found");
       }
       int sum=0;
       double average=0;
       for (VolunteerRating v : volunteerRating){
        sum+=v.getRating() ;
           sumArrayList.add(sum);
           average=sumArrayList.size();

      }
       return sum/average;


       }


    }



