package br.com.unipix.api.dto.fornecedor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ZenviaRequest {

    private String from;
    private String to;
    private String schedule;
    private String msg;
    private String callbackOption;
    private String id;
    private String aggregateId;
    private boolean flashSms = false;
    private Integer dataCoding;
}
