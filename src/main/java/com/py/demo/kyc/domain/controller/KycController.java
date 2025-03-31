package com.py.demo.kyc.domain.controller;

import com.py.demo.kyc.domain.services.IKycService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kyc")
@Tag(name = "KYC", description = "API para gestión de Prueba de Vida")
//kyc = Know your client, conoce a tu cliente.
public class KycController {
    private final IKycService diditService;

    public KycController(IKycService diditService) {
        this.diditService = diditService;
    }


    @Operation(summary = "Iniciar prueba de vida",
            description = "Inicia el proceso de verificación biométrica para validar la identidad del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prueba iniciada correctamente",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Documento no válido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/iniciarPruebaDeVida")
    public String iniciarPruebaDeVida(
            @Parameter(description = "Número de documento del cliente", required = true) @RequestParam String documento) {
        return diditService.iniciarPruebaDeVida(documento);
    }

}
