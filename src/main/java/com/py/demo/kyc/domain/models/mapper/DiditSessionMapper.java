package com.py.demo.kyc.domain.models.mapper;

import com.py.demo.kyc.domain.models.entity.DiditSession;
import com.py.demo.kyc.domain.models.record.DiditSessionRecord;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiditSessionMapper {
    DiditSession toEntity(DiditSessionRecord diditSessionRecord);

    DiditSessionRecord toRecord(DiditSession diditSession);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DiditSession partialUpdate(DiditSessionRecord diditSessionRecord, @MappingTarget DiditSession diditSession);
}


