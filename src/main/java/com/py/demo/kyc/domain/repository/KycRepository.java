package com.py.demo.kyc.domain.repository;

import com.py.demo.kyc.domain.models.entity.DiditSession;
import com.py.demo.kyc.shared.BaseRepo;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface KycRepository extends BaseRepo<DiditSession, Long> {
    Optional<DiditSession> findTop1BySolicitud_IdOrderByIdDesc(@NonNull Long id);

}