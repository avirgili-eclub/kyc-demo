package com.py.demo.kyc.domain.controller;

import com.py.demo.kyc.domain.models.dto.PruebaDeVidaRequest;
import com.py.demo.kyc.domain.models.dto.PruebaDeVidaResponse;
import com.py.demo.kyc.domain.services.IKycService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1/kyc")
@Tag(name = "KYC", description = "API para gestión de Prueba de Vida")
public class KycController {
    private final IKycService diditService;

    public KycController(IKycService diditService) {
        this.diditService = diditService;
    }


    @Operation(summary = "Iniciar prueba de vida",
            description = "Inicia el proceso de verificación biométrica para validar la identidad del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prueba iniciada correctamente",
                    content = @Content(schema = @Schema(implementation = PruebaDeVidaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Documento no válido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/iniciarPruebaDeVida")
    public ResponseEntity<?> iniciarPruebaDeVida(@RequestBody @Valid PruebaDeVidaRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        try {
            log.debug("Iniciando prueba de vida de {}", request);
            PruebaDeVidaResponse response = diditService.iniciarPruebaDeVida(request.getDocumento());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al iniciar prueba de vida: ", e);
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

}
