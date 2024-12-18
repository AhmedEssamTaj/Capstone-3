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
        Stadium stadium = findStadium(id);
        return convertToDTOout(stadium);
    }

    public List<StadiumDTOout> getAllStadiums() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found");
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsByCity(String city) {
        validateCity(city);
        List<Stadium> stadiums = stadiumRepository.findByCity(city);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found in city: " + city);
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsByCapacity(Integer capacity) {
        validateCapacity(capacity);
        List<Stadium> stadiums = stadiumRepository.findByCapacityGreaterThanEqual(capacity);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found with capacity >= " + capacity);
        return convertToDTOList(stadiums);
    }

    public long countStadiums() {
        long count = stadiumRepository.count();
        if (count == 0) throw new ApiException("No stadiums found in the system");
        return count;
    }


    public List<StadiumDTOout> getStadiumsByStatus(String status) {
        validateStatus(status);
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
        validateStadiumDTO(dto);
        Stadium stadium = createStadiumFromDTO(dto);
        stadiumRepository.save(stadium);
    }


    public void updateStadiumName(Integer id, String name) {
        Stadium stadium = findStadium(id);
        validateName(name);
        stadium.setName(name);
        stadiumRepository.save(stadium);
    }

    public void updateStadiumCity(Integer id, String city) {
        Stadium stadium = findStadium(id);
        validateCity(city);
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
        validateCapacity(capacity);
        stadium.setCapacity(capacity);
        stadiumRepository.save(stadium);
    }


    public void updateStadiumStatus(Integer id, String status) {
        Stadium stadium = findStadium(id);
        validateStatus(status);
        stadium.setStatus(status);
        stadiumRepository.save(stadium);
    }

    public void deleteStadiumById(Integer id) {
        Stadium stadium = findStadium(id);
        stadiumRepository.delete(stadium);
    }

    public void deleteStadiumsByCity(String city) {
        validateCity(city);
        List<Stadium> stadiums = stadiumRepository.findByCity(city);
        if (stadiums.isEmpty()) throw new ApiException("No stadiums found in city: " + city);
        stadiumRepository.deleteAll(stadiums);
    }

    public void deleteStadiumsByStatus(String status) {
        validateStatus(status);
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

    private void validateStadiumDTO(StadiumDTO dto) {
        if (dto == null) throw new ApiException("Stadium data cannot be null");
        validateName(dto.getName());
        validateCity(dto.getCity());
        validateCapacity(dto.getCapacity());
        validateStatus(dto.getStatus());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) throw new ApiException("Name cannot be null or empty");
    }

    private void validateCity(String city) {
        if (city == null || city.trim().isEmpty())
            throw new ApiException("City cannot be null or empty");
    }

    private void validateCapacity(Integer capacity) {
        if (capacity == null || capacity <= 0)
            throw new ApiException("Capacity must be greater than 0");
    }

    private void validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) throw new ApiException("Status cannot be null or empty");
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
