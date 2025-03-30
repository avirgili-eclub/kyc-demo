package com.py.demo.kyc.domain.controller;

import com.py.demo.kyc.domain.services.IKycService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kyc")
//kyc = Know your client, conoce a tu cliente.
public class KycController {
    private final IKycService diditService;

    public KycController(IKycService diditService) {
        this.diditService = diditService;
    }


    //Iniciar prueba de vida
    @PostMapping("/iniciarPruebaDeVida")
    public String iniciarPruebaDeVida(String documento) {
        return diditService.iniciarPruebaDeVida(documento);
    }

}
