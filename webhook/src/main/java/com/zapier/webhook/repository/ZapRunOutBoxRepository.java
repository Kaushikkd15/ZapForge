package com.zapier.webhook.repository;

import com.zapier.webhook.entity.ZapRunOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZapRunOutBoxRepository extends JpaRepository<ZapRunOutbox,String> {

     Optional<ZapRunOutbox> findByZapRunId(String zapRunId);
}
