package com.mico.web.repository;

import com.mico.web.core.BaseDaoImpl;
import com.mico.web.domain.User;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeDAO extends BaseDaoImpl<User, Long> {
    public void save(User entity) {
        persist(entity);
    }
}