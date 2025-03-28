package com.py.demo.kyc.domain.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiditStatusResponse {
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("session_number")
    private int sessionNumber;
    @JsonProperty("session_url")
    private String sessionUrl;
    private String status;
    @JsonProperty("vendor_data")
    private String vendorData;
    @JsonProperty("callback")
    private String callback;
    private String features;
    private KycDto kyc;
    private FaceDTO face;
    @JsonProperty("request_info")
    private RequestInfoDTO info;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KycDto {
        private String status;
        @JsonProperty("document_type")
        private String documentType;
        @JsonProperty("document_number")
        private String documentNumber;
        @JsonProperty("personal_number")
        private String personalNumber;
        @JsonProperty("portrait_image")
        private String portraitImage;
        @JsonProperty("front_image")
        private String frontImage;
        @JsonProperty("back_image")
        private String backImage;
        @JsonProperty("full_front_image")
        private String fullFrontImage;
        @JsonProperty("full_back_image")
        private String fullBackImage;
        @JsonProperty("date_of_birth")
        private String dateOfBirth;
        @JsonProperty("expiration_date")
        private String expirationDate;
        @JsonProperty("issuing_state")
        private String issuingState;
        @JsonProperty("issuing_state_name")
        private String issuingStateName;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("full_name")
        private String fullName;
        private String gender;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FaceDTO {
        @JsonProperty("face_match_status")
        private String faceMatchStatus;
        @JsonProperty("liveness_status")
        private String livenessStatus;
        @JsonProperty("face_match_similarity")
        private Double faceMatchSimilarity;
        @JsonProperty("liveness_confidence")
        private Double livenessConfidence;
        @JsonProperty("source_image")
        private String sourceImage;
        @JsonProperty("target_image")
        private String targetImage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestInfoDTO {
        private String isp;
        @JsonProperty("ip_city")
        private String ipCity;
        @JsonProperty("ip_state")
        private String ipState;
        private Double latitude;
        private String platform;
        private Double longitude;
        @JsonProperty("os_family")
        private String osFamily;
        @JsonProperty("time_zone")
        private String timeZone;
        @JsonProperty("ip_address")
        private String ipAddress;
        @JsonProperty("ip_country")
        private String ipCountry;
        @JsonProperty("device_brand")
        private String deviceBrand;
        @JsonProperty("device_model")
        private String deviceModel;
        private String organization;
        @JsonProperty("is_vpn_or_tor")
        private Boolean isVpnOrTor;
        @JsonProperty("browser_family")
        private String browserFamily;
        @JsonProperty("is_data_center")
        private Boolean isDataCenter;
        @JsonProperty("ip_country_code")
        private String ipCountryCode;
        @JsonProperty("time_zone_offset")
        private String timeZoneOffset;
    }
}


