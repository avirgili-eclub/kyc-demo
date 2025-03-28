package com.py.demo.kyc.domain.client;

import com.google.gson.Gson;
import com.py.demo.kyc.domain.client.dto.DiditNewSessionRequest;
import com.py.demo.kyc.domain.client.dto.DiditNewSessionResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DiditApiClient implements IDiditApiClient {
    @Value("${api.didit.clientId}")
    private String clientId;
    @Value("${api.didit.clientSecret}")
    private String clientSecret;
    @Value("${api.didit.auth}")
    private String authUrl;
    @Value("${api.didit.baseUrl}")
    private String baseUrl;
    @Value("${api.didit.callbackUrl}")
    private String callback;
    @Value("${api.didit.vendorId}")
    private String vendorData;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    private String getToken() {
        try {
            // Configurar credenciales
            String credentials = clientId + ":" + clientSecret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedCredentials);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
            bodyParams.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(bodyParams, headers);

            var response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, String.class).getBody();

            HashMap<String, String> hashMap = gson.fromJson(response, HashMap.class);
            String textoToken = hashMap.get("access_token");

            log.debug("TextoToken result {} ", textoToken);
            log.info("OK Token");

            return textoToken;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public ResponseEntity<DiditNewSessionResponse> createNewSession(DiditNewSessionRequest request) {
        ResponseEntity<DiditNewSessionResponse> response;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Content-Type", "application/json");
        headers.set("charset", "UTF-8");
        headers.set("Authorization", "Bearer " + getToken());


        // Crear cuerpo de la solicitud
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("callback", callback);
        requestBody.put("vendor_data", vendorData);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        log.debug("postConsultaSaldos Request headers params {} ", requestEntity.getHeaders());
        log.debug("postConsultaSaldos Request body params {} ", requestEntity.getBody());

        String URL = baseUrl;
        response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, DiditNewSessionResponse.class);

        log.info("postConsultaSaldos Response result {} ", response.getStatusCode());
        log.info("postConsultaSaldos Response body {} ", response.getBody());

        return response;
    }

    @Override
    public ResponseEntity<String> getStatus(String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + getToken());

        // Construir la URL con el sesionId y los parámetros
        String URL = baseUrl + "/" + sessionId + "/decision/";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URL);

        String fullUrl = uriBuilder.toUriString();
        log.debug("getStatus URL: {}", fullUrl);
        log.debug("getStatus Request headers: {}", headers);

        // Realizar la llamada GET
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        return responseEntity;
    }
}
