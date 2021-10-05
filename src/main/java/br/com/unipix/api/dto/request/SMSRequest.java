package br.com.unipix.api.dto.request;

import lombok.Data;

@Data
public class SMSRequest {

	private Integer produtoId;
	private Integer fornecedorId;
	private String smsId;
	private String numero;
	private String mensagem;
}
