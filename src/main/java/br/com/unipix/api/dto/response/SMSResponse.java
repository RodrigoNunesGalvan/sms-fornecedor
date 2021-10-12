package br.com.unipix.api.dto.response;

import lombok.Data;

@Data
public class SMSResponse {

	private String smsId;
	private String numero;
	private String mensagem;
	private Long campanhaId;
	private Long produtoId;
	private Long rotaId;
	private Long fornecedorId;
    private String mensagemFornecedor;
    private String status;

}
