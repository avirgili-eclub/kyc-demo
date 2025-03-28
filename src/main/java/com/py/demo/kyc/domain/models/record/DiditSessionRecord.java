package com.py.demo.kyc.domain.models.record;

import com.py.demo.kyc.domain.models.entity.Solicitud;

public record DiditSessionRecord(
        Long id,
        Solicitud solicitud,
        String validationUrl,
        String sessionToken,
        String operationGuid,
        String operationType,
        String operationDetails,
        String cisefRequest
) {
}
