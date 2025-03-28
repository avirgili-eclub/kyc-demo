package com.py.demo.kyc.domain.services;

import com.py.demo.kyc.domain.client.dto.DiditStatusResponse;

public interface IKycService {
    String pruebaDeVida(String documento);
    DiditStatusResponse consultarStatusPruebaDeVida(Long solicitudId);
}
