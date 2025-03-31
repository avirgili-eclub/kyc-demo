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
            @ApiResponse(responseCode = "200", description = "https://verify.didit.me/session/eyJhbGciVCnfx",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Documento no válido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/iniciarPruebaDeVida")
    public String iniciarPruebaDeVida(
            @Parameter(description = "Número de documento del cliente", required = true) @RequestParam String documento) {
        try {
            if (documento == null || documento.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Documento no válido");
            }

            String url = diditService.iniciarPruebaDeVida(documento);
            return ResponseEntity.ok(url);
        } catch (IllegalArgumentException e) {
            // Para casos donde el documento es inválido pero pasó la validación inicial
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Documento no válido: " + e.getMessage());
        } catch (Exception e) {
            // Para errores internos del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

}
