package com.py.demo.kyc.shared;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
public abstract class BaseRestController<T,R> {
    @GetMapping("/all")
    public List<R> getAll() {
        return getService().getAll();
    }
    @GetMapping("/")
    public Page<R> findAll( Specification<R> spec,
                            Pageable pageable) {
        return getService().findAll(spec, pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
        R result = getService().getById(id).orElse(null);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody R body, BindingResult result) {
        if(result.hasErrors()) {
            log.error("Error de Validacion {} ", result.getFieldErrors());
            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.formatMessages("01", result.getFieldErrors()));
        }
        try {
            R resultCreate = getService().createFromDto(body);
            return ResponseEntity.ok(new MessageResponse("00","Registro creado con exito", resultCreate));
        } catch (Exception e) {
            log.error("Error al crear {} ", e.getMessage());
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@Valid @RequestBody R body, BindingResult result) {
        if(result.hasErrors()) {
            log.error("Error de Validacion {} ", result.getFieldErrors());
            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.formatMessages("01", result.getFieldErrors()));
        }
        try {
            R resultUpdate = getService().updateFromDto(id, body);
            return ResponseEntity.ok(new MessageResponse("00","Registro actualizado con exito", resultUpdate));
        } catch (Exception e) {
            log.error("Error al actualizar {} ", e.getMessage());
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @RolesAllowed("admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try{
            getService().delete(id);
            return ResponseEntity.ok(new MessageResponse("00","Registro eliminado con exito", id));
        } catch (DBExeption.NoData e){
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    protected abstract BaseService<T,R,Long> getService();
}