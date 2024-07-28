package com.sms.demo.controller;

import com.sms.demo.entity.Role;
import com.sms.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("public/api/v1/utility")
public class UtilityController {

    @Autowired
    RoleRepository roleRepository;

    @PostMapping
    public List<Role> createRoles(@RequestBody List<Role> roles) {
        return roleRepository.saveAll(roles);
    }
}
