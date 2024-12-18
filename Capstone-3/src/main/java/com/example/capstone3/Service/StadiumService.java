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

// Bushra

public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public StadiumDTOout getStadiumById(Integer id) {
        Stadium stadium = findStadium(id);
        return convertToDTOout(stadium);
    }

    public List<StadiumDTOout> getAllStadiums() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found");
        return convertToDTOList(stadiums);
    }
    public List<StadiumDTOout> getStadiumsByCity(String city) {
        List<Stadium> stadiums = stadiumRepository.findByCity(city);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found in city: " + city);
        return convertToDTOList(stadiums);
    }
    public List<StadiumDTOout> getStadiumsByStatus(String status) {

        List<Stadium> stadiums = stadiumRepository.findByStatus(status);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found with status: " + status);
        return convertToDTOList(stadiums);
    }
    public Stadium getLargestStadium() {
        Stadium stadium = stadiumRepository.findTopByOrderByCapacityDesc();
        if (stadium == null) throw new ApiException("No stadiums found");
        return stadium;
    }

    public void addStadium(StadiumDTO dto) {

        Stadium stadium = createStadiumFromDTO(dto);
        stadiumRepository.save(stadium);
    }
    public void updateStadiumName(Integer id, String name) {
        Stadium stadium = findStadium(id);

        stadium.setName(name);
        stadiumRepository.save(stadium);
    }
    public void updateStadiumCity(Integer id, String city) {
        Stadium stadium = findStadium(id);

        stadium.setCity(city);
        stadiumRepository.save(stadium);
    }
    public void updateStadiumLocation(Integer id, String location) {
        Stadium stadium = findStadium(id);
        if (location == null || location.trim().isEmpty()) throw new ApiException("Location cannot be null or empty");
        stadium.setLocation(location);
        stadiumRepository.save(stadium);
    }
    public void updateStadiumCapacity(Integer id, Integer capacity) {
        Stadium stadium = findStadium(id);

        stadium.setCapacity(capacity);
        stadiumRepository.save(stadium);
    }
    public void updateStadiumStatus(Integer id, String status) {
        Stadium stadium = findStadium(id);

        stadium.setStatus(status);
        stadiumRepository.save(stadium);
    }
    public void deleteStadiumById(Integer id) {
        Stadium stadium = findStadium(id);
        stadiumRepository.delete(stadium);
    }
    public void deleteStadiumsByCity(String city) {
        List<Stadium> stadiums = stadiumRepository.findByCity(city);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found in city: " + city);
        stadiumRepository.deleteAll(stadiums);
    }
    public void deleteStadiumsByStatus(String status) {

        List<Stadium> stadiums = stadiumRepository.findByStatus(status);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found with status: " + status);
        stadiumRepository.deleteAll(stadiums);
    }
    public void deleteAllStadiums() {
        stadiumRepository.deleteAll();
    }
    private Stadium findStadium(Integer id) {
        if (id == null) throw new ApiException("Stadium ID cannot be null");
        Stadium stadium = stadiumRepository.findStadiumById(id);
        if (stadium == null) throw new ApiException("Stadium not found");
        return stadium;
    }
    private Stadium createStadiumFromDTO(StadiumDTO dto) {
        Stadium stadium = new Stadium();
        stadium.setName(dto.getName());
        stadium.setCity(dto.getCity());
        stadium.setLocation(dto.getLocation());
        stadium.setCapacity(dto.getCapacity());
        stadium.setParkingCapacity(dto.getParkingCapacity());
        stadium.setEmergencyExits(dto.getEmergencyExits());
        stadium.setStatus(dto.getStatus());
        return stadium;
    }
    private StadiumDTOout convertToDTOout(Stadium stadium) {
        return new StadiumDTOout(stadium.getName(), stadium.getLocation(), stadium.getCapacity(), stadium.getStatus());
    }
    private List<StadiumDTOout> convertToDTOList(List<Stadium> stadiums) {
        List<StadiumDTOout> list = new ArrayList<>();
        for (Stadium s : stadiums) {
            list.add(convertToDTOout(s));
        }
        return list;
    }

}
