package com.example.capstone3.Repository;

import com.example.capstone3.Model.Role;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleById(Integer id);

    List<Role> findByName(String name);

    List<Role> findByDescriptionContaining(String description);

    List<Role> findByEvent(Event event);

    List<Role> findByVolunteer(Volunteer volunteer);

    List<Role> findByEvent_Name(String eventName);




}
