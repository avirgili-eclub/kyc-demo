package com.py.demo.kyc.domain.services;

import com.py.demo.kyc.domain.models.dto.SolicitudDto;
import com.py.demo.kyc.domain.models.entity.Solicitud;
import com.py.demo.kyc.shared.DBExeption;
import com.py.demo.kyc.shared.IBaseService;

public interface ISolicitudService extends IBaseService<Solicitud, SolicitudDto, Long> {
    Solicitud findByDocumento(String documento) throws DBExeption.NoData;
}
