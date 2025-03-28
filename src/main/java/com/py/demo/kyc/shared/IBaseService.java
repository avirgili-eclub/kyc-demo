package com.py.demo.kyc.shared;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBaseService<T,R,ID> {

    List<R> getAll();
    Page<R> findAll(Specification spec, Pageable pageable);
    Optional<T> findById(ID id);
    //Optional<R> getById(ID id);
    Optional<R> getById(UUID id);

    T create(T entity) throws ValidationException;
    R createFromDto(R record) throws ValidationException;

    T update(ID id, T entity) throws DBExeption.NoData;
    R updateFromDto(ID id, R record) throws DBExeption.NoData;
    T update(T entity) throws DBExeption.NoData ;

    void delete(ID id) throws DBExeption.NoData;
    void remove(ID id);
    R dtoFromEntity(T entity);

    T entityFromDto(R dto);

    String getUserNameLogeado();
}
