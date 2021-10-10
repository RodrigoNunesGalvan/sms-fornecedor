package br.com.unipix.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unipix.api.model.ParametroFornecedor;
import br.com.unipix.api.repository.ParametroFornecedorRepository;

@Service
public class ParametroFornecedorService {

	@Autowired
	private ParametroFornecedorRepository repositoryParametroFornecedorSMS;

	public HashMap<String,String> findByfornecedorSMSID(Long fornecedorSMSID) {
		HashMap<String,String> chaves = new HashMap<>();
		List<ParametroFornecedor> parametros = repositoryParametroFornecedorSMS.findByfornecedorSMSID(fornecedorSMSID);
		for (ParametroFornecedor fornecedorSMSParam : parametros) {
			chaves.put(fornecedorSMSParam.getChave(), fornecedorSMSParam.getValor());
		}
		return chaves;
	}

}
