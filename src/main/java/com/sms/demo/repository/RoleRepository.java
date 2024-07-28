package com.sms.demo.repository;

import com.sms.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByRoleName(String roleName);

}
