package com.bsa.springdata.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnologyRepository extends JpaRepository<Technology, UUID> {
    Optional<Technology> findByName(String technologyName);

    @Modifying
    @Transactional
    @Query("update Technology t " +
            "set t.name = :newTech " +
            "where t.id in :techIds and t.name like :oldTech")
    void updateTechnology(@Param("oldTech") String oldTechnologyName,
                          @Param("newTech") String newTechnologyName,
                          @Param("techIds") List<UUID> technologyId);

}
