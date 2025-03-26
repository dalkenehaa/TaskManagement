package com.cg.tms.util;

import com.cg.tms.dto.AttachmentDto;
import com.cg.tms.entity.AttachmentEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttachmentUtil {

    public static AttachmentDto covertEntityToDto(AttachmentEntity entity) {
        AttachmentDto dto = new AttachmentDto();
        dto.setAttachmentId(entity.getAttachmentId());
        dto.setFileName(entity.getFileName());
        dto.setFilePath(entity.getFilePath());
        dto.setTaskId(entity.getTaskId());
        return dto;
    }

    public static Set<AttachmentDto> convertEntityListToDtoSet(List<AttachmentEntity> entityList) {
        return entityList.stream()
                .map(AttachmentUtil::covertEntityToDto)
                .collect(Collectors.toSet());
    }

    public static AttachmentEntity covertDtoToEntity(AttachmentDto dto) {
        AttachmentEntity entity = new AttachmentEntity();
        entity.setAttachmentId(dto.getAttachmentId());
        entity.setFileName(dto.getFileName());
        entity.setFilePath(dto.getFilePath());
        entity.setTaskId(dto.getTaskId());
        return entity;
    }
}
