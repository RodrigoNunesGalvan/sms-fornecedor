package br.com.unipix.api.dto.response;

import lombok.Data;

@Data
public class SMSZenviaResponse {

    private String statusCode;
    private String statusDescription;
    private String detailCode;
    private String detailDescription;
}
