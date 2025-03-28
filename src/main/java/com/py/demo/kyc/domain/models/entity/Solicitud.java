package com.py.demo.kyc.domain.models.entity;

import com.py.demo.kyc.shared.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "solicitud")
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Solicitud extends EntityBase {
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
