package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleDao.findAll());
    }

    public Role getRoleById(long id) {
        return roleDao.findById(id).orElse(null);
    }

    public Role getRoleByName(String name) {
        return roleDao.findByName(name);
    }

    public void createRole(Role role) {
        roleDao.save(role);
    }

    public void deleteRole(long id) {
        roleDao.deleteById(id);
    }
}
