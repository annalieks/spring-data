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

    @Query("select u from User u " +
            "inner join u.office o " +
            "on (o.city like :city) " +
            "order by u.lastName asc")
    List<User> findByCity(@Param("city") String city);

    List<User> findByExperienceGreaterThanEqualOrderByExperienceDesc(int experience);

    @Query("select u from User u " +
            "inner join u.office o " +
            "on (o.city like :city) " +
            "inner join u.team " +
            "on (u.team.room like :room)")
    List<User> findByRoomAndCity(@Param("city") String city, @Param("room") String room, Sort sort);

    @Modifying
    @Query("delete from User as u " +
            "where u.experience < :experience")
    int deleteByExperience(@Param("experience") int experience);
}
