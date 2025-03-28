package com.py.demo.kyc.domain.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiditNewSessionResponse {

    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("session_number")
    private int sessionNumber;
    @JsonProperty("session_token")
    private String sessionToken;
    @JsonProperty("url")
    private String url;
    @JsonProperty("vendor_data")
    private String vendorData;
    private String status;
    private String callback;
    private String features;
}