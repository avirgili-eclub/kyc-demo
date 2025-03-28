package com.py.demo.kyc.domain.services;

import com.py.demo.kyc.domain.models.dto.SolicitudDto;
import com.py.demo.kyc.domain.models.entity.Solicitud;
import com.py.demo.kyc.domain.repository.SolicitudRepository;
import com.py.demo.kyc.shared.BaseRepo;
import com.py.demo.kyc.shared.BaseService;
import com.py.demo.kyc.shared.DBExeption;
import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class SolicitudService extends BaseService<Solicitud, SolicitudDto, Long> implements ISolicitudService {
    private final SolicitudRepository repository;

    public SolicitudService(SolicitudRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseRepo getRepo() {
        return this.repository;
    }

    @Override
    protected Solicitud mapValues(Long id, Solicitud entity) throws DBExeption.NoData {
        Solicitud live = findById(id).orElseThrow(() -> new DBExeption.NoData("No se encontró el registro"));

        live.setDocumento(entity.getDocumento() == null ? "" : entity.getDocumento());
        live.setNombre(entity.getNombre() == null ? "" : entity.getNombre());
        live.setUpdatedDate(entity.getUpdatedDate() == null ? null : entity.getUpdatedDate());
        live.setEstadoSolicitud(entity.getEstadoSolicitud() == null ? null : entity.getEstadoSolicitud());
        live.setApellido(entity.getApellido() == null ? "" : entity.getApellido());
        live.setPais(entity.getPais() == null ? "" : entity.getPais());
        live.setNacionalidad(StringUtils.isNotBlank(entity.getNacionalidad()) ? entity.getNacionalidad() : "");
        return live;
    }

    @Override
    public SolicitudDto dtoFromEntity(Solicitud entity) {
        return toDto(entity);
    }

    @Override
    public Solicitud entityFromDto(SolicitudDto dto) {
        return toEntity(dto);
    }

    private Solicitud toEntity(SolicitudDto dto) {
        return new Solicitud();
    }

    private SolicitudDto toDto(Solicitud entity) {
        return null;
    }

    @Override
    public Solicitud findByDocumento(String documento) throws DBExeption.NoData {
        return repository.findByDocumento(documento)
                .orElseThrow(() -> new DBExeption.NoData("No se encontró la solicitud con el documento: " + documento));
    }
}
