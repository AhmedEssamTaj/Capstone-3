package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.StadiumDTO;
import com.example.capstone3.Model.Stadium;
import com.example.capstone3.Repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public Stadium getStadiumById(Integer id) {
        Stadium stadium = stadiumRepository.findStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
        return stadium;
    }

    public List<StadiumDTO> getAllStadiumDTOs() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        List<StadiumDTO> stadiumDTOs = new ArrayList<>();
        for (Stadium s : stadiums) {
            stadiumDTOs.add(new StadiumDTO(s.getId(), s.getName(), s.getLocation(), s.getCapacity(), s.getStatus()));
        }
        return stadiumDTOs;
    }

    public void addStadium(Stadium stadium) {
        stadiumRepository.save(stadium);
    }

    public void deleteStadium(Integer id) {
        Stadium stadium = getStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
        stadiumRepository.delete(stadium);
    }

    public void updateStadium(Integer id, Stadium updatedStadium) {
        Stadium stadium = getStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
        stadium.setName(updatedStadium.getName());
        stadium.setLocation(updatedStadium.getLocation());
        stadium.setCapacity(updatedStadium.getCapacity());
        stadium.setStatus(updatedStadium.getStatus());
        stadiumRepository.save(stadium);
    }
}
