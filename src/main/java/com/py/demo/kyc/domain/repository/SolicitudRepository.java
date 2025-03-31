package com.py.demo.kyc.domain.repository;

import com.py.demo.kyc.domain.models.entity.Solicitud;
import com.py.demo.kyc.shared.BaseRepo;

import java.util.Optional;

public interface SolicitudRepository  extends BaseRepo<Solicitud, Long>  {
    Optional<Solicitud> findByDocumento(String documento);
}
