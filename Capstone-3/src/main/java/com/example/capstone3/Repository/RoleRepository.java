package com.example.capstone3.Repository;

import com.example.capstone3.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);

    @Query("SELECT r FROM Role r WHERE r.name LIKE %?1%")
    List<Role> findByNameContaining(String name);
}
