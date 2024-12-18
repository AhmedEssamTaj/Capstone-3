package com.example.capstone3.Service;

import com.example.capstone3.ApiResponse.ApiException;
import com.example.capstone3.DTO.RoleDTO;
import com.example.capstone3.DTO.RoleDTOout;
import com.example.capstone3.Model.Role;
import com.example.capstone3.Model.Event;
import com.example.capstone3.Model.Volunteer;
import com.example.capstone3.Repository.RoleRepository;
import com.example.capstone3.Repository.EventRepository;
import com.example.capstone3.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;

    public RoleDTOout getRoleById(Integer id) {
        if (roleRepository.findById(id).isPresent()) {throw new ApiException("Role not found");}
        Role role = findRole(id);
        return convertToDTOout(role);
    }
    public List<RoleDTOout> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) throw new ApiException("No roles found");
        return convertToDTOList(roles);
    }
    public List<RoleDTOout> getRolesByEventId(Integer eventId) {
        if (eventRepository.findEventById(eventId)==null) {throw new ApiException("Event not found");}
        Event event = eventRepository.findEventById(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        if (roles.isEmpty()) throw new ApiException("No roles found for this event");
        return convertToDTOList(roles);
    }
    public List<RoleDTOout> getRolesByVolunteerId(Integer volunteerId) {
        if (volunteerRepository.findVolunteerById(volunteerId)==null) {throw new ApiException("Volunteer not found");}
        Volunteer volunteer = volunteerRepository.findVolunteerById(volunteerId);
        List<Role> roles = roleRepository.findByVolunteer(volunteer);
        if (roles.isEmpty()) throw new ApiException("No roles found for this volunteer");
        return convertToDTOList(roles);
    }
    public long countRoles() {
        long count = roleRepository.count();
        if (count == 0) throw new ApiException("No roles found in the system");
        return count;
    }
    public void addRole(RoleDTO dto) {
        if (volunteerRepository.findVolunteerById(dto.getVolunteer_id())==null) {throw new ApiException("Volunteer not found");}
        if (eventRepository.findEventById(dto.getEvent_id())==null) {throw new ApiException("Event not found");}
        Role role = createRoleFromDTO(dto);
        roleRepository.save(role);
    }
    public void updateRoleDescription(Integer id, String description) {
        if (roleRepository.findRoleById(id)==null) {throw new ApiException("Role not found");}
        Role role = findRole(id);
        if (description == null || description.trim().isEmpty())
            throw new ApiException("Description cannot be null or empty");
        role.setDescription(description);
        roleRepository.save(role);
    }
    public void updateRoleDetails(Integer id, RoleDTO dto) {
        if (roleRepository.findRoleById(id)==null) {throw new ApiException("Role not found");}
        if (volunteerRepository.findVolunteerById(dto.getVolunteer_id())==null) {throw new ApiException("Volunteer not found");}
        if (eventRepository.findEventById(dto.getEvent_id())==null) {throw new ApiException("Event not found");}
        Role role = findRole(id);
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(eventRepository.findEventById(dto.getEvent_id()));
        role.setVolunteer(volunteerRepository.findVolunteerById(dto.getVolunteer_id()));
        roleRepository.save(role);
    }
    public void deleteRoleById(Integer id) {
        if (roleRepository.findRoleById(id)==null) {throw new ApiException("Role not found");}
        Role role = findRole(id);
        roleRepository.delete(role);
    }
    private Role findRole(Integer id) {
        if (id == null) throw new ApiException("Role ID cannot be null");
        Role role = roleRepository.findRoleById(id);
        if (role == null) throw new ApiException("Role not found");
        return role;
    }
    private Role createRoleFromDTO(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(eventRepository.findEventById(dto.getEvent_id()));
        role.setVolunteer(volunteerRepository.findVolunteerById(dto.getVolunteer_id()));
        return role;
    }
    private RoleDTOout convertToDTOout(Role role) {
        return new RoleDTOout(role.getName(), role.getVolunteer().getName(), role.getEvent().getName(),
                role.getDescription());
    }
    private List<RoleDTOout> convertToDTOList(List<Role> roles) {
        List<RoleDTOout> list = new ArrayList<>();
        for (Role r : roles) {
            list.add(convertToDTOout(r));
        }
        return list;
    }
}
