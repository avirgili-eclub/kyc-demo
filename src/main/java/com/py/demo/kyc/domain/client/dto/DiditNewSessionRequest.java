package com.py.demo.kyc.domain.client.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiditNewSessionRequest {
    private String callBack;
    private String vendorData;
}
