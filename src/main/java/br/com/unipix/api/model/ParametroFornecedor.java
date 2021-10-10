package br.com.unipix.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_parametro_fornecedor", catalog = "unipix")
public class ParametroFornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "chave")
	private String chave;

	@Column(name = "valor")
	private String valor;

	@Column(name = "fornecedorSMSID")
	private Long fornecedorSMSID;

}