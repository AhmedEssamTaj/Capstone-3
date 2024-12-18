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
        Role role = findRole(id);
        return convertToDTOout(role);
    }

    public List<RoleDTOout> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) throw new ApiException("No roles found");
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByEventId(Integer eventId) {
        Event event = findEvent(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        if (roles.isEmpty()) throw new ApiException("No roles found for this event");
        return convertToDTOList(roles);
    }

    public List<RoleDTOout> getRolesByVolunteerId(Integer volunteerId) {
        Volunteer volunteer = findVolunteer(volunteerId);
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
        validateRoleDTO(dto);
        Role role = createRoleFromDTO(dto);
        roleRepository.save(role);
    }

    public void addRolesInBulk(List<RoleDTO> roles) {
        if (roles == null || roles.isEmpty()) throw new ApiException("Role list cannot be null or empty");
        for (RoleDTO dto : roles) {
            addRole(dto);
        }
    }


    public void updateRoleName(Integer id, String name) {
        Role role = findRole(id);
        if (name == null || name.trim().isEmpty()) throw new ApiException("Role name cannot be null or empty");
        role.setName(name);
        roleRepository.save(role);
    }

    public void updateRoleDescription(Integer id, String description) {
        Role role = findRole(id);
        if (description == null || description.trim().isEmpty())
            throw new ApiException("Description cannot be null or empty");
        role.setDescription(description);
        roleRepository.save(role);
    }

    public void updateRoleDetails(Integer id, RoleDTO dto) {
        validateRoleDTO(dto);
        Role role = findRole(id);
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(findEvent(dto.getEvent_id()));
        role.setVolunteer(findVolunteer(dto.getVolunteer_id()));
        roleRepository.save(role);
    }

    public void deleteRoleById(Integer id) {
        Role role = findRole(id);
        roleRepository.delete(role);
    }

    public void deleteRolesByEvent(Integer eventId) {
        Event event = findEvent(eventId);
        List<Role> roles = roleRepository.findByEvent(event);
        if (roles.isEmpty()) throw new ApiException("No roles found for this event");
        roleRepository.deleteAll(roles);
    }

    public void deleteRolesByVolunteer(Integer volunteerId) {
        Volunteer volunteer = findVolunteer(volunteerId);
        List<Role> roles = roleRepository.findByVolunteer(volunteer);
        if (roles.isEmpty()) throw new ApiException("No roles found for this volunteer");
        roleRepository.deleteAll(roles);
    }


    private Role findRole(Integer id) {
        if (id == null) throw new ApiException("Role ID cannot be null");
        Role role = roleRepository.findRoleById(id);
        if (role == null) throw new ApiException("Role not found");
        return role;
    }

    private Event findEvent(Integer id) {
        if (id == null) throw new ApiException("Event ID cannot be null");
        Event event = eventRepository.findEventById(id);
        if (event == null) throw new ApiException("Event not found");
        return event;
    }

    private Volunteer findVolunteer(Integer id) {
        if (id == null) throw new ApiException("Volunteer ID cannot be null");
        Volunteer volunteer = volunteerRepository.findVolunteerById(id);
        if (volunteer == null) throw new ApiException("Volunteer not found");
        return volunteer;
    }

    private void validateRoleDTO(RoleDTO dto) {
        if (dto == null) throw new ApiException("Role data cannot be null");
        if (dto.getName() == null || dto.getName().trim().isEmpty())
            throw new ApiException("Role name cannot be null or empty");
        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty())
            throw new ApiException("Description cannot be null or empty");
        if (dto.getEvent_id() == null) throw new ApiException("Event ID cannot be null");
        if (dto.getVolunteer_id() == null) throw new ApiException("Volunteer ID cannot be null");
    }

    private Role createRoleFromDTO(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setEvent(findEvent(dto.getEvent_id()));
        role.setVolunteer(findVolunteer(dto.getVolunteer_id()));
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
