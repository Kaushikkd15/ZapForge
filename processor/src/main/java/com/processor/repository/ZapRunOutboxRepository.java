package com.processor.repository;


import com.processor.entity.ZapRunOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZapRunOutboxRepository extends JpaRepository<ZapRunOutbox, String> {


}
