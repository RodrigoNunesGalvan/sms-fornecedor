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
@Table(name = "ParametroFornecedorSMS", catalog = "SMSHub")
public class ParametroFornecedorSMS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "chave")
	private String chave;

	@Column(name = "valor")
	private String valor;

	@Column(name = "fornecedorSMSID")
	private Integer fornecedorSMSID;

}