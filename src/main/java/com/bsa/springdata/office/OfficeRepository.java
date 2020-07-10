package com.bsa.springdata.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfficeRepository extends JpaRepository<Office, UUID> {
    @Query("SELECT DISTINCT o FROM Office o " +
            "INNER JOIN o.users u " +
            "INNER JOIN u.team team " +
            "INNER JOIN team.technology tech " +
            "WHERE tech.name LIKE :technology " +
            "ORDER BY o.address")
    List<Office> getByTechnology(@Param("technology") String technology);

    @Modifying
    @Transactional
    @Query("UPDATE Office AS o " +
            "SET o.address = :newAddress " +
            "WHERE o.address = :oldAddress " +
            "AND SIZE(o.users) > 0")
    void updateAddress(@Param("oldAddress") String oldAddress,
                       @Param("newAddress") String newAddress);

    Optional<Office> findByAddress(String address);
}
