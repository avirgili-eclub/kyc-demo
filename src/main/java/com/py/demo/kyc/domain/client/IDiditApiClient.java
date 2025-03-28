package com.py.demo.kyc.domain.client;

import com.py.demo.kyc.domain.client.dto.DiditNewSessionRequest;
import com.py.demo.kyc.domain.client.dto.DiditNewSessionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDiditApiClient {
    ResponseEntity<DiditNewSessionResponse> createNewSession(@RequestBody DiditNewSessionRequest request);

    ResponseEntity<String> getStatus(@RequestBody String sessionId);
}

