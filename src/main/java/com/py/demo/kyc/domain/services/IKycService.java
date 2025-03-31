package com.py.demo.kyc.domain.services;

import com.py.demo.kyc.domain.client.dto.DiditStatusResponse;
import com.py.demo.kyc.domain.exception.PruebaDeVidaException;
import com.py.demo.kyc.domain.models.dto.PruebaDeVidaResponse;

public interface IKycService {
    PruebaDeVidaResponse iniciarPruebaDeVida(String documento) throws PruebaDeVidaException;
    DiditStatusResponse consultarStatusPruebaDeVida(Long solicitudId);
}
