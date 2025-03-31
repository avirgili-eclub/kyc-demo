package com.py.demo.kyc.domain.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDeVidaRequest {
    @NotEmpty(message = "El campo 'documento' es obligatorio")
    @NotBlank(message = "El campo 'documento' es obligatorio")
    private String documento;
}
