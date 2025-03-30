package com.py.demo.kyc.domain.services;

import com.py.demo.kyc.domain.client.dto.DiditStatusResponse;

public interface IKycService {
    String iniciarPruebaDeVida(String documento);
    DiditStatusResponse consultarStatusPruebaDeVida(Long solicitudId);
}
