package com.py.demo.kyc.shared;


import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public abstract class BaseService<T, R, ID> implements IBaseService<T, R, ID> {

    public abstract BaseRepo getRepo();

    public List<R> getAll() {
        return getRepo().findAll().stream().map(entity -> dtoFromEntity((T) entity)).toList();
    }
    public Page<R> findAll(Specification spec, Pageable pageable) {
        return getRepo().findAll(spec, pageable).map(entity -> dtoFromEntity((T)entity));
    }

    public Optional<R> getById(UUID uuid) {
        return getRepo().findByUuid(uuid).map(entity -> dtoFromEntity((T) entity));
    }

    public Optional<T> findById(ID id) {
        return getRepo().findById(id);
    }

    public T create(T entity) throws ValidationException {
        beforeCreate(entity);
        setAuditFields(entity, true);
        T result = (T) getRepo().save(entity); getRepo().flush();
        afterCreate(result);
        return result;
    }

    public R createFromDto(R dto) {
        T entity = entityFromDto(dto);
        beforeCreate(entity);
        setAuditFields(entity, true);
        T result = (T) getRepo().save(entity);
        afterCreate(result);
        return dtoFromEntity(result);
    }

    protected void setAuditFields(T entity, Boolean create) {
        if (entity instanceof EntityBase) {
            if (create) {
                ((EntityBase) entity).setEstado(Estado.ACTIVO);
                ((EntityBase) entity).setDeleted(false);
                ((EntityBase) entity).setCreatedBy(getUserNameLogeado());
                ((EntityBase) entity).setCreatedDate(LocalDateTime.now());
            }
            else{
                ((EntityBase) entity).setUpdatedBy(getUserNameLogeado());
                ((EntityBase) entity).setUpdatedDate(LocalDateTime.now());
            }
        }
    }

    public T update(ID id, T entity) throws DBExeption.NoData {
        T live = mapValues(id, entity);
        beforeUpdate(live);
        setAuditFields(live, false);
        T result = (T) getRepo().save(live);
        afterUpdate(result);
        return result;
    }

    public R updateFromDto(ID id, R dto) throws DBExeption.NoData {
        T live = mapValues(id, entityFromDto(dto));
        beforeUpdate(live);
        setAuditFields(live, false);
        T result = (T) getRepo().save(live);
        afterUpdate(result);
        return dtoFromEntity(result);
    }
    public T update(T entity) throws DBExeption.NoData  {
        if(((EntityBase) entity).getId() == null){
            throw new DBExeption.NoData("No existe el registro");
        }
        beforeUpdate(entity);
        setAuditFields(entity, false);
        T result = (T) getRepo().save(entity);
        afterUpdate(result);
        return result;
    }

    /**
     * delete marca un registro como deleted true, borrado logico
     *
     * @param id
     */
    public void delete(ID id) throws DBExeption.NoData {
        T entity = (T) findById(id).orElseThrow(() -> new DBExeption.NoData("No existe el registro"));
        ((EntityBase) entity).setDeteledBy(getUserNameLogeado());
        ((EntityBase) entity).setDeletedDate(LocalDateTime.now());
        ((EntityBase) entity).setDeleted(true);
        ((EntityBase) entity).setEstado(Estado.INACTIVO);
        beforeDelete(entity);
        getRepo().save(entity);
        afterDelete(entity);
    }

    /**
     * remove elimina definitivamente un refistro de la base de datos
     *
     * @param id id
     */
    public void remove(ID id) {
        if (getRepo().existsById(id)){
            getRepo().deleteById(id);
            getRepo().flush();
        }else {
            log.error("No se pudo eliminar el registro, no existe con id: {}", id);
        }


    }
    public Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (Exception e1) {
                try {
                    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
                } catch (Exception e2) {
                    throw new ValidationException("Formato de fecha no valido");
                }
            }
        }
    }


    public void beforeCreate(T entity) {

    }

    public void beforeDelete(T entity) {

    }

    public void beforeUpdate(T entity){

    }

    public void afterCreate(T entity)  {

    }

    public void afterUpdate(T entity)  {

    }

    public void afterDelete(T entity)  {

    }

    protected abstract T mapValues(ID id, T entity) throws DBExeption.NoData;

    public abstract R dtoFromEntity(T entity);

    public abstract T entityFromDto(R dto);

    public String getUserNameLogeado() {
        try {
            if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
                Authentication principal = SecurityContextHolder.getContext().getAuthentication();
                // Mapear el objeto a JSONObject
                log.info("Principal: {}", principal);
                UserDetails userDetails = null;
                if(principal instanceof UserDetails) {
                    userDetails = (UserDetails) principal;
                    return userDetails.getUsername();
                }
                //transformar principal a un json

                return principal.getName();
            }else {
                return "anonymus";
            }
        } catch (Exception e) {
            log.error("Error al obtener el usuario logueado: {}", e.getMessage());
            return "anonymus";
        }
    }
}
