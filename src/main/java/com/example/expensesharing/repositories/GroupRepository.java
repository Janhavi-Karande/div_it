package com.example.expensesharing.repositories;

import com.example.expensesharing.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByName(String name);

    Group save(Group group);

    @Query("SELECT g FROM groupss g JOIN g.members m WHERE m.email = :userEmail")
    List<Group> findAllByMemberEmail(@Param("userEmail") String userEmail);

}
