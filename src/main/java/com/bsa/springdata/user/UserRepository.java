package com.bsa.springdata.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    @Query("SELECT u FROM User u " +
            "INNER JOIN u.office o " +
            "ON (o.city LIKE :city) " +
            "ORDER BY u.lastName")
    List<User> findByCity(@Param("city") String city);

    List<User> findByExperienceGreaterThanEqualOrderByExperienceDesc(int experience);

    @Query("SELECT u FROM User u " +
            "INNER JOIN u.office o " +
            "ON (o.city LIKE :city) " +
            "INNER JOIN u.team " +
            "ON (u.team.room LIKE :room)")
    List<User> findByRoomAndCity(@Param("city") String city, @Param("room") String room, Sort sort);

    @Modifying
    @Query("DELETE FROM User " +
            "WHERE experience < :experience")
    int deleteByExperience(@Param("experience") int experience);
}
