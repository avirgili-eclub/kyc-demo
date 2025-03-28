package com.py.demo.kyc.domain.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudDto {
    private String documento;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private String ciudad;
    private String nacionalidad;
    private String estadoCivil;
    private String pais;
    private String codigoPostal;
    private String tipoDocumento;
    private String fechaSolicitud;
    private String fechaAprobacion;
    private String fechaRechazo;
    private String motivoRechazo;
    private String estadoSolicitud;
}
