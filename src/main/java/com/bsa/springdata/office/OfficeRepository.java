package com.bsa.springdata.office;

import com.bsa.springdata.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfficeRepository extends JpaRepository<Office, UUID> {
    @Query("select distinct o from Office o " +
            "inner join o.users u " +
            "inner join u.team team " +
            "inner join team.technology tech " +
            "where tech.name like :technology " +
            "order by o.address")
    List<Office> getByTechnology(@Param("technology") String technology);

    @Modifying
    @Transactional
    @Query("update Office as o " +
            "set o.address = :newAddress " +
            "where o.address = :oldAddress " +
            "and o.users.size > 0")
    void updateAddress(@Param("oldAddress") String oldAddress,
                       @Param("newAddress") String newAddress);

    Optional<Office> findByAddress(String address);

}
