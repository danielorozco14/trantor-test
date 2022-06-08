package com.dorozco.service.repository;

import com.dorozco.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByStatus(String status);//Status: read, non-read

    @Modifying
    @Query("update Message m set m.status='seen' where m.id=?1 ")
    void updateMessageStatus(Integer messageId);
}
