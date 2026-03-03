package com.zapier.worker.repository;

import com.zapier.worker.entity.ZapRun;
import com.zapier.worker.entity.ZapRunOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZapRunRepository extends JpaRepository<ZapRun,String> {

    @Query("SELECT zr FROM ZapRun zr " +
           "JOIN FETCH zr.zap z" +
           "JOIN FETCH z.actions a" +
            "JOIN FETCH a.type " +
            "WHERE zr.id = :zapRunId")
    Optional<ZapRun> findByIdWithDetails(@Param("zapRunId") String zapRunId);

}
