package com.example.capstone3.Repository;

import com.example.capstone3.Model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {

    @Query("SELECT s FROM Stadium s WHERE s.city = :city")
    List<Stadium> findByCity(String city);
    @Query("SELECT s FROM Stadium s WHERE s.capacity >= :capacity")
    List<Stadium> findByCapacityGreaterThanEqual(Integer capacity);
    @Query("SELECT s FROM Stadium s WHERE s.status = :status")
    List<Stadium> findByStatus(String status);
    @Query("SELECT s FROM Stadium s WHERE s.parkingCapacity >= :capacity")
    List<Stadium> findByParkingCapacityGreaterThanEqual(Integer capacity);
    @Query("SELECT s FROM Stadium s WHERE s.emergencyExits >= :exits")
    List<Stadium> findByEmergencyExitsGreaterThanEqual(Integer exits);
    @Query("SELECT s FROM Stadium s WHERE s.city = :city AND s.status = :status")
    List<Stadium> findByCityAndStatus(String city, String status);
    @Query("SELECT s FROM Stadium s WHERE s.city IN :cities")
    List<Stadium> findByCityIn(List<String> cities);

    Stadium findStadiumById(Integer id);
    List<Stadium> findByCapacityLessThan(Integer capacity);
    Stadium findTopByOrderByCapacityDesc();
    Stadium findTopByOrderByIdAsc();
}