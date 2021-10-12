package br.com.unipix.api.dto.request;

import lombok.Data;

@Data
public class SMSRequest {

	private String smsId;
	private String numero;
	private String mensagem;
	private Long campanhaId;
	private Long produtoId;
	private Long rotaId;
	private Long fornecedorId;
    private String statusCode;
    private String statusDescription;
    private String mensagemFornecedor;
}
