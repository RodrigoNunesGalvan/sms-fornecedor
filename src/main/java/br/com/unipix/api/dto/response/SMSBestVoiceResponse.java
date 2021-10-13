package br.com.unipix.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SMSBestVoiceResponse {

    private String id;
    private String parceiroId;
    private String status;
    private String statusDetalhe;
    
}
