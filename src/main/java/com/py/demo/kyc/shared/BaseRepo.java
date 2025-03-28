package com.py.demo.kyc.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepo<T extends EntityBase,ID> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {
    Optional<T> findByUuid(UUID uuid);
}
