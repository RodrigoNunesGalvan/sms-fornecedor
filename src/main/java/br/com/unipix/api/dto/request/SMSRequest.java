package br.com.unipix.api.dto.request;

import lombok.Data;

@Data
public class SMSRequest {

	private Long produtoId;
	private Long campanhaId;
	private String smsId;
	private String numero;
	private String mensagem;
	private Long rotaId;
	private Long fornecedorId;
}
