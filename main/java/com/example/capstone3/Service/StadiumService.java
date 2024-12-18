package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;

import com.example.capstone3.DTO.StadiumDTO;
import com.example.capstone3.DTO.StadiumDTOout;
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

    public StadiumDTOout getStadiumById(Integer id) {
        Stadium stadium = stadiumRepository.findStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
        return convertToDTOout(stadium);
    }

    public List<StadiumDTOout> getAllStadiumDTOs() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        List<StadiumDTOout> stadiumDTOs = new ArrayList<>();
        for (Stadium s : stadiums) {
            stadiumDTOs.add(convertToDTOout(s));
        }
        return stadiumDTOs;
    }

    public void addStadium(StadiumDTO updatedStadium) {

        Stadium stadium=new Stadium();
        stadium.setName(updatedStadium.getName());
        stadium.setCity(updatedStadium.getCity());
        stadium.setLocation(updatedStadium.getLocation());
        stadium.setNumberOfGates(updatedStadium.getNumberOfGates());
        stadium.setParkingCapacity(updatedStadium.getParkingCapacity());
        stadium.setEmergencyExits(updatedStadium.getEmergencyExits());
        stadium.setCapacity(updatedStadium.getCapacity());
        stadium.setStatus(updatedStadium.getStatus());
        stadiumRepository.save(stadium);
    }

    public void deleteStadium(Integer id) {
        Stadium stadium = stadiumRepository.findStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
        stadiumRepository.delete(stadium);
    }

    public void updateStadium(Integer id, StadiumDTO updatedStadium) {
        Stadium stadium =stadiumRepository.findStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
        stadium.setName(updatedStadium.getName());
        stadium.setCity(updatedStadium.getCity());
        stadium.setLocation(updatedStadium.getLocation());
        stadium.setNumberOfGates(updatedStadium.getNumberOfGates());
        stadium.setParkingCapacity(updatedStadium.getParkingCapacity());
        stadium.setEmergencyExits(updatedStadium.getEmergencyExits());
        stadium.setCapacity(updatedStadium.getCapacity());
        stadium.setStatus(updatedStadium.getStatus());
        stadiumRepository.save(stadium);
    }

    private StadiumDTOout convertToDTOout(Stadium stadium) {
        return new StadiumDTOout(stadium.getName(),stadium.getLocation(),stadium.getCapacity(),stadium.getStatus());
    }

}
