package com.mico.web.repository;

import com.mico.web.domain.Docker;
import com.mico.web.domain.User;
import com.mico.workutils.doc.Doc;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DockerRepository extends JpaRepository<Docker, Integer> {

    @Query("from Docker where content like %:key%")
    List<Docker> vagueSelect (@Param("key")String key);
}