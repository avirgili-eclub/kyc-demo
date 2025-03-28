package com.py.demo.kyc.domain.services;

import com.google.gson.Gson;
import com.py.demo.kyc.domain.client.IDiditApiClient;
import com.py.demo.kyc.domain.client.dto.DiditNewSessionRequest;
import com.py.demo.kyc.domain.client.dto.DiditStatusResponse;
import com.py.demo.kyc.domain.models.entity.DiditSession;
import com.py.demo.kyc.domain.models.mapper.DiditSessionMapper;
import com.py.demo.kyc.domain.models.record.DiditSessionRecord;
import com.py.demo.kyc.domain.repository.KycRepository;
import com.py.demo.kyc.shared.BaseRepo;
import com.py.demo.kyc.shared.BaseService;
import com.py.demo.kyc.shared.DBExeption;
import com.py.demo.kyc.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class KycService extends BaseService<DiditSession, DiditSessionRecord, Long> implements IKycService {

    private final KycRepository repository;
    private final IDiditApiClient diditClient;
    private final SolicitudService solicitudService;
    private final DiditSessionMapper mapper;
    @Value("${api.didit.callbackUrl}")
    private String diditCallbackUrl;
    @Value("${api.didit.vendorId}")
    private String vendorId;

    @Autowired
    private Gson gson;

    public KycService(KycRepository repository, IDiditApiClient diditClient, SolicitudService solicitudService, DiditSessionMapper mapper) {
        this.repository = repository;
        this.diditClient = diditClient;
        this.solicitudService = solicitudService;
        this.mapper = mapper;
    }

    @Override
    public String pruebaDeVida(String documento) {
        try {
            DiditNewSessionRequest diditNewSessionRequest = DiditNewSessionRequest.builder()
                    .callBack(diditCallbackUrl)
                    .vendorData(vendorId)
                    .build();

            var result = diditClient.createNewSession(diditNewSessionRequest);
            log.info(Objects.requireNonNull(result.getBody()).toString());
            DiditSession diditSession = new DiditSession();
            diditSession.setDiditSessionResponse(gson.toJson(result.getBody()));
            diditSession.setDiditSessionRequest(gson.toJson(diditNewSessionRequest));
            diditSession.setSessionId(result.getBody().getSessionId());
            diditSession.setSessionToken(result.getBody().getSessionToken());
            diditSession.setStatus(result.getBody().getStatus());
            diditSession.setValidationUrl(result.getBody().getUrl());
            diditSession.setFeatures(result.getBody().getFeatures());
            diditSession.setSolicitud(solicitudService.findByDocumento(documento));
            repository.save(diditSession);
            return result.getBody().getUrl();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public DiditStatusResponse consultarStatusPruebaDeVida(Long solicitudId) {
        try {
            // 1. Obtener el objeto relacionado a la solicitud
            DiditSession diditSession = repository.findTop1BySolicitud_IdOrderByIdDesc(solicitudId)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró una operación para la solicitud ID: " + solicitudId));

//            // 2. Obtener el sessionId utilizando el servicio
//            String sessionId = getSesionIdBySolicitudId(solicitudId);
//            if (sessionId == null || sessionId.isBlank()) {
//                throw new IllegalArgumentException("Sesión ID no encontrado para la solicitud ID: " + solicitudId);
//            }

            // 3. Llamar al cliente para obtener el estado
            ResponseEntity<String> response = diditClient.getStatus(diditSession.getSessionId());

            if (response.getStatusCode().is2xxSuccessful()) {
                // 4. Parsear el response al DTO DiditStatusResponse
                DiditStatusResponse diditStatusResponse = (DiditStatusResponse) Util.convertStringJsonToObject(response.getBody(), DiditStatusResponse.class);

                // 5. Guardar el response en la base de datos
                diditSession.setDiditSessionResponse(response.getBody());
                diditSession.setVendor(diditStatusResponse.getVendorData());
                diditSession.setDocumentoFrente(diditStatusResponse.getKyc().getFrontImage());
                diditSession.setDocumentoDorso(diditStatusResponse.getKyc().getBackImage());

                // Opcional: guardar valores adicionales
                diditSession.setSelfie(diditStatusResponse.getFace().getTargetImage());

                // Actualizar la operación
                update(diditSession);

                return diditStatusResponse;
            } else {
                log.error("Error al obtener los datos de Didit. Status code: {}", response.getStatusCode());
                throw new RuntimeException("Error al obtener los datos de Didit");
            }
        } catch (Exception e) {
            log.error("Excepción al consultar el estado en Didit", e);
            throw new RuntimeException("Error en consultarStatusPruebaDeVida", e);
        }
    }

    private String getSesionIdBySolicitudId(Long solicitudId) {
        return null;
    }

    @Override
    public BaseRepo getRepo() {
        return this.repository;
    }

    @Override
    protected DiditSession mapValues(Long aLong, DiditSession entity) throws DBExeption.NoData {
        return null;
    }

    @Override
    public DiditSessionRecord dtoFromEntity(DiditSession entity) {
        return mapper.toRecord(entity);
    }

    @Override
    public DiditSession entityFromDto(DiditSessionRecord dto) {
        return mapper.toEntity(dto);
    }
}
