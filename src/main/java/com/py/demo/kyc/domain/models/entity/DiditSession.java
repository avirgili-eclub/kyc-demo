package com.py.demo.kyc.domain.models.entity;

import com.py.demo.kyc.shared.EntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
public class DiditSession extends EntityBase {
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "solicitud_id")
    @ToString.Exclude
    private Solicitud solicitud;
    private String validationUrl;
    private String sessionToken;
    private String sessionId;
    private Integer sessionNumber;
    private String diditSessionRequest;
    @Column(columnDefinition = "TEXT")
    private String diditSessionResponse;
    private String status;
    private String features;
    private String vendor;
    @Column(columnDefinition = "TEXT")
    private String documentoFrente;
    @Column(columnDefinition = "TEXT")
    private String documentoDorso;
    @Column(columnDefinition = "TEXT")
    private String selfie;
    private Double faceMatch;
    private Double livenessConfidence;
}
