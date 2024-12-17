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

    // GET Methods
    public StadiumDTOout getStadiumById(Integer id) {
        Stadium stadium = findStadium(id);
        return convertToDTOout(stadium);
    }

    public List<StadiumDTOout> getAllStadiums() {
        List<Stadium> stadiums = stadiumRepository.findAll();
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsByCity(String city) {
        List<Stadium> stadiums = stadiumRepository.findByCity(city);
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsByCapacity(Integer capacity) {
        List<Stadium> stadiums = stadiumRepository.findByCapacityGreaterThanEqual(capacity);
        return convertToDTOList(stadiums);
    }

    public long countStadiums() {
        return stadiumRepository.count();
    }

    public boolean existsStadiumById(Integer id) {
        return stadiumRepository.existsById(id);
    }

    public List<StadiumDTOout> getStadiumsByStatus(String status) {
        List<Stadium> stadiums = stadiumRepository.findByStatus(status);
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsWithParkingCapacityGreaterThan(Integer parkingCapacity) {
        List<Stadium> stadiums = stadiumRepository.findByParkingCapacityGreaterThanEqual(parkingCapacity);
        return convertToDTOList(stadiums);
    }

    public StadiumDTOout getFirstStadium() {
        Stadium stadium = stadiumRepository.findTopByOrderByIdAsc();
        return convertToDTOout(stadium);
    }

    public Stadium getLargestStadium() {
        return stadiumRepository.findTopByOrderByCapacityDesc();
    }

    public List<StadiumDTOout> getStadiumsWithEmergencyExitsGreaterThan(Integer exits) {
        List<Stadium> stadiums = stadiumRepository.findByEmergencyExitsGreaterThanEqual(exits);
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsByCityAndStatus(String city, String status) {
        List<Stadium> stadiums = stadiumRepository.findByCityAndStatus(city, status);
        return convertToDTOList(stadiums);
    }

    public List<StadiumDTOout> getStadiumsByMultipleCities(List<String> cities) {
        List<Stadium> stadiums = stadiumRepository.findByCityIn(cities);
        return convertToDTOList(stadiums);
    }



    // POST Methods
    public void addStadium(StadiumDTO dto) {
        Stadium stadium = createStadiumFromDTO(dto);
        stadiumRepository.save(stadium);
    }

    public void addStadiumsInBulk(List<StadiumDTO> dtos) {
        for (StadiumDTO dto : dtos) {
            addStadium(dto);
        }
    }

    public void duplicateStadium(Integer id) {
        Stadium existing = findStadium(id);
        Stadium duplicate = new Stadium();
        duplicate.setName(existing.getName() + "_copy");
        duplicate.setCity(existing.getCity());
        duplicate.setLocation(existing.getLocation());
        duplicate.setCapacity(existing.getCapacity());
        duplicate.setParkingCapacity(existing.getParkingCapacity());
        duplicate.setEmergencyExits(existing.getEmergencyExits());
        duplicate.setStatus(existing.getStatus());
        stadiumRepository.save(duplicate);
    }

    public void addStadiumWithDefaultCapacity(StadiumDTO dto, Integer defaultCapacity) {
        Stadium stadium = createStadiumFromDTO(dto);
        stadium.setCapacity(defaultCapacity);
        stadiumRepository.save(stadium);
    }

    // PUT Methods
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
        stadium.setLocation(location);
        stadiumRepository.save(stadium);
    }

    public void updateStadiumCapacity(Integer id, Integer capacity) {
        Stadium stadium = findStadium(id);
        stadium.setCapacity(capacity);
        stadiumRepository.save(stadium);
    }

    public void updateStadiumParkingCapacity(Integer id, Integer parkingCapacity) {
        Stadium stadium = findStadium(id);
        stadium.setParkingCapacity(parkingCapacity);
        stadiumRepository.save(stadium);
    }

    public void updateStadiumStatus(Integer id, String status) {
        Stadium stadium = findStadium(id);
        stadium.setStatus(status);
        stadiumRepository.save(stadium);
    }

    // DELETE Methods
    public void deleteStadiumById(Integer id) {
        Stadium stadium = findStadium(id);
        stadiumRepository.delete(stadium);
    }

    public void deleteStadiumsByCity(String city) {
        List<Stadium> stadiums = stadiumRepository.findByCity(city);
        if (stadiums.isEmpty()) {
            throw new ApiException("No stadiums found in city: " + city);
        }
        stadiumRepository.deleteAll(stadiums);
    }

    public void deleteStadiumsByStatus(String status) {
        List<Stadium> stadiums = stadiumRepository.findByStatus(status);
        if (stadiums.isEmpty()) {
            throw new ApiException("No stadiums with status: " + status);
        }
        stadiumRepository.deleteAll(stadiums);
    }

    public void deleteStadiumsWithCapacityLessThan(Integer capacity) {
        List<Stadium> stadiums = stadiumRepository.findByCapacityLessThan(capacity);
        if (stadiums.isEmpty()) {
            throw new ApiException("No stadiums with capacity less than: " + capacity);
        }
        stadiumRepository.deleteAll(stadiums);
    }

    public void deleteAllStadiums() {
        stadiumRepository.deleteAll();
    }

    // Utility Methods
    private Stadium findStadium(Integer id) {
        Stadium stadium = stadiumRepository.findStadiumById(id);
        if (stadium == null) {
            throw new ApiException("Stadium not found");
        }
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
