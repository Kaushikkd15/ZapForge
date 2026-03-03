package com.zapier.webhook.repository;

import com.zapier.webhook.entity.ZapRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZapRunRepository extends JpaRepository<ZapRun, String> {

    List<ZapRun> findByZapId(String zapId);

}
