package com.example.expensesharing.repositories;

import com.example.expensesharing.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByName(String name);

    Group save(Group group);
}
