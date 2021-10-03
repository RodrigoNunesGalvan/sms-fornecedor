package br.com.unipix.api.dto.request;

import lombok.Data;

@Data
public class SMSRequest {

	private String id;
	private String numero;
	private String mensagem;
}
