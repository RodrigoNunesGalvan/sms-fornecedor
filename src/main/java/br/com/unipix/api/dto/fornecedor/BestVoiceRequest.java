package br.com.unipix.api.dto.fornecedor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BestVoiceRequest {

	private String celular;
	private String mensagem;
	private String parceiroId;
	
}
