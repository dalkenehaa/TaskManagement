package com.cg.tms.repo;

import com.cg.tms.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {
    List<AttachmentEntity> findByTaskId(Integer taskId);
}

